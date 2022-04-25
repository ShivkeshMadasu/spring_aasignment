package springboot.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.dto.BookDto;
import springboot.entity.Book;
import springboot.service.BookService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
@Log4j2
public class BookController {

    @Autowired
    private BookService bookService;

    String bookForm = "books/book-form";

    @GetMapping("/list")
    public String listBooks(Model model)
    {
        List<Book> books = bookService.findAll();
        model.addAttribute("books",books);
        Book theBook = new Book();
        model.addAttribute("book" ,theBook);
        return "books/list-books";
    }

    @GetMapping("/add")
    public String showForm(Model model)
    {
        Book theBook = new Book();
        model.addAttribute("book" ,theBook);
        return bookForm;
    }

    @PostMapping("/save")
    public String saveBook(
            @ModelAttribute("book") @Valid BookDto bookDto,
            BindingResult bindingResult) {

        Book theBook = new Book();
        theBook.setId(bookDto.getId());
        theBook.setTitle(bookDto.getTitle());
        theBook.setAuthor(bookDto.getAuthor());
        theBook.setCategory(bookDto.getCategory());
        if (bindingResult.hasErrors()) {
            return bookForm;
        }
        else {
            // save the book
            bookService.save(theBook);

            // use a redirect to prevent duplicate submissions
            return "redirect:/books/list";
        }
    }

    @GetMapping("/update")
    public String updateBook(@RequestParam("bookId") int theId, Model model)
    {
        Book book = bookService.findById(theId);
        model.addAttribute("book",book);
        return bookForm;
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("bookId") int theId)
    {
        bookService.deleteById(theId);
        return "redirect:/books/list";
    }

    @GetMapping("/search")
    public String searchBook(
            @RequestParam("category") String bookCategory, Model model) {
        List<Book> books = bookService.findAllByCategory(bookCategory);
        model.addAttribute("books",books);
        return "books/list-books";
    }

}
