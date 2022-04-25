package springboot.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springboot.config.TestDataSourceConfig;
import springboot.dto.BookDto;
import springboot.entity.Book;
import springboot.service.BookService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    private void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void listBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Let Us C", "Yashwanth","Programming"));
        books.add(new Book("Data Structures","Sai","Programming"));
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/books/list")).andExpect(status().isOk());
    }

    @Test
    void showForm() throws Exception {
        mockMvc.perform(get("/books/add")).andExpect(status().isOk());
    }

    @Test
    void saveBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("Data Structures");
        bookDto.setAuthor("Shivkesh");
        bookDto.setCategory("PROGRAMMING");
        mockMvc.perform(post("/books/save")).andExpect(status().isOk());
        mockMvc.perform(post("/books/save").flashAttr("book",bookDto)).andExpect(status().is3xxRedirection());
    }

    @Test
    void updateBook() throws Exception {
        Book theBook = new Book(3,"OOPS","Rushikesh","Programming");
        when(bookService.findById(3)).thenReturn(theBook);
        mockMvc.perform(get("/books/update").param("bookId","3")).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        Book theBook = new Book(3,"OOPS","Rushikesh","Programming");
        when(bookService.findById(3)).thenReturn(theBook);
        mockMvc.perform(get("/books/delete").param("bookId","3")).andExpect(status().is3xxRedirection());
    }

    @Test
    void searchBook() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book("One Piece", "Eiichiro Oda","Comic"));
        when(bookService.findAllByCategory("Comic")).thenReturn(books);
        mockMvc.perform(get("/books/search").param("category","Comic")).andExpect(status().isOk());
    }
}
