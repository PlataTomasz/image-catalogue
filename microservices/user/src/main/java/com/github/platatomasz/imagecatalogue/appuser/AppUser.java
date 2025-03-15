package com.github.platatomasz.imagecatalogue.appuser;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String email;
	private String password;
	
	private String nickname;
	private String avatarFileName;
	
	public AppUser(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	@Override
	public void eraseCredentials() {
		this.email = null;
		this.password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Add proper roles
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	
}