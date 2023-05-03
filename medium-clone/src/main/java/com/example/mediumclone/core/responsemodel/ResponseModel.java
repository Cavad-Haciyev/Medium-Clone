package com.example.mediumclone.core.responsemodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseModel {

  public static ResponseEntity<Object> responseBuilder(LocalDateTime timeStamp,
      Object responseObject,
      String message, HttpStatus status
  ) {
    ResponseModelService response = ResponseModelService.builder()
        .timeStamp(timeStamp)
        .object(responseObject)
        .message(message)
        .status(status)
        .build();
    return new ResponseEntity<>(response, status);

  }

  public static ResponseEntity<List<Object>> responseBuilderaList(LocalDateTime timeStamp,
      Object responseObject,
      String message, HttpStatus status
  ) {
    List<Object> list = new ArrayList<>();
    ResponseModelService response = ResponseModelService.builder()
        .timeStamp(timeStamp)
        .object(responseObject)
        .message(message)
        .status(status)
        .build();
    list.add(response);
    return new ResponseEntity<>(list, status);

  }

  public static ResponseEntity<Page<Object>> responseBuilderPage(LocalDateTime timeStamp,
      Object responseObject,
      String message, HttpStatus status
  ) {
    List<Object> list = new ArrayList<>();
    ResponseModelService response = ResponseModelService.builder()
        .timeStamp(timeStamp)
        .object(responseObject)
        .message(message)
        .status(status)
        .build();
    list.add(response);
    Page page = new PageImpl(list);
    return new ResponseEntity<>(page, status);

  }
}
