package springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByNameAndMobileNumber(String name, String mobileNumber);
}
