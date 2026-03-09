package Book.demo.User.Entity.DTO;
import lombok.Builder;

@Builder
public record UserLoginRequest(String userGmail,
                        String userPassword){

}
