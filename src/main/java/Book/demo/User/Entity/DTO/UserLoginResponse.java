package Book.demo.User.Entity.DTO;

import Book.demo.Books.Entity.BooksModel;
import lombok.Builder;

import java.util.List;

@Builder
public record UserLoginResponse(String userName,
                                String userGmail,
                                Integer userAge,
                                List<BooksModel> booksModels) {
}
