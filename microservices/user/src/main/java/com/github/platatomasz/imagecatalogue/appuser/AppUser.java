package com.github.platatomasz.imagecatalogue.appuser;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AppUser implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String username;
	private String password;
	
	private String nickname;
	private String avatarFileName;
	
	public AppUser(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}

	@Override
	public void eraseCredentials() {
		this.username = null;
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
		return username;
	}
	
	
}