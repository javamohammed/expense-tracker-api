package com.mido.expensetracker.repositories;

import com.mido.expensetracker.entity.User;
import com.mido.expensetracker.exception.AuthException;

public interface UserRepository {

	public Integer create(String firstName, String lastName, String email, String password) throws AuthException;
	
	public User findByEmailAndPassword(String email, String password) throws AuthException; 
	
	public Integer getCountByEmail(String email) throws AuthException;
	
	public User fidById(int UserId) throws AuthException;
}
