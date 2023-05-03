package com.example.mediumclone.controller;

import com.example.mediumclone.payload.request.CommentRequest;
import com.example.mediumclone.payload.response.CommentResponse;
import com.example.mediumclone.core.responsemodel.ResponseModel;
import com.example.mediumclone.service.CommentService;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comments")
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/comment")
  public ResponseEntity<Page<CommentResponse>> getAllPost(@RequestParam Integer pageSize,
      @RequestParam Integer pageNo) {
    return ResponseEntity.ok(commentService.getAllComment(pageSize, pageNo));
  }


  @GetMapping("/{id}")
  public ResponseEntity<Object> getCommentById(@PathVariable UUID id) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            commentService.getCommentById(id),
            "Successfully",
            HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> createComment(@RequestBody @Valid CommentRequest request) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            commentService.createComment(request),
            "Successfully Created",
            HttpStatus.CREATED);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteComment(@PathVariable UUID id) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            commentService.deleteComment(id),
            "Successfully Deleted",
            HttpStatus.OK);

  }

}
