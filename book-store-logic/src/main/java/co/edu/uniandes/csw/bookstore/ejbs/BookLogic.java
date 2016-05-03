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

import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class BookLogic implements IBookLogic {

    @Inject private BookPersistence persistence;

    /**
     * @generated
     */
    @Override
    public int countBooks() {
        return persistence.count();
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> getBooks() {
        return persistence.findAll();
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> getBooks(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }
    /**
     * @generated
     */
    @Override
    public BookEntity getBook(Long id) {
        return persistence.find(id);
    }

    /**
     * @generated
     */
    @Override
    public BookEntity createBook(BookEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * @generated
     */
    @Override
    public BookEntity updateBook(BookEntity entity) {
        BookEntity newEntity = entity;
        BookEntity oldEntity = persistence.find(entity.getId());
        newEntity.setAuthors(oldEntity.getAuthors());
        return persistence.update(newEntity);
    }

    /**
     * @generated
     */
    @Override
    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> listAuthors(Long bookId) {
        return persistence.find(bookId).getAuthors();
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity getAuthors(Long bookId, Long authorsId) {
        List<AuthorEntity> list = persistence.find(bookId).getAuthors();
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        int index = list.indexOf(authorsEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity addAuthors(Long bookId, Long authorsId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        bookEntity.getAuthors().add(authorsEntity);
        return getAuthors(bookId, authorsId);
    }

    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> replaceAuthors(Long bookId, List<AuthorEntity> list) {
        BookEntity bookEntity = persistence.find(bookId);
        bookEntity.setAuthors(list);
        return bookEntity.getAuthors();
    }

    /**
     * @generated
     */
    @Override
    public void removeAuthors(Long bookId, Long authorsId) {
        BookEntity entity = persistence.find(bookId);
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        entity.getAuthors().remove(authorsEntity);
    }
}
