package com.duy.nb.spring.security.algorithms;

import com.duy.nb.spring.security.untils.Constants;

import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.security.*;
import java.util.Base64;
import java.util.Objects;

/**
 * Lớp định nghĩa các phương thức tạo key, mã hóa, giải mã bằng AES
 */
@Component
public class AESAlgorithm {
    public static final Logger LOGGER = LogManager.getLogger(AESAlgorithm.class);
    private static KeyGenerator keyGenerator = null;

    /**
     * Phương thức tạo khóa AES
     * @return Khóa bí mật
     */
    public SecretKey getSecretKey(){
        return keyGenerator.generateKey();
    }

    /**
     * Phương thức mã hóa AES
     * @param content chuỗi cần mã hóa
     * @param secretKey khóa bí mật
     * @return chuỗi đã được mã hóa hoặc chuỗi rỗng nếu catch exception
     */
    public String encrypt(String content, Key secretKey) {
        if(Objects.isNull(content)||Objects.isNull(secretKey)){
            throw new IllegalArgumentException();
        }
        try {
            Cipher cipher = Cipher.getInstance(Constants.AES_ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherContent = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(cipherContent);
        }catch (NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (NoSuchPaddingException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (InvalidKeyException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (IllegalBlockSizeException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (BadPaddingException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }
    }
    /**
     * Phương thức giải mã AES
     * @param cipherContent chuỗi cần giải mã
     * @param secretKey khóa bí mật
     * @return chuỗi ban đầu hoặc chuỗi rỗng nếu catch exception
     */
    public String decrypt(String cipherContent, Key secretKey) {
        if(Objects.isNull(cipherContent)||Objects.isNull(secretKey)){
            throw new IllegalArgumentException();
        }
        try {
            Cipher cipher = Cipher.getInstance(Constants.AES_ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedContent = cipher.doFinal(Base64.getDecoder().decode(cipherContent.getBytes()));
            return new String(decryptedContent);
        }catch (NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (NoSuchPaddingException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (InvalidKeyException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (IllegalBlockSizeException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }catch (BadPaddingException e){
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }
    }
    // Định nghĩa bộ khởi tạo khóa với tên thuật toán và kích thước khóa
    @PostConstruct
    private void init() {
        try {
            if(keyGenerator == null) keyGenerator = KeyGenerator.getInstance(Constants.AES_ALGORITHM_NAME);
            keyGenerator.init(Constants.AES_KEY_SIZE);
        } catch(NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(InvalidParameterException e){
            LOGGER.error(e.getMessage());
        }
    }
}
