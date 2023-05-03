package com.example.mediumclone.service;

import com.example.mediumclone.exception.ConfirmPasswordNotEqualsException;
import com.example.mediumclone.exception.TokenExpiredException;
import com.example.mediumclone.exception.UserNotFoundException;
import com.example.mediumclone.model.ForgotPasswordToken;
import com.example.mediumclone.model.User;
import com.example.mediumclone.payload.mapper.UserMapper;
import com.example.mediumclone.payload.request.ChangePassword;
import com.example.mediumclone.repository.ForgotPasswordRepository;
import com.example.mediumclone.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordTokenService {

  private final ForgotPasswordRepository repository;
  private final UserService userService;
  private final UserRepository userRepository;

  public void forgotPassword(String email) {
    User user = userService.forgotPassword(email);
    if (user == null) {
      throw new UserNotFoundException();
    } else {
      LocalDateTime createTime = LocalDateTime.now();
      LocalDateTime expDate = createTime.plusMinutes(15);
      ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
      forgotPasswordToken.setUser(user);
      forgotPasswordToken.setToken(UUID.randomUUID());
      forgotPasswordToken.setCreatedDate(createTime);
      forgotPasswordToken.setExpireDate(expDate);
      repository.save(forgotPasswordToken);
    }
  }


  public void changePassword(UUID token, ChangePassword password) {
    ForgotPasswordToken byToken = repository.findByToken(token);
    User user = byToken.getUser();
    if (byToken.getExpireDate().isBefore(LocalDateTime.now())) {
      throw new TokenExpiredException();
    } else {
      if (password.confirmPassword().equals(password.confirmPassword())) {
        user.setPassword(password.password());
        userRepository.save(user);
        UserMapper.MAPPER.mapToChangePassword(password);
      } else {
        throw new ConfirmPasswordNotEqualsException();
      }

    }
  }
}
