package com.duy.nb.spring.security.service;

import com.duy.nb.spring.security.algorithms.AESAlgorithm;
import com.duy.nb.spring.security.algorithms.RSAAlgorithm;
import com.duy.nb.spring.security.dto.CreateHistoryResponseDto;
import com.duy.nb.spring.security.dto.TransactionDto;
import com.duy.nb.spring.security.entity.KeyStorage;
import com.duy.nb.spring.security.entity.TransactionHistory;
import com.duy.nb.spring.security.repository.KeyStorageRepository;
import com.duy.nb.spring.security.repository.TransactionHistoryRepository;
import com.duy.nb.spring.security.untils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionHistoryService {
    @Autowired
    private static RSAAlgorithm rsaAlgorithm;

    @Autowired
    private AESAlgorithm aesAlgorithm;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final KeyStorageRepository keyStorageRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository, KeyStorageRepository keyStorageRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.keyStorageRepository = keyStorageRepository;
    }

    /**
     * Service tạo lịch sử giao dịch
     * @param transactionDto đối tượng nhận từ controller đã được mã hóa bởi private key của server
     * @return danh sách 2 phần tử bao gồm lịch sử của tài khoản chuyển và nhận đã được mã hóa với public key của client
     */
    public List<CreateHistoryResponseDto> createTransaction(TransactionDto transactionDto){
        if(Objects.isNull(transactionDto)){
            throw new IllegalArgumentException();
        }
        SecretKey secretKey = aesAlgorithm.getSecretKey();
        Date date = new Date();

        transactionDto.setTransactionId(rsaAlgorithm.decrypt(transactionDto.getTransactionId(),Constants.SERVER_PRIVATE_KEY));
        transactionDto.setInAccount(rsaAlgorithm.decrypt(transactionDto.getInAccount(),Constants.SERVER_PRIVATE_KEY));
        transactionDto.setOutAccount(rsaAlgorithm.decrypt(transactionDto.getOutAccount(),Constants.SERVER_PRIVATE_KEY));
        transactionDto.setValue(rsaAlgorithm.decrypt(transactionDto.getValue(),Constants.SERVER_PRIVATE_KEY));

        TransactionHistory inAccountHistory = new TransactionHistory();
        inAccountHistory.setAccount(aesAlgorithm.encrypt(transactionDto.getInAccount(),secretKey));
        inAccountHistory.setTransactionId(transactionDto.getTransactionId());
        inAccountHistory.setHave(new BigDecimal(transactionDto.getValue()));
        inAccountHistory.setTime(date);
        TransactionHistory savedInAccountHistory = transactionHistoryRepository.save(inAccountHistory);
        keyStorageRepository.save(new KeyStorage(savedInAccountHistory.getId(), Base64.getEncoder().encodeToString(secretKey.getEncoded())));

        TransactionHistory outAccountHistory = new TransactionHistory();
        outAccountHistory.setAccount(aesAlgorithm.encrypt(transactionDto.getOutAccount(),secretKey));
        outAccountHistory.setTransactionId(transactionDto.getTransactionId());
        outAccountHistory.setInDebt(new BigDecimal(transactionDto.getValue()));
        outAccountHistory.setTime(date);
        TransactionHistory savedOutAccountHistory = transactionHistoryRepository.save(outAccountHistory);
        keyStorageRepository.save(new KeyStorage(savedOutAccountHistory.getId(), Base64.getEncoder().encodeToString(secretKey.getEncoded())));

        CreateHistoryResponseDto inAccountResponse = new CreateHistoryResponseDto();
        inAccountResponse.setId(rsaAlgorithm.encrypt(String.valueOf(savedInAccountHistory.getId()),Constants.CLIENT_PUBLIC_KEY));
        inAccountResponse.setTransactionId(rsaAlgorithm.encrypt(savedInAccountHistory.getTransactionId(), Constants.CLIENT_PUBLIC_KEY));
        inAccountResponse.setAccount(rsaAlgorithm.encrypt(savedInAccountHistory.getAccount(), Constants.CLIENT_PUBLIC_KEY));
        inAccountResponse.setHave(rsaAlgorithm.encrypt(String.valueOf(savedInAccountHistory.getHave()),Constants.CLIENT_PUBLIC_KEY));
        inAccountResponse.setTime(rsaAlgorithm.encrypt(String.valueOf(savedInAccountHistory.getTime()),Constants.CLIENT_PUBLIC_KEY));

        CreateHistoryResponseDto outAccountResponse = new CreateHistoryResponseDto();
        outAccountResponse.setId(rsaAlgorithm.encrypt(String.valueOf(savedOutAccountHistory.getId()),Constants.CLIENT_PUBLIC_KEY));
        outAccountResponse.setTransactionId(rsaAlgorithm.encrypt(savedOutAccountHistory.getTransactionId(), Constants.CLIENT_PUBLIC_KEY));
        outAccountResponse.setAccount(rsaAlgorithm.encrypt(savedOutAccountHistory.getAccount(), Constants.CLIENT_PUBLIC_KEY));
        outAccountResponse.setInDebt(rsaAlgorithm.encrypt(String.valueOf(savedOutAccountHistory.getInDebt()),Constants.CLIENT_PUBLIC_KEY));
        outAccountResponse.setTime(rsaAlgorithm.encrypt(String.valueOf(savedOutAccountHistory.getTime()),Constants.CLIENT_PUBLIC_KEY));

        List<CreateHistoryResponseDto> result = new ArrayList<>();
        result.add(inAccountResponse);
        result.add(outAccountResponse);
        return result;
    }
}
