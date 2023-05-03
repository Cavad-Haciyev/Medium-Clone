package com.example.mediumclone.controller;

import com.example.mediumclone.payload.request.ChangePassword;
import com.example.mediumclone.service.ForgotPasswordTokenService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/fpt")
public class ForgotPasswordTokenController {

  private final ForgotPasswordTokenService service;

  @GetMapping("/token/{email}")
  private String forgotPassword(@PathVariable String email) {
    service.forgotPassword(email);
    return " Check Email";
  }

  @PutMapping("/token/{token}")
  public String changePassword(@PathVariable UUID token, @RequestBody ChangePassword password) {
    service.changePassword(token, password);
    return "Successfully Changed";
  }
}
