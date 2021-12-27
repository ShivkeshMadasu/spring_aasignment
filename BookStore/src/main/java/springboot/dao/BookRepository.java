package springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
