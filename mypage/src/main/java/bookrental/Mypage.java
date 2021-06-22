package bookrental;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Mypage_table")
public class Mypage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long payId;
        private Long askId;
        private Long bookId;
        private String status;
        private Double bookPrice;
        private String askDate;
        private String payDate;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public Long getPayId() {
            return payId;
        }

        public void setPayId(Long payId) {
            this.payId = payId;
        }
        public Long getAskId() {
            return askId;
        }

        public void setAskId(Long askId) {
            this.askId = askId;
        }
        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public Double getBookPrice() {
            return bookPrice;
        }

        public void setBookPrice(Double bookPrice) {
            this.bookPrice = bookPrice;
        }
        public String getAskDate() {
            return askDate;
        }

        public void setAskDate(String askDate) {
            this.askDate = askDate;
        }
        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

}
