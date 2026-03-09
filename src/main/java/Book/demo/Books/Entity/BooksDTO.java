package Book.demo.Books.Entity;

import Book.demo.User.Entity.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BooksDTO {

    private Long id;

    private String title;

    private String bookUrl;

    private String synopsis;

    private String author;

    private UserModel userModel;

}
