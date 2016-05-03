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

import co.edu.uniandes.csw.bookstore.api.IEditorialLogic;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.persistence.EditorialPersistence;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class EditorialLogic implements IEditorialLogic {

    @Inject private EditorialPersistence persistence;

    @Inject private IBookLogic bookLogic;

    /**
     * @generated
     */
    @Override
    public int countEditorials() {
        return persistence.count();
    }

    /**
     * @generated
     */
    @Override
    public List<EditorialEntity> getEditorials() {
        return persistence.findAll();
    }

    /**
     * @generated
     */
    @Override
    public List<EditorialEntity> getEditorials(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }
    /**
     * @generated
     */
    @Override
    public EditorialEntity getEditorial(Long id) {
        return persistence.find(id);
    }

    /**
     * @generated
     */
    @Override
    public EditorialEntity createEditorial(EditorialEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * @generated
     */
    @Override
    public EditorialEntity updateEditorial(EditorialEntity entity) {
        EditorialEntity newEntity = entity;
        EditorialEntity oldEntity = persistence.find(entity.getId());
        newEntity.setBooks(oldEntity.getBooks());
        return persistence.update(newEntity);
    }

    /**
     * @generated
     */
    @Override
    public void deleteEditorial(Long id) {
        persistence.delete(id);
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> listBooks(Long editorialId) {
        return persistence.find(editorialId).getBooks();
    }

    /**
     * @generated
     */
    @Override
    public BookEntity getBooks(Long editorialId, Long booksId) {
        List<BookEntity> list = persistence.find(editorialId).getBooks();
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
    public BookEntity addBooks(Long editorialId, Long booksId) {
        EditorialEntity editorialEntity = persistence.find(editorialId);
        BookEntity booksEntity = bookLogic.getBook(booksId);
        booksEntity.setEditorial(editorialEntity);
        return booksEntity;
    }

    /**
     * @generated
     */
    @Override
    public List<BookEntity> replaceBooks(Long editorialId, List<BookEntity> list) {
        EditorialEntity editorialEntity = persistence.find(editorialId);
        List<BookEntity> bookList = bookLogic.getBooks();
        for (BookEntity book : bookList) {
            if (list.contains(book)) {
                book.setEditorial(editorialEntity);
            } else {
                if (book.getEditorial() != null && book.getEditorial().equals(editorialEntity)) {
                    book.setEditorial(null);
                }
            }
        }
        editorialEntity.setBooks(list);
        return editorialEntity.getBooks();
    }

    /**
     * @generated
     */
    @Override
    public void removeBooks(Long editorialId, Long booksId) {
        BookEntity entity = bookLogic.getBook(booksId);
        entity.setEditorial(null);
    }
}
