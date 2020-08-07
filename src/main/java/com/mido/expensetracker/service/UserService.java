package com.mido.expensetracker.service;

import com.mido.expensetracker.entity.User;
import com.mido.expensetracker.exception.AuthException;

public interface UserService {
	
	public User validateUser(String email, String password) throws AuthException;
	public User register(String firstName, String lastName, String email, String password) throws AuthException;

}
