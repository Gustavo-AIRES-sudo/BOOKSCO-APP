package Book.demo.User.Mapper;

import Book.demo.User.Entity.UserDTO;
import Book.demo.User.Entity.UserModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO map(UserModel userModel);
    UserModel map(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserDTO dto, @MappingTarget UserModel entity);
}
