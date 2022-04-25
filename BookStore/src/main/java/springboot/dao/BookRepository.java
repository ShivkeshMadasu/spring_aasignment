package springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory(String category);
}
