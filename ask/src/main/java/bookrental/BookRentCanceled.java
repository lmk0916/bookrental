package bookrental;

public class BookRentCanceled extends AbstractEvent {

    private Long id;
    private Long askkId;
    private String status;

    public BookRentCanceled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getAskId() {
        return askkId;
    }

    public void setAskId(Long askkId) {
        this.askkId = askkId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
