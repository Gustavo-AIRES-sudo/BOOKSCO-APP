package Book.demo.User.Mapper;

import Book.demo.User.Entity.DTO.UserLoginRequest;
import Book.demo.User.Entity.DTO.UserLoginResponse;
import Book.demo.User.Entity.UserModel;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    public static UserModel toUserModel(UserLoginRequest userLoginRequest){
        return UserModel
                .builder()
                .userGmail(userLoginRequest.userGmail())
                .userPassword(userLoginRequest.userPassword())
                .build();
    }

    public static UserLoginResponse toResponse(UserModel userModel){
        return UserLoginResponse
                .builder()
                .userName(userModel.getUsername())
                .userAge(userModel.getUserAge())
                .userGmail(userModel.getUserGmail())
                .booksModels(userModel.getBooksModels())
                .build();
    }
}
