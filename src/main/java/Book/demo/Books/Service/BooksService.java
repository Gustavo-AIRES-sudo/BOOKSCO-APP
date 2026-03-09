package Book.demo.Books.Service;

import Book.demo.Books.Entity.BooksDTO;
import Book.demo.Books.Entity.BooksModel;
import Book.demo.Books.Mapper.BooksMapper;
import Book.demo.Books.Repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BooksService {

    @Autowired
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;

    public BooksService(BooksRepository booksRepository, BooksMapper booksMapper) {
        this.booksRepository = booksRepository;
        this.booksMapper = booksMapper;
    }

    public List<BooksDTO> titles(){
        List<BooksModel> allBooks = booksRepository.findAll();
        return allBooks.stream()
                .map(booksMapper::map)
                .collect(Collectors.toList());
    }

    public BooksDTO findTitleById(Long id){
        Optional<BooksModel> book = booksRepository.findById(id);
        return book.map(booksMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public boolean idVerify(Long id){
        return booksRepository.existsById(id);
    }

    public BooksDTO addTitle(BooksDTO booksDTO){
        BooksModel books = booksMapper.map(booksDTO);
        books = booksRepository.save(books);
        return booksMapper.map(books);
    }

    public void deleteTitle(Long id){

        if (booksRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        booksRepository.deleteById(id);
    }

    public BooksDTO alterBook(Long id, BooksDTO booksDTO){
        Optional<BooksModel> book = booksRepository.findById(id);

        if (book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found in DATABASE");
        }

        BooksModel updatingBook = book.get();
        booksMapper.updateBookFromDTO(booksDTO, updatingBook);
        BooksModel newBook = booksRepository.save(updatingBook);
        return booksMapper.map(newBook);
    }


}
