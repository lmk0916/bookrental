package bookrental;

public class Asked extends AbstractEvent {

    private Long id;
    private Long bookId;
    private String askDate;
    private String status;
    private Double bookPrice;

    public Asked(){
        super();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getbookId() {
        return bookId;
    }

    public void setbookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setAskDate(String askDate) {
        this.askDate = askDate;
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
}
