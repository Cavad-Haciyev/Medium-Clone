package com.example.mediumclone.payload.mapper;

import com.example.mediumclone.model.Post;
import com.example.mediumclone.payload.request.PostRequest;
import com.example.mediumclone.payload.response.FollowingPostResponse;
import com.example.mediumclone.payload.response.PostResponse;
import com.example.mediumclone.payload.response.SavedPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  PostMapper MAPPER = Mappers.getMapper(PostMapper.class);

  Post mapToEntity(PostRequest request);

  PostResponse mapToResponse(Post post);

  SavedPostResponse mapToSavedPostResponse(Post post);

  FollowingPostResponse mapToFollowingPostResponse(Post post);

}
