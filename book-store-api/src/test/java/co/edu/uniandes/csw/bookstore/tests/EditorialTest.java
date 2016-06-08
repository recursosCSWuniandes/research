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
import co.edu.uniandes.csw.bookstore.dtos.basic.EditorialBasicDTO;
import co.edu.uniandes.csw.bookstore.dtos.minimum.EditorialMinimumDTO;
import co.edu.uniandes.csw.bookstore.dtos.minimum.BookMinimumDTO;
import co.edu.uniandes.csw.bookstore.resources.EditorialService;
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
public class EditorialTest {

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private final String editorialPath = "editorials";
    private final static List<EditorialMinimumDTO> oraculo = new ArrayList<>();
    private final String booksPath = "books";
    private final static List<BookMinimumDTO> oraculoBooks = new ArrayList<>();
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
                        .resolve("co.edu.uniandes.csw:auth-utils:0.1.3")
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(EditorialService.class.getPackage())
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
            EditorialBasicDTO editorial = factory.manufacturePojo(EditorialBasicDTO.class);
            editorial.setId(i + 1L);

            oraculo.add(editorial);

            BookMinimumDTO books = factory.manufacturePojo(BookMinimumDTO.class);
            books.setId(i + 1L);
            oraculoBooks.add(books);
        }
    }

    public Cookie login(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
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
    public void createEditorialTest() throws IOException {
        EditorialMinimumDTO editorial = oraculo.get(0);
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(editorialPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialMinimumDTO  editorialTest = (EditorialMinimumDTO) response.readEntity(EditorialMinimumDTO.class);
        Assert.assertEquals(editorial.getId(), editorialTest.getId());
        Assert.assertEquals(editorial.getName(), editorialTest.getName());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    @InSequence(2)
    public void getEditorialById() {
        Cookie cookieSessionId = login(username, password);
        EditorialMinimumDTO editorialTest = target.path(editorialPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(EditorialMinimumDTO.class);
        Assert.assertEquals(editorialTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(editorialTest.getName(), oraculo.get(0).getName());
    }

    @Test
    @InSequence(3)
    public void listEditorialTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(editorialPath)
                .request().cookie(cookieSessionId).get();
        String listEditorial = response.readEntity(String.class);
        List<EditorialMinimumDTO> listEditorialTest = new ObjectMapper().readValue(listEditorial, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listEditorialTest.size());
    }

    @Test
    @InSequence(4)
    public void updateEditorialTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        EditorialMinimumDTO editorial = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EditorialMinimumDTO editorialChanged = factory.manufacturePojo(EditorialMinimumDTO.class);
        editorial.setName(editorialChanged.getName());
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialMinimumDTO editorialTest = (EditorialMinimumDTO) response.readEntity(EditorialMinimumDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(editorial.getName(), editorialTest.getName());
    }

    @Test
    @InSequence(9)
    public void deleteEditorialTest() {
        Cookie cookieSessionId = login(username, password);
        EditorialMinimumDTO editorial = oraculo.get(0);
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

    @Test
    @InSequence(5)
    public void addBooksTest() {
        Cookie cookieSessionId = login(username, password);

        BookMinimumDTO books = oraculoBooks.get(0);
        EditorialMinimumDTO editorial = oraculo.get(0);


        Response response = target.path("books")
                .request().cookie(cookieSessionId)
                .post(Entity.entity(books, MediaType.APPLICATION_JSON));

        BookMinimumDTO booksTest = (BookMinimumDTO) response.readEntity(BookMinimumDTO.class);
        Assert.assertEquals(books.getId(), booksTest.getId());
        Assert.assertEquals(books.getName(), booksTest.getName());
        Assert.assertEquals(books.getDescription(), booksTest.getDescription());
        Assert.assertEquals(books.getIsbn(), booksTest.getIsbn());
        Assert.assertEquals(books.getImage(), booksTest.getImage());
        Assert.assertEquals(books.getPublishDate(), booksTest.getPublishDate());
        Assert.assertEquals(Created, response.getStatus());

        response = target.path(editorialPath).path(editorial.getId().toString())
                .path(booksPath).path(books.getId().toString())
                .request().cookie(cookieSessionId)
                .post(Entity.entity(books, MediaType.APPLICATION_JSON));

        booksTest = (BookMinimumDTO) response.readEntity(BookMinimumDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(books.getId(), booksTest.getId());
    }

    @Test
    @InSequence(6)
    public void listBooksTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        EditorialMinimumDTO editorial = oraculo.get(0);

        Response response = target.path(editorialPath)
                .path(editorial.getId().toString())
                .path(booksPath)
                .request().cookie(cookieSessionId).get();

        String booksList = response.readEntity(String.class);
        List<BookMinimumDTO> booksListTest = new ObjectMapper().readValue(booksList, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, booksListTest.size());
    }

    @Test
    @InSequence(7)
    public void getBooksTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        BookMinimumDTO books = oraculoBooks.get(0);
        EditorialMinimumDTO editorial = oraculo.get(0);

        BookMinimumDTO booksTest = target.path(editorialPath)
                .path(editorial.getId().toString()).path(booksPath)
                .path(books.getId().toString())
                .request().cookie(cookieSessionId).get(BookMinimumDTO.class);

        Assert.assertEquals(books.getId(), booksTest.getId());
        Assert.assertEquals(books.getName(), booksTest.getName());
        Assert.assertEquals(books.getDescription(), booksTest.getDescription());
        Assert.assertEquals(books.getIsbn(), booksTest.getIsbn());
        Assert.assertEquals(books.getImage(), booksTest.getImage());
        Assert.assertEquals(books.getPublishDate(), booksTest.getPublishDate());
    }

    @Test
    @InSequence(8)
    public void removeBooksTest() {
        Cookie cookieSessionId = login(username, password);

        BookMinimumDTO books = oraculoBooks.get(0);
        EditorialMinimumDTO editorial = oraculo.get(0);

        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .path(booksPath).path(books.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
