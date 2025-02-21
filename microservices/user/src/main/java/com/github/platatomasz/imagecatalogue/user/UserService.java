package com.github.platatomasz.imagecatalogue.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Iterable<AppUser> getAllUsers() {
		return userRepository.findAll();
	}
	
	public void addUser(AppUser user) {
		userRepository.save(user);
	}

	public Optional<AppUser> getUserById(Long id) {
		return userRepository.findById(id);
	}
}
