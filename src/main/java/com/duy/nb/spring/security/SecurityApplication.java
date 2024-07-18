package com.duy.nb.spring.security;

import com.duy.nb.spring.security.algorithms.AESAlgorithm;
import com.duy.nb.spring.security.algorithms.RSAAlgorithm;
import com.duy.nb.spring.security.untils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private RSAAlgorithm rsaAlgorithm;

	@Autowired
	private AESAlgorithm aesAlgorithm;

    public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String plainText = "Duydeeptry";

		System.out.println(Constants.DIVIDE_LINE);

		// Sử dụng RSA
		KeyPair keyPair = rsaAlgorithm.getKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		System.out.println(Constants.PUBLIC_KEY_TITLE + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println(Constants.PRIVATE_KEY_TITLE+ Base64.getEncoder().encodeToString(publicKey.getEncoded()));

		String encryptedTextByRSA = rsaAlgorithm.encrypt(plainText,publicKey);
		System.out.println(Constants.RSA_ENCRIPT_TITLE +encryptedTextByRSA);
		String decryptedTextByRSA = rsaAlgorithm.decrypt(encryptedTextByRSA, privateKey);
		System.out.println(Constants.RSA_DECRYPT_TITLE +decryptedTextByRSA);

		System.out.println(Constants.DIVIDE_LINE);


		//Sử dụng AES
		SecretKey secretKey = aesAlgorithm.getSecretKey();
		System.out.println(Constants.SECRET_KEY_TITLE + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
		String encryptedTextByAES = aesAlgorithm.encrypt(plainText,secretKey);
		System.out.println(Constants.AES_ENCRIPT_TITLE +encryptedTextByAES);
		String decryptedTextByAES = aesAlgorithm.decrypt(encryptedTextByAES, secretKey);
		System.out.println(Constants.AES_DECRYPT_TITLE +decryptedTextByAES);
	}

}
