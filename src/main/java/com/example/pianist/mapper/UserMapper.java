package com.example.pianist.mapper;

import com.example.pianist.dao.entity.UserEntity;
import com.example.pianist.model.request.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "progress", ignore = true)
    @Mapping(target = "currentLevel", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "role", ignore = true)
    UserEntity dtoToEntity(UserRegisterRequest dto);
}