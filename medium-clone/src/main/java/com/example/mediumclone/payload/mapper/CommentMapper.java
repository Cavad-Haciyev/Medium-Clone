package com.example.mediumclone.payload.mapper;

import com.example.mediumclone.model.Comment;
import com.example.mediumclone.payload.request.CommentRequest;
import com.example.mediumclone.payload.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

  CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

  Comment mapToEntity(CommentRequest request);

  CommentResponse mapToResponse(Comment comment);

}
