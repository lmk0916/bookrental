package bookrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Pay_table")
public class Pay {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Long askId;
    private Long bookId;
    private String payDate;
    private String payCancelDate;
    private Double bookPrice;

    @PostPersist
    public void onPostPersist(){
        System.out.println("##### onPostPersist status = " + this.getStatus());

        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
            System.out.println("##### SLEEP2");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.getStatus().equals("ASKED") || this.getStatus().equals("PAID")) {
            Paid paid = new Paid();
            BeanUtils.copyProperties(this, paid);
            paid.setStatus("PAID");
            paid.publishAfterCommit();
        }

    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("##### onPreUpdate status = " + this.getStatus());
        if (this.getStatus().equals("ASK_CANCELED") || this.getStatus().equals("PAY_CANCELED")) {
            PayCanceled payCanceled = new PayCanceled();
            BeanUtils.copyProperties(this, payCanceled);
            payCanceled.setStatus("PAY_CANCELED");
            payCanceled.publishAfterCommit();
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
    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
    public String getPayCancelDate() {
        return payCancelDate;
    }

    public void setPayCancelDate(String payCancelDate) {
        this.payCancelDate = payCancelDate;
    }
    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }




}
