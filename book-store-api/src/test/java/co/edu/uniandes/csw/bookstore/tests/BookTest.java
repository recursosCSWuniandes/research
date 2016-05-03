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
import co.edu.uniandes.csw.bookstore.dtos.basic.BookBasicDTO;
import co.edu.uniandes.csw.bookstore.dtos.minimum.BookMinimumDTO;
import co.edu.uniandes.csw.bookstore.dtos.minimum.AuthorMinimumDTO;
import co.edu.uniandes.csw.bookstore.resources.BookService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
    private final static List<BookMinimumDTO> oraculo = new ArrayList<>();
    private final String authorsPath = "authors";
    private final static List<AuthorMinimumDTO> oraculoAuthors = new ArrayList<>();
    private WebTarget target;
    private final String apiPath = "api";
    private final String username = System.getenv("USERNAME_USER");
    private final String password = System.getenv("PASSWORD_USER");

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw.bookstore:book-store-logic:0.1.0")
                        .withTransitivity().asFile())
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw:auth-utils:0.1.0")
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

    @BeforeClass
    public static void setUp() {
        insertData();
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            BookBasicDTO book = factory.manufacturePojo(BookBasicDTO.class);
            book.setId(i + 1L);
            oraculo.add(book);

            AuthorMinimumDTO authors = factory.manufacturePojo(AuthorMinimumDTO.class);
            authors.setId(i + 1L);
            oraculoAuthors.add(authors);
        }
    }

    public Cookie login(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
        user.setRememberMe(true);
        Response response = target.path("users").path("login").request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Ok) {
            return response.getCookies().get(JWT.cookieName);
        } else {
            return null;
        }
    }

    @Before
    public void setUpTest() {
        target = createWebTarget();
    }

    @Test
    @InSequence(1)
    public void createBookTest() throws IOException {
        BookMinimumDTO book = oraculo.get(0);
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookMinimumDTO bookTest = (BookMinimumDTO) response.readEntity(BookMinimumDTO.class);
        Assert.assertEquals(book.getId(), bookTest.getId());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(book.getPublishDate(), bookTest.getPublishDate());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    @InSequence(2)
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
    @InSequence(3)
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
    @InSequence(4)
    public void updateBookTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO book = oraculo.get(0);
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
    @InSequence(9)
    public void deleteBookTest() {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO book = oraculo.get(0);
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

    @Test
    @InSequence(5)
    public void addAuthorsTest() {
        Cookie cookieSessionId = login(username, password);

        AuthorMinimumDTO authors = oraculoAuthors.get(0);
        BookMinimumDTO book = oraculo.get(0);

        Response response = target.path("authors")
                .request().cookie(cookieSessionId)
                .post(Entity.entity(authors, MediaType.APPLICATION_JSON));

        AuthorMinimumDTO authorsTest = (AuthorMinimumDTO) response.readEntity(AuthorMinimumDTO.class);
        Assert.assertEquals(authors.getId(), authorsTest.getId());
        Assert.assertEquals(authors.getName(), authorsTest.getName());
        Assert.assertEquals(authors.getBirthDate(), authorsTest.getBirthDate());
        Assert.assertEquals(Created, response.getStatus());

        response = target.path(bookPath).path(book.getId().toString())
                .path(authorsPath).path(authors.getId().toString())
                .request().cookie(cookieSessionId)
                .post(Entity.entity(authors, MediaType.APPLICATION_JSON));

        authorsTest = (AuthorMinimumDTO) response.readEntity(AuthorMinimumDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(authors.getId(), authorsTest.getId());
    }

    @Test
    @InSequence(6)
    public void listAuthorsTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO book = oraculo.get(0);

        Response response = target.path(bookPath)
                .path(book.getId().toString())
                .path(authorsPath)
                .request().cookie(cookieSessionId).get();

        String authorsList = response.readEntity(String.class);
        List<AuthorMinimumDTO> authorsListTest = new ObjectMapper().readValue(authorsList, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, authorsListTest.size());
    }

    @Test
    @InSequence(7)
    public void getAuthorsTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        AuthorMinimumDTO authors = oraculoAuthors.get(0);
        BookMinimumDTO book = oraculo.get(0);

        AuthorMinimumDTO authorsTest = target.path(bookPath)
                .path(book.getId().toString()).path(authorsPath)
                .path(authors.getId().toString())
                .request().cookie(cookieSessionId).get(AuthorMinimumDTO.class);

        Assert.assertEquals(authors.getId(), authorsTest.getId());
        Assert.assertEquals(authors.getName(), authorsTest.getName());
        Assert.assertEquals(authors.getBirthDate(), authorsTest.getBirthDate());
    }

    @Test
    @InSequence(8)
    public void removeAuthorsTest() {
        Cookie cookieSessionId = login(username, password);

        AuthorMinimumDTO authors = oraculoAuthors.get(0);
        BookMinimumDTO book = oraculo.get(0);

        Response response = target.path(bookPath).path(book.getId().toString())
                .path(authorsPath).path(authors.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
