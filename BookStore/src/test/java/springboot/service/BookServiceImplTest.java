package springboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.dao.BookRepository;
import springboot.entity.Book;

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
        books.add(new Book("Let Us C", "Yashwanth"));
        books.add(new Book("The Rainbow","Sai"));
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> bookList = bookService.findAll();
        assertEquals(books.size(),bookList.size());
        // to make sure book repository is used
        verify(bookRepository).findAll();
    }

    @Test
    void findById() throws RuntimeException {
        when(bookRepository.findById(2)).thenReturn(Optional.of(new Book("The Rainbow", "Sai")));
        Book book = bookService.findById(2);
        assertEquals("The Rainbow",book.getTitle());
        assertEquals("Sai",book.getAuthor());
        verify(bookRepository).findById(2);
    }

    @Test
    void save() {
        Book book = new Book("Data Structures","Shivkesh");
        bookService.save(book);
        verify(bookRepository).save(book);
    }

    @Test
    void deleteById(){
        Book theBook = new Book(3,"OOPS","Rushikesh");
        when(bookRepository.findById(3)).thenReturn(Optional.of(theBook));
        bookService.deleteById(theBook.getId());
        verify(bookRepository).deleteById(theBook.getId());
    }
}