package bookrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Ask_table")
public class Ask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Long bookId;
    private String askDate;
    private Double bookPrice;

    @PostPersist
    public void onPostPersist(){

        System.out.println("##### onPostPersist status = " + this.getStatus());
        if (this.getStatus().equals("ASKED")) {
            Asked asked = new Asked();
            BeanUtils.copyProperties(this, asked);
            asked.publishAfterCommit();

            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

            bookrental.external.Pay pay = new bookrental.external.Pay();
            // mappings goes here
            pay.setaskId(this.getId());
            pay.getBookId(this.getBookId());
            pay.setStatus(this.getStatus());
            AskApplication.applicationContext.getBean(bookrental.external.PayService.class)
                    .pay(pay);
        }
    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("##### onPostUpdate status = " + this.getStatus());
        if (this.getStatus().equals("ASK_CANCELED")) {
            AskCanceled askCanceled = new AskCanceled();
            BeanUtils.copyProperties(this, askCanceled);
            askCanceled.publishAfterCommit();

            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

            bookrental.external.Pay pay = new bookrental.external.Pay();
            // mappings goes here
            pay.setaskId(this.getId());
            pay.getBookId(this.getBookId());
            pay.setStatus(this.getStatus());
            AskApplication.applicationContext.getBean(bookrental.external.PayService.class)
                    .payCancel(pay, this.getId());

        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public String getAskDate() {
        return askDate;
    }
    public void setAskDate(String askDate) {
        this.askDate = askDate;
    }
    public Double getBookPrice() {
        return bookPrice;
    }
    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }




}
