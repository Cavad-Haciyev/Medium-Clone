package com.example.mediumclone.repository;

import com.example.mediumclone.model.ForgotPasswordToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long> {

  ForgotPasswordToken findByToken(UUID token);

}
