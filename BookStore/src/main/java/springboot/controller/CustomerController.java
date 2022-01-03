package springboot.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.dto.BookDto;
import springboot.dto.CustomerDto;
import springboot.entity.Book;
import springboot.entity.Customer;
import springboot.service.BookService;
import springboot.service.CustomerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
@Log4j2
public class CustomerController {

    String customerForm = "customers/customer-form";
    String redirect = "redirect:/customers/list?bookId=";

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomer(Model model, @RequestParam("bookId") int bookId)
    {
        Book book = bookService.findById(bookId);
        model.addAttribute("book",book);
        model.addAttribute("customers",book.getCustomerList());
        return "customers/customer-list";
    }

    @GetMapping("/add")
    public String addCustomer(Model model,@RequestParam("bookId") int bookId)
    {
        Book book = bookService.findById(bookId);
        model.addAttribute("book",book);
        Customer customer = new Customer();
        model.addAttribute("customer",customer);
        return customerForm;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("customer") @Valid CustomerDto customerDto, BindingResult bindingResult, @RequestParam("bookId") int bookId,
                       @ModelAttribute("book") BookDto bookDto)
    {
        Book theBook = new Book();
        theBook.setId(bookId);

        Customer theCustomer = new Customer();
        theCustomer.setId(customerDto.getId());
        theCustomer.setName(customerDto.getName());
        theCustomer.setMobileNumber(customerDto.getMobileNumber());

        if(bindingResult.hasErrors()){
            return customerForm;}
        Book book = bookService.findById(bookId);
        customerService.saveBookCustomer(theCustomer, book);

        return redirect+bookId;
    }

    @GetMapping("/update")
    public String update(Model model,@RequestParam("customerId") int customerId, @RequestParam("bookId") int bookId)
    {
        Book book = bookService.findById(bookId);
        Customer customer = customerService.findById(customerId);
        model.addAttribute(customer);
        model.addAttribute(book);
        return "customers/updateForm";
    }

    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute("customer") @Valid CustomerDto customerDto, BindingResult bindingResult, @RequestParam("bookId") int bookId,
                       @ModelAttribute("book") BookDto bookDto) {
        Book theBook = new Book();
        theBook.setId(bookId);

        Customer theCustomer = new Customer();
        theCustomer.setId(customerDto.getId());
        theCustomer.setName(customerDto.getName());
        theCustomer.setMobileNumber(customerDto.getMobileNumber());

        if (bindingResult.hasErrors()){
            return customerForm;}
        Customer customer = customerService.findById(theCustomer.getId());
        theCustomer.setBookList(customer.getBookList());
        customerService.save(theCustomer);

        return redirect+bookId;

    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") int customerId,@RequestParam("bookId") int bookId){
        customerService.deleteById(customerId);
        return redirect+bookId;
    }

}
