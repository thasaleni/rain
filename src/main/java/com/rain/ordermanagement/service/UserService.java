package com.rain.ordermanagement.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rain.ordermanagement.dto.JwtResponse;
import com.rain.ordermanagement.model.Account;
import com.rain.ordermanagement.model.Role;
import com.rain.ordermanagement.model.RoleType;
import com.rain.ordermanagement.model.User;
import com.rain.ordermanagement.repository.RoleRepository;
import com.rain.ordermanagement.repository.UserRepository;
import com.rain.ordermanagement.security.JwtUtils;


@Service
public class UserService {
	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;
	private JwtUtils jwtUtils;

	UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User addUser(User user) {
		User created = null;
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("User already exists");
		} else {
			user.setPassword(encoder.encode(user.getPassword()));
			Optional<Role> userOption = roleRepository.findByType(RoleType.ROLE_USER);
			if (userOption.isEmpty()) {
				Role role = roleRepository.save(new Role(RoleType.ROLE_USER));
				user.setRoles(Collections.singleton(role));

			} else {
				user.setRoles(Collections.singleton(userOption.get()));
			}
			Account account = new Account(user);
			user.setAccount(account);
			created = userRepository.save(user);
		}
		return created;

	}

	
	public JwtResponse login(User user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		User userDetails = (User) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
