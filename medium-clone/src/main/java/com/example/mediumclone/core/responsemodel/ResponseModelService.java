package com.example.mediumclone.core.responsemodel;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModelService {

  private LocalDateTime timeStamp;
  private Object object;
  private String message;
  private HttpStatus status;
}
