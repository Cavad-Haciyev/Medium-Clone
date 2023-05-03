package com.example.mediumclone.service;

import com.example.mediumclone.exception.CommentNotFoundException;
import com.example.mediumclone.model.Comment;
import com.example.mediumclone.model.Post;
import com.example.mediumclone.model.User;
import com.example.mediumclone.payload.mapper.CommentMapper;
import com.example.mediumclone.payload.mapper.UserMapper;
import com.example.mediumclone.payload.request.CommentRequest;
import com.example.mediumclone.payload.response.CommentResponse;
import com.example.mediumclone.payload.response.UserPostResponse;
import com.example.mediumclone.repository.CommentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostService postService;
  private final UserService userService;

  public Page<CommentResponse> getAllComment(int pageSize, int pageNo) {
    Pageable pageable = PageRequest.of(pageSize, pageNo);
    return commentRepository.findAllComment(pageable)
        .map(CommentMapper.MAPPER::mapToResponse);
  }

  public CommentResponse getCommentById(UUID id) {
    Comment comment = commentRepository
        .findByCommentId(id)
        .orElseThrow(
            CommentNotFoundException::new);
    return CommentMapper.MAPPER.mapToResponse(comment);
  }

  public CommentResponse createComment(CommentRequest request) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    UserPostResponse userByName = userService.getUserByName(name);
    User user = UserMapper.MAPPER.mapToUser(userByName);
    UUID id = user.getId();
    Post postByIdForComment = postService.getPostByIdForComment(request.postId());
    Comment comment = CommentMapper.MAPPER.mapToEntity(request);
    comment.setActivate(true);
    comment.setPost(postByIdForComment);
    comment.setId(id);
    Comment save = commentRepository.save(comment);
    return CommentMapper.MAPPER.mapToResponse(save);

  }

  public String deleteComment(UUID commentId) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    UserPostResponse userByName = userService.getUserByName(name);
    User user = UserMapper.MAPPER.mapToUser(userByName);
    Comment comment = commentRepository
        .findById(commentId)
        .orElseThrow(CommentNotFoundException::new);
    Post post = comment.getPost();
    User user1 = post.getUser();
    if (user.getId().equals(comment.getUserId()) || user1.getId().equals(user.getId())) {
      commentRepository.softDelete(comment.getId());
    }
    return "Comment can not be Deleted!!";

  }

}
