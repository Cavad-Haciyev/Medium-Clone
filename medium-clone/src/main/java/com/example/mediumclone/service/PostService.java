package com.example.mediumclone.service;


import com.example.mediumclone.core.utility.ImageUtility;
import com.example.mediumclone.exception.PostNotFoundException;
import com.example.mediumclone.model.Post;
import com.example.mediumclone.model.User;
import com.example.mediumclone.payload.mapper.PostMapper;
import com.example.mediumclone.payload.mapper.UserMapper;
import com.example.mediumclone.payload.request.PostRequest;
import com.example.mediumclone.payload.response.FollowingPostResponse;
import com.example.mediumclone.payload.response.PostResponse;
import com.example.mediumclone.payload.response.UserPostResponse;
import com.example.mediumclone.repository.PostRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserService userService;

  public Page<PostResponse> getAllPost(int pageSize, int pageNo) {

    Pageable pageable = PageRequest.of(pageSize, pageNo);
    Page<Post> allPost = postRepository.findAllPost(pageable);
    return allPost.map(PostMapper.MAPPER::mapToResponse);

  }

  public Page<FollowingPostResponse> getFollowersPost(int pageSize, int pageNo) {
    Pageable pageable = PageRequest.of(pageSize, pageNo);
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    List<FollowingPostResponse> followingPostResponses = new ArrayList<>();
    Page<Post> followersPost = postRepository.getFollowersPost(name,pageable);
    for (Post post : followersPost) {
      FollowingPostResponse followingPostResponse = PostMapper.MAPPER.mapToFollowingPostResponse(
          post);
      followingPostResponses.add(followingPostResponse);
    }
    Page page = new PageImpl(followingPostResponses);
    return page;
  }


  public Page<PostResponse> getPostByTittle(String tittle, int pageSize, int pageNo) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("tittle").descending());
    Page<Post> findByTittle = postRepository.findByTittle(tittle, pageable);

    return findByTittle.map(post -> PostMapper.MAPPER.mapToResponse(post));

  }
  public String likeIt(UUID postId){
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    UserPostResponse userByName = userService.getUserByName(name);
    User user = UserMapper.MAPPER.mapToUser(userByName);

    Post post = postRepository
        .findById(postId)
        .orElseThrow(() -> new PostNotFoundException());
    post.getPostLikes().add(user.getId());
    postRepository.save(post);
    return "Successfully Like It";

  }

  public PostResponse getPostById(UUID id) {
    Post post = postRepository.
        findByPostId(id).
        orElseThrow(() -> new PostNotFoundException());
    post.setImage(ImageUtility.decompressImage(post.getImage()));
    return PostMapper.MAPPER.mapToResponse(post);
  }


  public PostResponse createPost(MultipartFile file, PostRequest request) throws IOException {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    UserPostResponse userByName = userService.getUserByName(name);
    User user = UserMapper.MAPPER.mapToUser(userByName);
    Post post = PostMapper.MAPPER.mapToEntity(request);
    post.setActivate(true);
    post.setUser(user);
    post.setImageName(file.getOriginalFilename());
    post.setImageType(file.getContentType());
    post.setImage(ImageUtility.compressImage(file.getBytes()));
    postRepository.save(post);
    Post save = postRepository.save(post);
    return PostMapper.MAPPER.mapToResponse(save);

  }


  public String deletePost(UUID id) {
    Post post = postRepository
        .findById(id)
        .orElseThrow(PostNotFoundException::new);
    postRepository.softDelete(post.getId());
    return "Successfully Deleted";
  }


  public Post getPostByIdForComment(UUID id) {
    Post post = postRepository
        .findByPostId(id)
        .orElseThrow(PostNotFoundException::new);
    return post;
  }


}
