package springboot.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.dao.BookRepository;
import springboot.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int theId) {
        Optional<Book> result = bookRepository.findById(theId);
        Book book = null;
        if (result.isPresent())
        {
            book = result.get();
        }
        else
        {
            log.error("Couldn't find book with Id:"+theId);
            throw new RuntimeException("No Book found with Id: "+theId);
        }
        return book;
    }

    @Override
    public void save(Book theBook) {
        bookRepository.save(theBook);
    }

    @Override
    public void deleteById(int theId) {
        findById(theId);
        bookRepository.deleteById(theId);
    }
}
