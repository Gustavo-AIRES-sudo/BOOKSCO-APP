package Book.demo.Books.Repository;

import Book.demo.Books.Entity.BooksModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksModel, Long> {

}
