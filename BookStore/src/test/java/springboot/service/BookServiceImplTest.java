package springboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.dao.BookRepository;
import springboot.entity.Book;
import springboot.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void findAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Let Us C", "Yashwanth","Programming"));
        books.add(new Book("Data Structures","Sai","Programming"));
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> bookList = bookService.findAll();
        assertEquals(books.size(),bookList.size());
        // to make sure book repository is used
        verify(bookRepository).findAll();
    }

    @Test
    void findById(){
        when(bookRepository.findById(2)).thenReturn(Optional.of(new Book("Data Structures", "Sai","Programming")));
        Book book = bookService.findById(2);
        assertEquals("Data Structures",book.getTitle());
        assertEquals("Sai",book.getAuthor());
        assertEquals("Programming",book.getCategory());
        verify(bookRepository).findById(2);
        Optional<Book> result = Optional.empty();
        when(bookRepository.findById(999)).thenReturn(result);
        try {
            bookService.findById(999);
        }
        catch (RuntimeException exception){
            assertEquals("No Book found with Id: 999",exception.getMessage());
        }
    }

    @Test
    void save() {
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setAuthor("Shivkesh");
        book.setCategory("Programming");
        bookService.save(book);
        verify(bookRepository).save(book);
    }

    @Test
    void deleteById(){
        Book theBook = new Book(3,"OOPS","Rushikesh","Programming");
        when(bookRepository.findById(3)).thenReturn(Optional.of(theBook));
        bookService.deleteById(theBook.getId());
        verify(bookRepository).deleteById(theBook.getId());
    }

    @Test
    void findAllByCategory() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("One Piece", "Eiichiro Oda","Comic"));
        when(bookRepository.findAllByCategory("Comic")).
                thenReturn(books);
        List<Book> result = bookService.findAllByCategory("Comic");
        Book book = result.get(0);
        assertEquals("One Piece",book.getTitle());
        verify(bookRepository).findAllByCategory("Comic");
    }
}