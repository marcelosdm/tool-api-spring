package com.marcelo.tools.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.tools.entity.User;
import com.marcelo.tools.error.NotFoundError;
import com.marcelo.tools.repository.UserRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository userRepository;

	/**
	 * Get all users
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
	}

	/**
	 * Get user by id
	 * 
	 * @param userId
	 * @return
	 * @throws NotFoundError
	 */
	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) throws NotFoundError {
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundError("No user found with the id " + userId)));
	}

	/**
	 * Create new user
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user));
	}

	/**
	 * Update a user
	 * 
	 * @param userId
	 * @param userDetails
	 * @return
	 * @throws NotFoundError
	 */
	@PutMapping("{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User userDetails)
			throws NotFoundError {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundError("No user found with the id " + userId));

		user.setName(userDetails.getName());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());

		User updateUser = userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body(updateUser);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) throws NotFoundError {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundError("No user found with the id " + userId));

		userRepository.delete(user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
