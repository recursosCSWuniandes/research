package co.edu.uniandes.csw.bookstore.ejbs;

import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.api.IReviewLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import co.edu.uniandes.csw.bookstore.persistence.ReviewPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@Stateless
public class ReviewLogic implements IReviewLogic {

    @Inject private ReviewPersistence persistence;

    @Inject
    private IBookLogic bookLogic;

    /**
     * Obtiene el número de registros de Review.
     *
     * @return Número de registros de Review.
     * @generated
     */
    public int countReviews() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Review que pertenecen a un Book.
     *
     * @param bookid id del Book el cual es padre de los Reviews.
     * @return Colección de objetos de ReviewEntity.
     * @generated
     */
    @Override
    public List<ReviewEntity> getReviews(Long bookid) {
        BookEntity book = bookLogic.getBook(bookid);
        return book.getReviews();
    }

    /**
     * Obtiene la lista de los registros de Review que pertenecen a un Book indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param bookid id del Book el cual es padre de los Reviews.
     * @return Colección de objetos de ReviewEntity.
     * @generated
     */
    @Override
    public List<ReviewEntity> getReviews(Integer page, Integer maxRecords, Long bookid) {
        return persistence.findAll(page, maxRecords, bookid);
    }

    /**
     * Obtiene los datos de una instancia de Review a partir de su ID.
     *
     * @param reviewid) Identificador del Review a consultar
     * @return Instancia de ReviewEntity con los datos del Review consultado.
     * @generated
     */
    @Override
    public ReviewEntity getReview(Long reviewid) {
        try {
            return persistence.find(reviewid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Review no existe");
        }
    }

    /**
     * Se encarga de crear un Review en la base de datos.
     *
     * @param entity Objeto de ReviewEntity con los datos nuevos
     * @param bookid id del Book el cual sera padre del nuevo Review.
     * @return Objeto de ReviewEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ReviewEntity createReview(Long bookid, ReviewEntity entity) {
        BookEntity book = bookLogic.getBook(bookid);
        entity.setBook(book);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Review.
     *
     * @param entity Instancia de ReviewEntity con los nuevos datos.
     * @param bookid id del Book el cual sera padre del Review actualizado.
     * @return Instancia de ReviewEntity con los datos actualizados.
     * @generated
     */
    @Override
    public ReviewEntity updateReview(Long bookid, ReviewEntity entity) {
        BookEntity book = bookLogic.getBook(bookid);
        entity.setBook(book);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Review de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param bookid id del Book el cual es padre del Review.
     * @generated
     */
    @Override
    public void deleteReview(Long id) {
        ReviewEntity old = getReview(id);
        persistence.delete(old.getId());
    }
  
}
