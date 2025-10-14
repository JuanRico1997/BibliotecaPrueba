package org.example.domain;

public class Book {

    private int id;
    private String title;
    private Author author;
    private int year_publication;
    private int availability;

    public Book() {}

    public Book(int id, String title, Author author, int year_publication, int availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year_publication = year_publication;
        this.availability = availability;
    }

    public Book(String title, Author author, int year_publication, int availability) {
        this.title = title;
        this.author = author;
        this.year_publication = year_publication;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear_publication() {
        return year_publication;
    }

    public void setYear_publication(int year_publication) {
        this.year_publication = year_publication;
    }

    public int isAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return title + " - " + author.getName() + " (" + year_publication + ")" +
                (availability>0 ? " [Available]" : " [Not available]");
    }
}
