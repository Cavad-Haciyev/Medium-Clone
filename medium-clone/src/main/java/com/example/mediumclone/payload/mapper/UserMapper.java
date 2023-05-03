package com.example.mediumclone.payload.mapper;

import com.example.mediumclone.model.User;
import com.example.mediumclone.payload.request.ChangePassword;
import com.example.mediumclone.payload.request.UserRequest;
import com.example.mediumclone.payload.response.FollowingResponse;
import com.example.mediumclone.payload.response.UserDetailsResponse;
import com.example.mediumclone.payload.response.UserPostResponse;
import com.example.mediumclone.payload.response.UserRegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

  User mapToEntity(UserRequest request);

  UserPostResponse mapToUserPostResponse(User user);

  User mapToUser(UserPostResponse userPostResponse);

  UserDetailsResponse mapToUserDetails(User user);

  UserRegistrationResponse mapToRegistrationResponse(User user);

  FollowingResponse mapToFollowing(User user);

  User mapToChangePassword(ChangePassword password);
}
