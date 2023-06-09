package com.example.mediumclone.rabbitmq;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {

  private String messageId;
  private String message;
  private LocalDateTime messageDate;
}
