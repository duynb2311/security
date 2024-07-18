package com.duy.nb.spring.security.algorithms;

import com.duy.nb.spring.security.untils.Constants;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;
import java.util.Objects;

/**
 * Lớp định nghĩa các phương thức tạo key, mã hóa, giải mã bằng RSA
 */
@Component
public class RSAAlgorithm {
    public static final Logger LOGGER = LogManager.getLogger(RSAAlgorithm.class);
    private static KeyPairGenerator keyPairGenerator;

    static {
        try {
            if (keyPairGenerator == null) keyPairGenerator = KeyPairGenerator.getInstance(Constants.RSA_ALGORITHM_NAME);
            keyPairGenerator.initialize(Constants.RSA_KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        } catch (InvalidParameterException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Phương thức tạo cặp khóa RSA
     *
     * @return Cặp khóa public/private
     */
    public KeyPair getKeyPair() {
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Phương thức mã hóa RSA
     *
     * @param content   chuỗi cần mã hóa
     * @param publicKey khóa công khai
     * @return chuỗi đã được mã hóa hoặc chuỗi rỗng nếu catch exception
     */
    public String encrypt(String content, Key publicKey) {
        if (Objects.isNull(content) || Objects.isNull(publicKey)) {
            throw new IllegalArgumentException();
        }
        try {
            Cipher cipher = Cipher.getInstance(Constants.RSA_ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherContent = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(cipherContent);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (NoSuchPaddingException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (InvalidKeyException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (BadPaddingException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }
    }

    /**
     * Phương thức giải mã RSA
     *
     * @param cipherContent chuỗi cần giải mã
     * @param privateKey    khóa bí mật
     * @return chuỗi ban đầu hoặc chuỗi rỗng nếu catch exception
     */
    public String decrypt(String cipherContent, Key privateKey) {
        if (Objects.isNull(cipherContent) || Objects.isNull(privateKey)) {
            throw new IllegalArgumentException();
        }
        try {
            Cipher cipher = Cipher.getInstance(Constants.RSA_ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedContent = cipher.doFinal(Base64.getDecoder().decode(cipherContent.getBytes()));
            return new String(decryptedContent);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (NoSuchPaddingException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (InvalidKeyException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        } catch (BadPaddingException e) {
            LOGGER.error(e.getMessage());
            return Constants.EMPTY_STRING;
        }
    }
}
