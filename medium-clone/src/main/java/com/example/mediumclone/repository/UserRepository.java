package com.example.mediumclone.repository;

import com.example.mediumclone.model.User;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {

  User findByUserName(String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE `medium-clone`.user set activate = false where id=:userId", nativeQuery = true)
  void softDelete(UUID userId);

  @Query(value = "SELECT * from `medium-clone`.user where activate=true and id=:userId", nativeQuery = true)
  Optional<User> getUserById(@Param("userId") UUID userId);

  Optional<User> getUserByUserName(String userName);

  @Query(value = "select  * from `medium-clone`.user join  `medium-clone`.post on user.id = post.user_id and post.activate=true", nativeQuery = true)
  Page<User> getAllUser(Pageable pageable);

  Optional<User> findByEmail(String email);


}
