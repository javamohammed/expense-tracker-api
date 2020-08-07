package com.mido.expensetracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.RowMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mido.expensetracker.entity.User;
import com.mido.expensetracker.exception.AuthException;


@Repository
public class UserRepositoyImpl implements UserRepository {

	private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
	
	public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
		
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, hashedPassword);
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("USER_ID");
		} catch (Exception e) {
			throw new AuthException("Invalid details. Failed to create account");
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws AuthException {
		try {
			
			User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[] {email}, userRowMapper);
			
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new AuthException("Invalid Email/passwod");
			return user;
			
		} catch (Exception e) {
			throw new AuthException("Invalid Email/passwod");
		}
	}

	@Override
	public Integer getCountByEmail(String email) throws AuthException {
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
	}

	@Override
	public User fidById(int UserId) throws AuthException {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{UserId}, userRowMapper);
	}
	
	private RowMapper<User>  userRowMapper = ((rs, rowNum) -> {
		return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
	});
}