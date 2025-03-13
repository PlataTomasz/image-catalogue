package com.github.platatomasz.imagecatalogue.appuser;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
	@Autowired
	private final AppUserRepository userRepository;

	public AppUserService(AppUserRepository userRepository) {
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

	public void updateUser(AppUser user) throws NoSuchElementException {
		// User user = userRepository.findById(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> {
			throw new UsernameNotFoundException("User with email: " + email + " does not exist!");
		});
	}
}
