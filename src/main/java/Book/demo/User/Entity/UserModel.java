package Book.demo.User.Entity;

import Book.demo.Books.Entity.BooksModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Table(name = "tb_user")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "username")
    private String userName;

    @Column (name = "age")
    private Integer userAge;

    @Column (name = "gmail", unique = true)
    private String userGmail;

    @OneToMany(mappedBy = "userModel")
    @JsonManagedReference
    private List<BooksModel> booksModels = new ArrayList<>();

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userGmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
