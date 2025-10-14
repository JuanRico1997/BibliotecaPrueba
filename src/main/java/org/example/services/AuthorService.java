package org.example.services;

import org.example.dao.AuthorDao;
import org.example.domain.Author;

public class AuthorService {
    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public Author findByName(String nameAuthor){
        return authorDao.findByName(nameAuthor);
    }

    public boolean createAuthor(Author author){
        Author existing = authorDao.findByName(author.getName());
        if (existing != null){
            System.out.println("El autor ya existe");
            return false;
        }
        return authorDao.createAuthor(author);
    }

}
