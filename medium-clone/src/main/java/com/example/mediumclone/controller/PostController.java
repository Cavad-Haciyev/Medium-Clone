package com.example.mediumclone.controller;

import com.example.mediumclone.core.responsemodel.ResponseModel;
import com.example.mediumclone.payload.request.PostRequest;
import com.example.mediumclone.service.PostService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posts")
public class PostController {

  private final PostService postService;

  @GetMapping("/post")
  public ResponseEntity<Page<Object>> getAllPost(@RequestParam("pageSize") Integer pageSize,
      @RequestParam("pageNo") Integer pageNo
  ) {

    return ResponseModel
        .responseBuilderPage(LocalDateTime.now(),
            postService.getAllPost(pageSize, pageNo),
            "Successfully",
            HttpStatus.OK);

  }

  @GetMapping("/followersPost") // Get My Profile Details
  public ResponseEntity<Page<Object>> getFollowersPost(@RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("pageNo") Integer pageNo) {
    return ResponseModel
        .responseBuilderPage(LocalDateTime.now(),
            postService.getFollowersPost(pageSize,pageNo),
            "Successfully",
            HttpStatus.OK);
  }


  @GetMapping("/tittle")
  public ResponseEntity<Page<Object>> getPostByTittle(@RequestParam(value = "tittle") String tittle,
                                                      @RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("pageNo") Integer pageNo) {
    return ResponseModel
        .responseBuilderPage(LocalDateTime.now(),
            postService.getPostByTittle(tittle, pageSize, pageNo),
            "Successfully",
            HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getPostById(@PathVariable UUID id) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            postService.getPostById(id),
            "Successfully",
            HttpStatus.OK);

  }


  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,
      MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public ResponseEntity<Object> createPost(@RequestPart("file") MultipartFile file,
                                            @RequestPart("image") @Valid PostRequest request)throws IOException {

    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            postService.createPost(file, request),
            "Successfully",
            HttpStatus.CREATED);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            postService.deletePost(id),
            "Successfully",
            HttpStatus.OK);
  }
  @GetMapping("/likeIt/{postId}")
  public ResponseEntity<Object> likeIt(@PathVariable UUID postId){
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            postService.likeIt(postId),
            "Successfully",
            HttpStatus.OK);
  }

}
