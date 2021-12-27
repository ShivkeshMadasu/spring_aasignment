package springboot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message="is required")
    @Column(name="title")
    private String title;

    @NotBlank(message="is required")
    @Column(name="author")
    private String author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name="book_customer", joinColumns = @JoinColumn(name="book_id"), inverseJoinColumns = @JoinColumn(name="customer_id"))
    private List<Customer> customerList;

    public Book(){}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void addCustomer(Customer theCustomer) {
        if (customerList == null) {
            customerList = new ArrayList<>();
        }
        customerList.add(theCustomer);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", customerList=" + customerList +
                '}';
    }
}
