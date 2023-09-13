package com.example.blogplatform.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.blogplatform.auth.RegisterDto;
import com.example.blogplatform.exception.errors.ConflictException;
import com.example.blogplatform.exception.errors.NotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public Optional<User> findByEmail(String email) {
    Optional<User> exist = this.userRepository.findByEmail(email);
    return Optional.ofNullable(exist.orElseThrow(() -> new NotFoundException("User not found")));
  }

  public User createUser(RegisterDto dto)  {
    Optional<User> exist = this.userRepository.findByEmail(dto.getEmail());
    if(exist.isPresent()) {
      throw new ConflictException("User already exists");
    }

    User user = User.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .build();

    return userRepository.save(user);
  }
}
