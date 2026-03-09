package Book.demo.Books.Entity;

import Book.demo.User.Entity.UserModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "tb_books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "userModel")

public class BooksModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "book_url", unique = true)
    private String bookUrl;

    @Column(name = "synopsis", unique = true)
    private String synopsis;

    @Column(name = "author", unique = true)
    private String author;

    @ManyToOne
    @JoinColumn(name = "foreignkey_user_id")
    @JsonBackReference
    private UserModel userModel;

}
