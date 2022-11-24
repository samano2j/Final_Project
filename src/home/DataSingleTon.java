package home;

public class DataSingleTon {
    private static final DataSingleTon instance = new DataSingleTon();

    private BookData bookData;

    private String status;

    private String user;

    public static DataSingleTon getInstance(){
        return instance;
    }

    public BookData getBookData() {
        return bookData;
    }

    public void setBookData(BookData bookData) {
        this.bookData = bookData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
