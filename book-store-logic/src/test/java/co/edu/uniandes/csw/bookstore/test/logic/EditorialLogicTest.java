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
package co.edu.uniandes.csw.bookstore.test.logic;

import co.edu.uniandes.csw.bookstore.ejbs.EditorialLogic;
import co.edu.uniandes.csw.bookstore.api.IEditorialLogic;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.persistence.EditorialPersistence;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class EditorialLogicTest {

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IEditorialLogic editorialLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;

    /**
     * @generated
     */
    private List<EditorialEntity> data = new ArrayList<EditorialEntity>();

    /**
     * @generated
     */
    private List<BookEntity> booksData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EditorialEntity.class.getPackage())
                .addPackage(EditorialLogic.class.getPackage())
                .addPackage(IEditorialLogic.class.getPackage())
                .addPackage(EditorialPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from BookEntity").executeUpdate();
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BookEntity books = factory.manufacturePojo(BookEntity.class);
            em.persist(books);
            booksData.add(books);
        }

        for (int i = 0; i < 3; i++) {
            EditorialEntity entity = factory.manufacturePojo(EditorialEntity.class);

            em.persist(entity);
            data.add(entity);

            if (i == 0) {
                booksData.get(i).setEditorial(entity);
            }
        }
    }

    /**
     * @generated
     */
    @Test
    public void createEditorialTest() {
        EditorialEntity newEntity = factory.manufacturePojo(EditorialEntity.class);
        EditorialEntity result = editorialLogic.createEditorial(newEntity);
        Assert.assertNotNull(result);
        EditorialEntity entity = em.find(EditorialEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * @generated
     */
    @Test
    public void getEditorialsTest() {
        List<EditorialEntity> list = editorialLogic.getEditorials();
        Assert.assertEquals(data.size(), list.size());
        for (EditorialEntity entity : list) {
            boolean found = false;
            for (EditorialEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * @generated
     */
    @Test
    public void getEditorialTest() {
        EditorialEntity entity = data.get(0);
        EditorialEntity resultEntity = editorialLogic.getEditorial(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * @generated
     */
    @Test
    public void deleteEditorialTest() {
        EditorialEntity entity = data.get(1);
        editorialLogic.deleteEditorial(entity.getId());
        EditorialEntity deleted = em.find(EditorialEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * @generated
     */
    @Test
    public void updateEditorialTest() {
        EditorialEntity entity = data.get(0);
        EditorialEntity pojoEntity = factory.manufacturePojo(EditorialEntity.class);

        pojoEntity.setId(entity.getId());

        editorialLogic.updateEditorial(pojoEntity);

        EditorialEntity resp = em.find(EditorialEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

    /**
     * @generated
     */
    @Test
    public void getBooksTest() {
        EditorialEntity entity = data.get(0);
        BookEntity bookEntity = booksData.get(0);
        BookEntity response = editorialLogic.getBooks(entity.getId(), bookEntity.getId());

        Assert.assertEquals(bookEntity.getId(), response.getId());
        Assert.assertEquals(bookEntity.getName(), response.getName());
        Assert.assertEquals(bookEntity.getDescription(), response.getDescription());
        Assert.assertEquals(bookEntity.getIsbn(), response.getIsbn());
        Assert.assertEquals(bookEntity.getImage(), response.getImage());
        Assert.assertEquals(bookEntity.getPublishDate(), response.getPublishDate());
    }

    /**
     * @generated
     */
    @Test
    public void listBooksTest() {
        List<BookEntity> list = editorialLogic.listBooks(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * @generated
     */
    @Test
    public void addBooksTest() {
        EditorialEntity entity = data.get(0);
        BookEntity bookEntity = booksData.get(1);
        BookEntity response = editorialLogic.addBooks(entity.getId(), bookEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(bookEntity.getId(), response.getId());
    }

    /**
     * @generated
     */
    @Test
    public void replaceBooksTest() {
        EditorialEntity entity = data.get(0);
        List<BookEntity> list = booksData.subList(1, 3);
        editorialLogic.replaceBooks(entity.getId(), list);

        entity = editorialLogic.getEditorial(entity.getId());
        Assert.assertFalse(entity.getBooks().contains(booksData.get(0)));
        Assert.assertTrue(entity.getBooks().contains(booksData.get(1)));
        Assert.assertTrue(entity.getBooks().contains(booksData.get(2)));
    }

    /**
     * @generated
     */
    @Test
    public void removeBooksTest() {
        editorialLogic.removeBooks(data.get(0).getId(), booksData.get(0).getId());
        BookEntity response = editorialLogic.getBooks(data.get(0).getId(), booksData.get(0).getId());
        Assert.assertNull(response);
    }
}
