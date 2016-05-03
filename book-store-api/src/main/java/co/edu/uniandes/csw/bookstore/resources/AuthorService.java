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
import co.edu.uniandes.csw.bookstore.api.IAuthorLogic;
import co.edu.uniandes.csw.bookstore.dtos.basic.AuthorBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.dtos.basic.BookBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.ArrayList;
/**
 * @generated
 */
@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorService {

    @Inject private IAuthorLogic authorLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    private List<AuthorBasicDTO> listEntity2DTO(List<AuthorEntity> entityList){
        List<AuthorBasicDTO> list = new ArrayList<>();
        for (AuthorEntity entity : entityList) {
            list.add(new AuthorBasicDTO(entity));
        }
        return list;
    }

    private List<BookBasicDTO> booksListEntity2DTO(List<BookEntity> entityList){
        List<BookBasicDTO> list = new ArrayList<>();
        for (BookEntity entity : entityList) {
            list.add(new BookBasicDTO(entity));
        }
        return list;
    }

    private List<BookEntity> booksListDTO2Entity(List<BookBasicDTO> dtos){
        List<BookEntity> list = new ArrayList<>();
        for (BookBasicDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Obtiene la lista de los registros de Book.
     *
     * @return Colección de objetos de AuthorBasicDTO cada uno con sus respectivos Review
     * @generated
     */
    @GET
    public List<AuthorBasicDTO> getAuthors() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", authorLogic.countAuthors());
            return listEntity2DTO(authorLogic.getAuthors(page, maxRecords));
        }
        return listEntity2DTO(authorLogic.getAuthors());
    }

    /**
     * Obtiene los datos de una instancia de Book a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de AuthorBasicDTO con los datos del Book consultado y sus Review
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public AuthorBasicDTO getAuthor(@PathParam("id") Long id) {
        return new AuthorBasicDTO(authorLogic.getAuthor(id));
    }

    /**
     * Se encarga de crear un book en la base de datos.
     *
     * @param dto Objeto de AuthorBasicDTO con los datos nuevos
     * @return Objeto de AuthorBasicDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public AuthorBasicDTO createAuthor(AuthorBasicDTO dto) {
        return new AuthorBasicDTO(authorLogic.createAuthor(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Book.
     *
     * @param id Identificador de la instancia de Book a modificar
     * @param dto Instancia de AuthorBasicDTO con los nuevos datos.
     * @return Instancia de AuthorBasicDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public AuthorBasicDTO updateAuthor(@PathParam("id") Long id, AuthorBasicDTO dto) {
        AuthorEntity entity = dto.toEntity();
        entity.setId(id);
        AuthorEntity oldEntity = authorLogic.getAuthor(id);
        entity.setBooks(oldEntity.getBooks());
        return new AuthorBasicDTO(authorLogic.updateAuthor(entity));
    }

    /**
     * Elimina una instancia de Book de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteAuthor(@PathParam("id") Long id) {
        authorLogic.deleteAuthor(id);
    }

    /**
     * Obtiene una colección de instancias de BookBasicDTO asociadas a una
     * instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @return Colección de instancias de BookBasicDTO asociadas a la instancia de Author
     * @generated
     */
    @GET
    @Path("{authorId: \\d+}/books")
    public List<BookBasicDTO> listBooks(@PathParam("authorId") Long authorId) {
        return booksListEntity2DTO(authorLogic.listBooks(authorId));
    }

    /**
     * Obtiene una instancia de Book asociada a una instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     * @generated
     */
    @GET
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookBasicDTO getBooks(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return new BookBasicDTO(authorLogic.getBooks(authorId, bookId));
    }

    /**
     * Asocia un Book existente a un Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     * @return Instancia de BookBasicDTO que fue asociada a Author
     * @generated
     */
    @POST
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookBasicDTO addBooks(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return new BookBasicDTO(authorLogic.addBooks(authorId, bookId));
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param books Colección de instancias de BookDTO a asociar a instancia de Author
     * @return Nueva colección de BookDTO asociada a la instancia de Author
     * @generated
     */
    @PUT
    @Path("{authorId: \\d+}/books")
    public List<BookBasicDTO> replaceBooks(@PathParam("authorId") Long authorId, List<BookBasicDTO> books) {
        return booksListEntity2DTO(authorLogic.replaceBooks(authorId, booksListDTO2Entity(books)));
    }

    /**
     * Desasocia un Book existente de un Author existente
     *
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     * @generated
     */
    @DELETE
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public void removeBooks(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        authorLogic.removeBooks(authorId, bookId);
    }
}
