package com.example.mediumclone.repository;

import com.example.mediumclone.model.Post;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>,
    PagingAndSortingRepository<Post, UUID> {

  @Transactional
  @Modifying
  @Query(value = "UPDATE `medium-clone`.post set activate = false where id=:postId", nativeQuery = true)
  void softDelete(UUID postId);

  @Query(value = "SELECT * from `medium-clone`.post where activate=true and id=:postId", nativeQuery = true)
  Optional<Post> findByPostId(UUID postId);

  @Query(value = "SELECT * from `medium-clone`.post where activate=true", nativeQuery = true)
  Page<Post> findAllPost(Pageable pageable);

  @Query(value = "select * from `medium-clone`.post WHERE  post.tittle  like concat('%',:tittle,'%') and activate=true ", nativeQuery = true)
  Page<Post> findByTittle(String tittle, Pageable pageable);

  @Query(value = "select *\n"
      + "from `medium-clone`.post\n"
      + "         join `medium-clone`.following on following.following_user = post.user_id\n"
      + "         join `medium-clone`.user u on u.id=following.user_id where u.user_name = :userName and u.activate=true and post.activate=true", nativeQuery = true)
  Page<Post> getFollowersPost(String userName,Pageable pageable);
}
