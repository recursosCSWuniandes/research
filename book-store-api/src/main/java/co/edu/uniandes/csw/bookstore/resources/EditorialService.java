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
import co.edu.uniandes.csw.bookstore.api.IEditorialLogic;
import co.edu.uniandes.csw.bookstore.dtos.basic.EditorialBasicDTO;
import co.edu.uniandes.csw.bookstore.dtos.full.EditorialFullDTO;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.dtos.basic.BookBasicDTO;
import co.edu.uniandes.csw.bookstore.dtos.full.BookFullDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.ArrayList;
/**
 * @generated
 */
@Path("/editorials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EditorialService {

    @Inject private IEditorialLogic editorialLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    private List<EditorialBasicDTO> listEntity2DTO(List<EditorialEntity> entityList){
        List<EditorialBasicDTO> list = new ArrayList<>();
        for (EditorialEntity entity : entityList) {
            list.add(new EditorialBasicDTO(entity));
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
     * @return Colección de objetos de EditorialBasicDTO cada uno con sus respectivos Review
     * @generated
     */
    @GET
    public List<EditorialBasicDTO> getEditorials() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", editorialLogic.countEditorials());
            return listEntity2DTO(editorialLogic.getEditorials(page, maxRecords));
        }
        return listEntity2DTO(editorialLogic.getEditorials());
    }

    /**
     * Obtiene los datos de una instancia de Book a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de EditorialFullDTO con los datos del Book consultado y sus Review
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public EditorialFullDTO getEditorial(@PathParam("id") Long id) {
        return new EditorialFullDTO(editorialLogic.getEditorial(id));
    }

    /**
     * Se encarga de crear un book en la base de datos.
     *
     * @param dto Objeto de EditorialFullDTO con los datos nuevos
     * @return Objeto de EditorialFullDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public EditorialFullDTO createEditorial(EditorialFullDTO dto) {
        return new EditorialFullDTO(editorialLogic.createEditorial(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Book.
     *
     * @param id Identificador de la instancia de Book a modificar
     * @param dto Instancia de EditorialFullDTO con los nuevos datos.
     * @return Instancia de EditorialFullDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public EditorialFullDTO updateEditorial(@PathParam("id") Long id, EditorialFullDTO dto) {
        EditorialEntity entity = dto.toEntity();
        entity.setId(id);
        EditorialEntity oldEntity = editorialLogic.getEditorial(id);
        entity.setBooks(oldEntity.getBooks());
        return new EditorialFullDTO(editorialLogic.updateEditorial(entity));
    }

    /**
     * Elimina una instancia de Book de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEditorial(@PathParam("id") Long id) {
        editorialLogic.deleteEditorial(id);
    }

    /**
     * Obtiene una colección de instancias de BookBasicDTO asociadas a una
     * instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @return Colección de instancias de BookBasicDTO asociadas a la instancia de Editorial
     * @generated
     */
    @GET
    @Path("{editorialId: \\d+}/books")
    public List<BookBasicDTO> listBooks(@PathParam("editorialId") Long editorialId) {
        return booksListEntity2DTO(editorialLogic.listBooks(editorialId));
    }

    /**
     * Obtiene una instancia de Book asociada a una instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     * @generated
     */
    @GET
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public BookFullDTO getBooks(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        return new BookFullDTO(editorialLogic.getBooks(editorialId, bookId));
    }

    /**
     * Asocia un Book existente a un Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     * @return Instancia de BookFullDTO que fue asociada a Editorial
     * @generated
     */
    @POST
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public BookFullDTO addBooks(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        return new BookFullDTO(editorialLogic.addBooks(editorialId, bookId));
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param books Colección de instancias de BookDTO a asociar a instancia de Editorial
     * @return Nueva colección de BookDTO asociada a la instancia de Editorial
     * @generated
     */
    @PUT
    @Path("{editorialId: \\d+}/books")
    public List<BookBasicDTO> replaceBooks(@PathParam("editorialId") Long editorialId, List<BookBasicDTO> books) {
        return booksListEntity2DTO(editorialLogic.replaceBooks(editorialId, booksListDTO2Entity(books)));
    }

    /**
     * Desasocia un Book existente de un Editorial existente
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     * @generated
     */
    @DELETE
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public void removeBooks(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        editorialLogic.removeBooks(editorialId, bookId);
    }
}
