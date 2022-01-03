package springboot.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.dao.BookRepository;
import springboot.dao.CustomerRepository;
import springboot.entity.Book;
import springboot.entity.Customer;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(int theId) {
        Optional<Customer> result = customerRepository.findById(theId);
        Customer customer= null;
        if (result.isPresent())
        {
            customer= result.get();
        }
        else
        {
            log.error("Couldn't find Customer with Id:"+theId);
            throw new IdNotFoundException("No Customer found with Id: "+theId);
        }
        return customer;
    }

    @Override
    public Optional<Customer> findByNameAndMobileNumber(String name, String mobileNumber) {
        return customerRepository.findByNameAndMobileNumber(name,mobileNumber);
    }

    @Override
    public void save(Customer theCustomer) {
        customerRepository.save(theCustomer);
    }

    @Override
    public void saveBookCustomer(Customer customer, Book book) {
    Optional<Customer> result = findByNameAndMobileNumber(customer.getName(),customer.getMobileNumber());
        if(result.isPresent()){
        customer = result.get();}
        else{
            save(customer);}
        if(!book.getCustomerList().contains(customer)) {
            book.addCustomer(customer);
            bookRepository.save(book);
        }
    }

    @Override
    public void deleteById(int theId) {
        findById(theId);
        customerRepository.deleteById(theId);
    }
}
