package springboot.service;


import springboot.entity.Book;
import springboot.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<Customer> findAll();

    public Customer findById(int theId);

    Optional<Customer> findByNameAndMobileNumber(String name, String mobileNumber);

    public void save(Customer theCustomer);

    void saveBookCustomer(Customer customer, Book book);

    public void deleteById(int theId);
}
