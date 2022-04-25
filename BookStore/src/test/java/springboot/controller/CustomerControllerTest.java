package springboot.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springboot.config.TestDataSourceConfig;
import springboot.dto.BookDto;
import springboot.dto.CustomerDto;
import springboot.entity.Book;
import springboot.entity.Customer;
import springboot.service.BookService;
import springboot.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    private void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void listCustomer() throws Exception {
        Book theBook = new Book(1,"OOPS","Rushikesh","Programming");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1,"Shivkesh","9948892312"));
        customerList.add(new Customer(2,"Rushikesh","7702644649"));
        theBook.setCustomerList(customerList);
        when(bookService.findById(1)).thenReturn((theBook));
        mockMvc.perform(get("/customers/list").param("bookId","1")).andExpect(status().isOk());
    }

    @Test
    void addCustomer() throws Exception {
        Book theBook = new Book(1,"OOPS","Rushikesh","Programming");
        Customer customer = new Customer(1,"Shivkesh","9948892312");
        theBook.addCustomer(customer);
        when(bookService.findById(1)).thenReturn((theBook));
        mockMvc.perform(get("/customers/add").param("bookId","1")).andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        Book book = new Book("Data Structures","Shivkesh","Programming");
        BookDto bookDto = new BookDto(2,"Data Structures","Shivkesh","Programming");
        CustomerDto customerDto = new CustomerDto(3,"Tharun","9876542312");
        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setId(3);
        customerDto1.setName("");
        customerDto1.setMobileNumber("");
        Customer customer = new Customer(3,"Tharun","9876542312");
        book.addCustomer(customer);
        when(bookService.findById(2)).thenReturn((book));
        mockMvc.perform(post("/customers/save").flashAttr("customer",customerDto1).flashAttr("book",bookDto).param("bookId","2")).andExpect(status().isOk());
        mockMvc.perform(post("/customers/save").flashAttr("customer", customerDto)
                .flashAttr("book",bookDto).param("bookId","2")).andExpect(status().is3xxRedirection());
    }

    @Test
    void update() throws Exception {
        Book book = new Book(2,"Data Structures","Shivkesh","Programming");
        Customer customer = new Customer(3,"Tharun","9876542312");
        when(bookService.findById(2)).thenReturn((book));
        when(customerService.findById(3)).thenReturn((customer));
        mockMvc.perform(get("/customers/update").param("bookId","2").param("customerId","3"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        Book book = new Book("Data Structures","Shivkesh","Programming");
        BookDto bookDto = new BookDto(2,"Data Structures","Shivkesh","Programming");
        CustomerDto customerDto = new CustomerDto(3,"Tharun","9876542312");
        CustomerDto customerDto1 = new CustomerDto(3,"","");
        Customer customer = new Customer(3,"Tharun","9876542312");
        book.addCustomer(customer);
        when(customerService.findById(3)).thenReturn((customer));
        mockMvc.perform(post("/customers/update-customer").flashAttr("customer", customerDto1)
                .flashAttr("book",bookDto).param("bookId","2")).andExpect(status().isOk());
        mockMvc.perform(post("/customers/update-customer").flashAttr("customer", customerDto)
                .flashAttr("book",bookDto).param("bookId","2")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        Book book = new Book("Data Structures","Shivkesh","Programming");
        Customer customer = new Customer(3,"Tharun","9876542312");
        when(bookService.findById(2)).thenReturn((book));
        mockMvc.perform(get("/customers/delete").param("bookId","2").param("customerId","3"))
                .andExpect(status().is3xxRedirection());
    }
}