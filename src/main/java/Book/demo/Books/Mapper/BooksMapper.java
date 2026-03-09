package Book.demo.Books.Mapper;

import Book.demo.Books.Entity.BooksDTO;
import Book.demo.Books.Entity.BooksModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


//TODO: ESTUDAR O QUE FOI IMPLEMENTADO NA CAMADA DO MAPPER.
@Mapper(componentModel = "spring")
public interface BooksMapper {


    BooksDTO map(BooksModel booksModel);
    BooksModel map(BooksDTO booksDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDTO(BooksDTO dto, @MappingTarget BooksModel entity);
}