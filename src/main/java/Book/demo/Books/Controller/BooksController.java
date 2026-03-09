package Book.demo.Books.Controller;

import Book.demo.Books.Entity.BooksDTO;
import Book.demo.Books.Mapper.BooksMapper;
import Book.demo.Books.Service.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private final BooksService booksService;
    private final BooksMapper booksMapper;

    public BooksController(BooksService booksService, BooksMapper booksMapper) {
        this.booksService = booksService;
        this.booksMapper = booksMapper;
    }

    @GetMapping("/welcome")
    @Operation(summary = "Welcome message to library", description = "this route basically sends a message to the new user in the library.")
    public String welcome (){
        return "Welcome to the your library";
    }

    @GetMapping("/titles")
    @Operation(summary = "List all books of the library", description = "this route show all titles of library, returning the informations of the books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "body of all books found")
    })
    public ResponseEntity<@NonNull List<BooksDTO>> showAllTitles (){
        List<BooksDTO> findAll = booksService.titles();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(findAll);
    }

    @GetMapping("/{id}")
    @Operation(summary = "List a book by id", description = "this route expects an id as a parameter. When the request is valid, returns the book body JSON.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "body")
    })
    public ResponseEntity<@NonNull BooksDTO> showById (
            @Parameter(description = "ID of the book to be retrieved")
            @PathVariable Long id
    ){
        BooksDTO dtoBook = booksService.findTitleById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtoBook);
    }

    @PostMapping("/add")
    @Operation(summary = "Add book route", description = "this route adds a book in the database, provided the user fill in the body request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "creates a book, returning a string message with the new book infos."),
            @ApiResponse(responseCode = "400", description = "error in the request of book, like: syntax, corrupted data, etc.")
    })
    public ResponseEntity<?> addBook (@RequestBody BooksDTO booksDTO){
        BooksDTO newBook = booksService.addTitle(booksDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("New book added. Infos: " + newBook);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete route", description = "deletes a book when the parameter is valid. To the parameter be valid, the id of book needs to be in the database. Returns a string message if the operation is valid. If is not, returns an error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation of delete book was a sucess"),
            @ApiResponse(responseCode = "404", description = "id not found")
    })
    public ResponseEntity<Map<String, String>> deleteBook (
            @Parameter(description = "ID of the book to be deleted")
            @PathVariable Long id
    ){
        booksService.deleteTitle(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Book deleted successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/alter/{id}")
    @Operation(summary = "Alter book infos route", description = "this route alters the information of book, provided the request is valid. To be valid, needs an existing id and a valid request body. returns a string message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation of alter book infos was a sucess"),
            @ApiResponse(responseCode = "404", description = "id not found")
    })
    public ResponseEntity<String> alterBook (
            @Parameter(description = "ID of the book to be updated")
            @PathVariable Long id,
            @RequestBody BooksDTO booksdto
    ){
        BooksDTO savedBook = booksService.alterBook(id, booksdto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Altered info of the book " + savedBook.getTitle());
    }
}