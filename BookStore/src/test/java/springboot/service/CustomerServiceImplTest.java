package springboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.dao.BookRepository;
import springboot.dao.CustomerRepository;
import springboot.entity.Book;
import springboot.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    BookRepository bookRepository;

    @Test
    void findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1,"Shivkesh","9948892312"));
        customerList.add(new Customer(2,"Rushikesh","7702644649"));
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> customers = customerService.findAll();
        assertEquals(customerList.size(),customers.size());
        // to make sure customer repository is used
        verify(customerRepository).findAll();
    }

    @Test
    void findById() {
        when(customerRepository.findById(2)).thenReturn(Optional.of(new Customer(2,"Sai", "8567342564")));
        Customer customer = customerService.findById(2);
        assertEquals("Sai",customer.getName());
        assertEquals("8567342564",customer.getMobileNumber());
        verify(customerRepository).findById(2);
        Optional<Customer> result = Optional.empty();
        when(customerRepository.findById(999)).thenReturn(result);
        try {
            customerService.findById(999);
        }
        catch (RuntimeException exception){
            assertEquals("No Customer found with Id: 999",exception.getMessage());
        }
    }

    @Test
    void findByNameAndMobileNumber() {
        when(customerRepository.findByNameAndMobileNumber("Sai","8567342564")).
                thenReturn(Optional.of((new Customer(2, "Sai", "8567342564"))));
        Optional<Customer> result = customerService.findByNameAndMobileNumber("Sai","8567342564");
        Customer customer = result.get();
        assertEquals(2,customer.getId());
        verify(customerRepository).findByNameAndMobileNumber("Sai","8567342564");
    }

    @Test
    void save() {
        Customer customer = new Customer();
        customer.setId(3);
        customer.setName("Tharun");
        customer.setMobileNumber("9876542312");
        customerService.save(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void saveBookCustomer() {

        Book book = new Book("Data Structures","Shivkesh","Programming");
        Customer customer = new Customer(3,"Tharun","9876542312");
        when(customerRepository.findByNameAndMobileNumber(customer.getName(),customer.getMobileNumber())).
                thenReturn(Optional.of(customer));
        book.addCustomer(customer);
        customerService.saveBookCustomer(customer,book);
        assertEquals((book.getCustomerList()).get(0),customer);
        verify(customerRepository).findByNameAndMobileNumber(customer.getName(),customer.getMobileNumber());
        Optional<Customer> result = Optional.empty();
        Customer customer1 = new Customer(5,"Sai","8567342564");
        when(customerRepository.findByNameAndMobileNumber(customer1.getName(),customer1.getMobileNumber())).
                thenReturn(result);
        customerService.saveBookCustomer(customer1,book);
        verify(bookRepository).save(book);
    }

    @Test
    void deleteById() {
        Customer customer = new Customer(3,"Tharun","9876542312");
        when(customerRepository.findById(3)).thenReturn(Optional.of(customer));
        customerService.deleteById(customer.getId());
        verify(customerRepository).deleteById(customer.getId());
    }
}