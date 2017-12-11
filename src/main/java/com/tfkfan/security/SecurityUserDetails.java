package com.tfkfan.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import java.util.ArrayList;
import java.util.Collection;

public class SecurityUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	public SecurityUserDetails(User user) {
		if (user == null)
			return;
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setRole(user.getRole());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Role role = getRole();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
		authorities.add(authority);

		return authorities;
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
