package org.example.dao;

import org.example.domain.Book;

import java.util.List;

public interface BookDao {
    Book createBook(Book book);
    boolean updateAvailableBook(int idBook,int availability);
    boolean deleteBook(int idBook);
    List<Book> findAllBook();
    List<Book> findBookByAvailable();
    Book findBookById(int id);
    boolean updateBook(Book book);
}
