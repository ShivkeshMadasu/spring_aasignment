package springboot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message="is required")
    @Column(name="name")
    private String name;

    @NotBlank(message="is required")
    @Column(name="mobile_number")
    private String mobileNumber;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name="book_customer", joinColumns = @JoinColumn(name="customer_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
    private List<Book> bookList;

    public Customer(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addBook(Book theBook)
    {
        if(bookList == null)
        {
            bookList = new ArrayList<>();
        }
        bookList.add(theBook);
    }
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", BookList=" + bookList +
                '}';
    }
}
