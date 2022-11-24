package home;

import java.util.ArrayList;

public class BookData {
    private String title;
    private String author;
    private int year;
    private ArrayList<String> genre = new ArrayList<>();
    private String description;
    private String image;
    private String availability;
    private String status;
    private String student;

    public BookData(){

    }
    
    // public BookData(String title) {
    //     this.title = title;
    // }

    public BookData(String title, String author, int year, ArrayList<String> genre, String description, String image) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.image = image;
    }

    public BookData(String title, String author, int year, String description, String image, String availability, String status, String student) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.description = description;
        this.image = image;
        this.availability = availability;
        this.status = status;
        this.student = student;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = description;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", year=" + year + ", genre=" + genre + "]";
    }

}
