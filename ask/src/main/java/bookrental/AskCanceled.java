package bookrental;

public class AskCanceled extends AbstractEvent {

    private Long id;
    private Long bookId;
    private String status;

    public AskCanceled(){
        super();
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

    public void setbookId(Long bookId) {
        this.bookId = bookId;
    }
}
