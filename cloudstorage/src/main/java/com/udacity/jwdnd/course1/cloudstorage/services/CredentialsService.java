package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class CredentialsService {

	CredentialsMapper credentialsMapper;
	public final Logger logger = LoggerFactory.getLogger(CredentialsService.class);
	UserMapper userMapper;
	HashService hashService;
	EncryptionService encryptionService;

	CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, HashService hashService,
			EncryptionService encryptionService) {
		this.credentialsMapper = credentialsMapper;
		this.userMapper = userMapper;
		this.hashService = hashService;
		this.encryptionService = encryptionService;

	}

	public boolean isUsernameAvailable(String username) {
		return userMapper.getUser(username) == null;
	}

	public boolean storeCredentials(Credentials cred, User user) {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		try {
			String encodedKey = Base64.getEncoder().encodeToString(key);
			String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), encodedKey);

			return credentialsMapper.insert(new Credentials(null, cred.getUsername(), encodedKey, encryptedPassword,
					cred.getUrl(), user.getUserId()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Credentials getCredentials(Integer credId) {
		try {
			Credentials credential = credentialsMapper.viewCredentials(credId);
			String password = "";
			password = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
			credential.setPassword(password);
			return credential;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public List<Credentials> getAllCredentials(Integer userId) {
		return credentialsMapper.viewAllCredentials(userId);
	}

	public boolean deleteCredential(Integer credId) {
		return credentialsMapper.delete(credId);
	}

	public boolean update(Credentials cred) {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		try {
			String encodedKey = Base64.getEncoder().encodeToString(key);
			String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), encodedKey);
			return credentialsMapper.update(new Credentials(cred.getCredentialid(), cred.getUsername(), encodedKey,
					encryptedPassword, cred.getUrl(), cred.getUserId()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
