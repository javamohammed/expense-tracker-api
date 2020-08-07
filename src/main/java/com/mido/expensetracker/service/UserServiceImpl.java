package com.mido.expensetracker.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mido.expensetracker.entity.User;
import com.mido.expensetracker.exception.AuthException;
import com.mido.expensetracker.repositories.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository  userRepository;
	@Override
	public User validateUser(String email, String password) throws AuthException {
		if(email != null) email = email.toLowerCase();
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User register(String firstName, String lastName, String email, String password) throws AuthException {
		// 
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if(email != null) email = email.toLowerCase();
		if(!pattern.matcher(email).matches())
			throw new AuthException("Ivalid Email");
		Integer count = userRepository.getCountByEmail(email);
		
		if(count > 0)
			throw new AuthException("Email already ine use");
		Integer userId = userRepository.create(firstName, lastName, email, password);
		
		return userRepository.fidById(userId);
	}

}
