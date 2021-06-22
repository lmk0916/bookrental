package bookrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Book_table")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long askId;
    private String status;
    private String description;
    private String bookName;
    private Double price;

    @PostPersist
    public void onPostPersist(){
        System.out.println("##### onPostPersist status = " + this.getStatus());
        if (this.getStatus().equals("WAITING")) {
            RentalRegistered rentalRegistered = new RentalRegistered();
            BeanUtils.copyProperties(this, rentalRegistered);
            rentalRegistered.publishAfterCommit();
        }

    }

     @PostUpdate
    public void onPostUpdate() {

         System.out.println("##### onPostUpdate status = " + this.getStatus());
         if (this.getStatus().equals("RENTED")) {
             BookRented bookRented = new BookRented();
             BeanUtils.copyProperties(this, bookRented);
             bookRented.setStatus("RENTED");
             bookRented.publishAfterCommit();
         }

         if (this.getStatus().equals("WAITING") && this.getaskId() == null) {
             BookRentCanceled bookRentCanceled = new BookRentCanceled();
             BeanUtils.copyProperties(this, bookRentCanceled);
             bookRentCanceled.setStatus("RENTED_CANCELED");
             bookRentCanceled.publishAfterCommit();
         }

     }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getaskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
