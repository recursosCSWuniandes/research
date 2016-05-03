/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.bookstore.ejbs;

import co.edu.uniandes.csw.bookstore.api.IAuthorLogic;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class AuthorLogic implements IAuthorLogic {

    @Inject private AuthorPersistence persistence;

    @Inject private IBookLogic bookLogic;

    /**
     * @generated
     */
    @Override
    public int countAuthors() {
        return persistence.count();
    }

    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> getAuthors() {
        return persistence.findAll();
    }

    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> getAuthors(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }
    /**
     * @generated
     */
    @Override
    public AuthorEntity getAuthor(Long id) {
        return persistence.find(id);
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity createAuthor(AuthorEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity updateAuthor(AuthorEntity entity) {
        AuthorEntity newEntity = entity;
        AuthorEntity oldEntity = persistence.find(entity.getId());
        newEntity.setBooks(oldEntity.getBooks());
        return persistence.update(newEntity);
    }

    /**
     * @generated
     */
    @Override
    public void deleteAuthor(Long id) {
        persistence.delete(id);
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> listBooks(Long authorId) {
        return persistence.find(authorId).getBooks();
    }

    /**
     * @generated
     */
    @Override
    public BookEntity getBooks(Long authorId, Long booksId) {
        List<BookEntity> list = persistence.find(authorId).getBooks();
        BookEntity booksEntity = new BookEntity();
        booksEntity.setId(booksId);
        int index = list.indexOf(booksEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * @generated
     */
    @Override
    public BookEntity addBooks(Long authorId, Long booksId) {
        bookLogic.addAuthors(booksId, authorId);
        return bookLogic.getBook(booksId);
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> replaceBooks(Long authorId, List<BookEntity> list) {
        AuthorEntity authorEntity = persistence.find(authorId);
        List<BookEntity> bookList = bookLogic.getBooks();
        for (BookEntity book : bookList) {
            if (list.contains(book)) {
                if (!book.getAuthors().contains(authorEntity)) {
                    bookLogic.addAuthors(book.getId(), authorId);
                }
            } else {
                bookLogic.removeAuthors(book.getId(), authorId);
            }
        }
        authorEntity.setBooks(list);
        return authorEntity.getBooks();
    }

    /**
     * @generated
     */
    @Override
    public void removeBooks(Long authorId, Long booksId) {
        bookLogic.removeAuthors(booksId, authorId);
    }
}
