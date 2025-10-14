package org.example.dao;

import org.example.domain.Author;

public interface AuthorDao {
    Author findByName(String nameAuthor);
    boolean createAuthor(Author author);
}
