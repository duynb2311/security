package com.duy.nb.spring.security.controller;

import com.duy.nb.spring.security.algorithms.RSAAlgorithm;
import com.duy.nb.spring.security.dto.CreateHistoryResponseDto;
import com.duy.nb.spring.security.dto.TransactionDto;
import com.duy.nb.spring.security.service.TransactionHistoryService;
import com.duy.nb.spring.security.untils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1")
public class TransactionHistoryController {
    @Autowired
    private RSAAlgorithm rsaAlgorithm;

    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    /**
     * Controller nhận request tạo lịch sử giao dịch
     * @param transactionDto giả lập đã được mã hóa từ phía client với public key của server
     * @return danh sách 2 phần tử bao gồm lịch sử của tài khoản chuyển và nhận đã được mã hóa với public key của client
     */
    @PostMapping("/add-history")
    public List<CreateHistoryResponseDto> createTransaction(@RequestBody TransactionDto transactionDto){
        transactionDto.setTransactionId(rsaAlgorithm.encrypt(transactionDto.getTransactionId(), Constants.SERVER_PUBLIC_KEY));
        transactionDto.setInAccount(rsaAlgorithm.encrypt(transactionDto.getInAccount(), Constants.SERVER_PUBLIC_KEY));
        transactionDto.setOutAccount(rsaAlgorithm.encrypt(transactionDto.getOutAccount(), Constants.SERVER_PUBLIC_KEY));
        transactionDto.setValue(rsaAlgorithm.encrypt(transactionDto.getValue(), Constants.SERVER_PUBLIC_KEY));
        return transactionHistoryService.createTransaction(transactionDto);
    }
}
