package org.example.services;

import org.example.dao.BookDao;
import org.example.dao.Impl.BookDaoImpl;
import org.example.domain.Author;
import org.example.domain.Book;

import java.util.List;

public class BookService {
    private  BookDao bookDao;
    private AuthorService authorService;

    public BookService(BookDao bookDao, AuthorService authorService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
    }
    public BookService() {

    }
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    public Book addBook(Book book){
        Author author = book.getAuthor();
        if (author != null){
            Author existing = authorService.findByName(author.getName());
            if (existing == null){
                if (authorService.createAuthor(author)) {
                    // si createAuthor no seteara el id por el driver, recupéralo
                    if (author.getId() == 0) {
                        existing = authorService.findByName(author.getName());
                        if (existing != null) book.setAuthor(existing);
                    }
                }
            } else {
                book.setAuthor(existing);
            }
        }
        return bookDao.createBook(book);
    }

    public boolean decreaseAvailability(Book book) {
        int newAvailability = book.isAvailability() - 1;
        if (newAvailability < 0) {
            System.out.println("No hay ejemplares disponibles para prestar.");
            return false;
        }
        book.setAvailability(newAvailability);
        return bookDao.updateAvailableBook(book.getId(), newAvailability);
    }

    public boolean increaseAvailability(Book book) {
        int newAvailability = book.isAvailability() + 1;
        book.setAvailability(newAvailability);
        return bookDao.updateAvailableBook(book.getId(), newAvailability);
    }

    public boolean deleteBook(int idBook){
        return bookDao.deleteBook(idBook);
    }

    public Book findBookById(int id){
        return bookDao.findBookById(id);
    }

    public List<Book> allBooks(){
        return bookDao.findAllBook();
    }

    public List<Book> booksAvailable(){
        return bookDao.findBookByAvailable();
    }

    public boolean updateBook(Book book) {
        Author author = book.getAuthor();
        if (author != null) {
            Author existing = authorService.findByName(author.getName());
            if (existing == null) {
                // Si no existe, crear y luego recuperar (o confiar en que el DAO setee el ID)
                authorService.createAuthor(author);
                existing = authorService.findByName(author.getName());
            }
            book.setAuthor(existing);
        }
        return bookDao.updateBook(book);
    }



}
