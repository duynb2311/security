package com.duy.nb.spring.security.untils;

import com.duy.nb.spring.security.algorithms.AESAlgorithm;
import com.duy.nb.spring.security.algorithms.RSAAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.*;

public class Constants {

    public static final Logger LOGGER = LogManager.getLogger(RSAAlgorithm.class);
    @Autowired
    private static RSAAlgorithm rsaAlgorithm;

    @Autowired
    private AESAlgorithm aesAlgorithm;
    public static final String RSA_ALGORITHM_NAME = "RSA";
    public static final Integer RSA_KEY_SIZE = 2048;

    private static KeyPairGenerator keyPairGenerator;

    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(Constants.RSA_ALGORITHM_NAME);
            keyPairGenerator.initialize(Constants.RSA_KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        } catch (InvalidParameterException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static KeyPair clientKey = keyPairGenerator.generateKeyPair();
    public static KeyPair serverKey = keyPairGenerator.generateKeyPair();

    public static final PublicKey CLIENT_PUBLIC_KEY = clientKey.getPublic();
    public static final PublicKey CLIENT_PRIVATE_KEY = clientKey.getPublic();
    public static final PublicKey SERVER_PUBLIC_KEY = serverKey.getPublic();
    public static final PublicKey SERVER_PRIVATE_KEY = serverKey.getPublic();

    public static final String DIVIDE_LINE = "__________________";
    public static final String EMPTY_STRING = "";


    public static final String PUBLIC_KEY_TITLE = "Public Key (Base64):";
    public static final String PRIVATE_KEY_TITLE = "Private Key (Base64):";
    public static final String RSA_ENCRIPT_TITLE = "Encrypted text by RSA:";
    public static final String RSA_DECRYPT_TITLE = "Decrypted text by RSA:";


    public static final String AES_ALGORITHM_NAME = "AES";
    public static final Integer AES_KEY_SIZE = 256;
    public static final String SECRET_KEY_TITLE = "AES Secret Key (Base64):";
    public static final String AES_ENCRIPT_TITLE = "Encrypted text by AES:";
    public static final String AES_DECRYPT_TITLE = "Decrypted text by AES:";
}
