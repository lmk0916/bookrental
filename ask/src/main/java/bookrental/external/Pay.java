package bookrental.external;

public class Pay {

    private Long id;
    private String status;
    private Long bookId;
    private String payDate;
    private String payCancelDate;
    private Double bookPrice;
    private Long askId;

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
    public Long getBookId(Long bookId) {
        return this.bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
    public Long getaskId() { return askId; }
    public void setaskId(Long id) {
        this.askId = id;
    }

}
