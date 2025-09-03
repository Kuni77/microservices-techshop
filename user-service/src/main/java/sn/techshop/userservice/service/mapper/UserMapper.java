package sn.techshop.userservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.techshop.userservice.domain.User;
import sn.techshop.userservice.service.dto.UserDto;
import sn.techshop.userservice.service.dto.UserRegistrationDto;

@Mapper
public interface UserMapper {
    @Mapping(source = "CreatedDate", target = "createdAt")
    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    User toUser(UserDto userDto);

    // Mapping depuis UserRegistrationDto
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    User fromRegistrationDto(UserRegistrationDto dto);
}
