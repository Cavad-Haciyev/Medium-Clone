package com.example.mediumclone.repository;

import com.example.mediumclone.model.Comment;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

  @Transactional
  @Modifying
  @Query(value = "UPDATE `medium-clone`.comment set comment.activate=false where id=:commentId", nativeQuery = true)
  void softDelete(UUID commentId);

  @Query(value = "SELECT * from `medium-clone`.comment where activate=true and id=:commentId", nativeQuery = true)
  Optional<Comment> findByCommentId(UUID commentId);

  @Query(value = "SELECT * from `medium-clone`.comment where activate=true", nativeQuery = true)
  Page<Comment> findAllComment(Pageable pageable);
}
