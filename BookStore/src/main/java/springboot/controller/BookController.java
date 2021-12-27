package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.entity.Book;
import springboot.service.BookService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String listBooks(Model model)
    {
        List<Book> books = bookService.findAll();
        model.addAttribute("books",books);
        return "books/list-books";
    }

    @GetMapping("/add")
    public String showForm(Model model)
    {
        Book theBook = new Book();
        model.addAttribute("book" ,theBook);
        return "books/book-form";
    }

    @PostMapping("/save")
    public String saveBook(
            @ModelAttribute("book") @Valid Book theBook,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/book-form";
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
        return "books/book-form";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("bookId") int theId)
    {
        bookService.deleteById(theId);
        return "redirect:/books/list";
    }

}
