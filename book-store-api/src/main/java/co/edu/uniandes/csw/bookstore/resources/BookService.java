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
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.dtos.basic.BookBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.dtos.basic.AuthorBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import static java.util.stream.Collectors.toList;

/**
 * @generated
 */
@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookService {

    @Inject
    private IBookLogic bookLogic;
    @Context
    private HttpServletResponse response;
    @QueryParam("page")
    private Integer page;
    @QueryParam("maxRecords")
    private Integer maxRecords;

    private List<BookBasicDTO> listEntity2DTO(List<BookEntity> entityList) {
        return entityList.parallelStream().map(BookBasicDTO::new).collect(toList());
    }

    private List<AuthorBasicDTO> authorsListEntity2DTO(List<AuthorEntity> entityList) {
        return entityList.parallelStream().map(AuthorBasicDTO::new).collect(toList());
    }

    private List<AuthorEntity> authorsListDTO2Entity(List<AuthorBasicDTO> dtos) {
        return dtos.parallelStream().map(AuthorBasicDTO::toEntity).collect(toList());
    }

    /**
     * Obtiene la lista de los registros de Book.
     *
     * @return Colección de objetos de BookBasicDTO cada uno con sus respectivos
     * Review
     * @generated
     */
    @GET
    public List<BookBasicDTO> getBooks() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", bookLogic.countBooks());
            return listEntity2DTO(bookLogic.getBooks(page, maxRecords));
        }
        return listEntity2DTO(bookLogic.getBooks());
    }

    /**
     * Obtiene los datos de una instancia de Book a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BookBasicDTO con los datos del Book consultado y sus
     * Review
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public BookBasicDTO getBook(@PathParam("id") Long id) {
        return new BookBasicDTO(bookLogic.getBook(id));
    }

    /**
     * Se encarga de crear un book en la base de datos.
     *
     * @param dto Objeto de BookBasicDTO con los datos nuevos
     * @return Objeto de BookBasicDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public BookBasicDTO createBook(BookBasicDTO dto) {
        return new BookBasicDTO(bookLogic.createBook(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Book.
     *
     * @param id Identificador de la instancia de Book a modificar
     * @param dto Instancia de BookBasicDTO con los nuevos datos.
     * @return Instancia de BookBasicDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public BookBasicDTO updateBook(@PathParam("id") Long id, BookBasicDTO dto) {
        BookEntity entity = dto.toEntity();
        entity.setId(id);
        BookEntity oldEntity = bookLogic.getBook(id);
        entity.setAuthors(oldEntity.getAuthors());
        entity.setReviews(oldEntity.getReviews());
        return new BookBasicDTO(bookLogic.updateBook(entity));
    }

    /**
     * Elimina una instancia de Book de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteBook(@PathParam("id") Long id) {
        bookLogic.deleteBook(id);
    }

    /**
     * Obtiene una colección de instancias de AuthorBasicDTO asociadas a una
     * instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorBasicDTO asociadas a la
     * instancia de Book
     * @generated
     */
    @GET
    @Path("{bookId: \\d+}/authors")
    public List<AuthorBasicDTO> listAuthors(@PathParam("bookId") Long bookId) {
        return authorsListEntity2DTO(bookLogic.listAuthors(bookId));
    }

    /**
     * Obtiene una instancia de Author asociada a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @generated
     */
    @GET
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorBasicDTO getAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return new AuthorBasicDTO(bookLogic.getAuthors(bookId, authorId));
    }

    /**
     * Asocia un Author existente a un Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @return Instancia de AuthorBasicDTO que fue asociada a Book
     * @generated
     */
    @POST
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorBasicDTO addAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return new AuthorBasicDTO(bookLogic.addAuthors(bookId, authorId));
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authors Colección de instancias de AuthorDTO a asociar a instancia
     * de Book
     * @return Nueva colección de AuthorDTO asociada a la instancia de Book
     * @generated
     */
    @PUT
    @Path("{bookId: \\d+}/authors")
    public List<AuthorBasicDTO> replaceAuthors(@PathParam("bookId") Long bookId, List<AuthorBasicDTO> authors) {
        return authorsListEntity2DTO(bookLogic.replaceAuthors(bookId, authorsListDTO2Entity(authors)));
    }

    /**
     * Desasocia un Author existente de un Book existente
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @generated
     */
    @DELETE
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void removeAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.removeAuthors(bookId, authorId);
    }

    @Path("{bookId: \\d+}/reviews")
    public Class<ReviewService> getReviewService() {
        return ReviewService.class;
    }
}
