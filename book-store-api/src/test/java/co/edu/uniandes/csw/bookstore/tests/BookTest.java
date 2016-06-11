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
package co.edu.uniandes.csw.bookstore.tests;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import co.edu.uniandes.csw.bookstore.dtos.minimum.BookMinimumDTO;
import co.edu.uniandes.csw.bookstore.dtos.minimum.AuthorMinimumDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.resources.BookService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(Arquillian.class)
public class BookTest {

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private final String bookPath = "books";
    private final static List<BookEntity> oraculo = new ArrayList<>();
    private final String authorsPath = "authors";
    private final static List<AuthorMinimumDTO> oraculoAuthors = new ArrayList<>();
    private WebTarget target;
    private final String apiPath = "api";
    private final String username = System.getenv("USERNAME_USER");
    private final String password = System.getenv("PASSWORD_USER");
    private PodamFactory factory = new PodamFactoryImpl();

    @ArquillianResource
    private URL deploymentURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(BookService.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @PersistenceContext(unitName = "BookStorePU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private void clearData() {
        em.createQuery("delete from BookEntity").executeUpdate();
    }

    private void insertData() {
        System.out.println("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        if (em != null) {
            System.out.println(em.toString());
        } else {
            System.out.println("em es nulo");
        }
        for (int i = 0; i < 3; i++) {
            BookEntity entity = factory.manufacturePojo(BookEntity.class);
            entity.setId(i + 1L);
            em.persist(entity);
            oraculo.add(entity);
        }
    }

    @Before
    public void setUpTest() {
        target = createWebTarget();
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

    public Cookie login(String username, String password) {
        UserDTO credentials = new UserDTO();
        credentials.setUserName(username);
        credentials.setPassword(password);
        Response response = target.path("users").path("login").request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Ok) {
            return response.getCookies().get(JWT.cookieName);
        } else {
            return null;
        }
    }

    @Test
    @Ignore
    public void createBookTest() throws IOException {
        BookMinimumDTO book = factory.manufacturePojo(BookMinimumDTO.class);
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookMinimumDTO bookTest = (BookMinimumDTO) response.readEntity(BookMinimumDTO.class);
//        Assert.assertEquals(book.getId(), bookTest.getId());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(book.getPublishDate(), bookTest.getPublishDate());
        Assert.assertEquals(Created, response.getStatus());
        
        BookEntity entity = em.find(BookEntity.class, bookTest.getId());
        Assert.assertNotNull(entity);
    }

    @Test
    public void getBookById() {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO bookTest = target.path(bookPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(BookMinimumDTO.class);
        Assert.assertEquals(bookTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(bookTest.getName(), oraculo.get(0).getName());
        Assert.assertEquals(bookTest.getDescription(), oraculo.get(0).getDescription());
        Assert.assertEquals(bookTest.getIsbn(), oraculo.get(0).getIsbn());
        Assert.assertEquals(bookTest.getImage(), oraculo.get(0).getImage());
        Assert.assertEquals(bookTest.getPublishDate(), oraculo.get(0).getPublishDate());
    }

    @Test
    @Ignore
    public void listBookTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId).get();
        String listBook = response.readEntity(String.class);
        List<BookMinimumDTO> listBookTest = new ObjectMapper().readValue(listBook, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listBookTest.size());
    }

    @Test
    @Ignore
    public void updateBookTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO book = new BookMinimumDTO(oraculo.get(0));
        PodamFactory factory = new PodamFactoryImpl();
        BookMinimumDTO bookChanged = factory.manufacturePojo(BookMinimumDTO.class);
        book.setName(bookChanged.getName());
        book.setDescription(bookChanged.getDescription());
        book.setIsbn(bookChanged.getIsbn());
        book.setImage(bookChanged.getImage());
        book.setPublishDate(bookChanged.getPublishDate());
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookMinimumDTO bookTest = (BookMinimumDTO) response.readEntity(BookMinimumDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(book.getPublishDate(), bookTest.getPublishDate());
    }

    @Test
    @Ignore
    public void deleteBookTest() {
        Cookie cookieSessionId = login(username, password);
        Long bookId = oraculo.get(0).getId();
        Response response = target.path(bookPath).path(bookId.toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
