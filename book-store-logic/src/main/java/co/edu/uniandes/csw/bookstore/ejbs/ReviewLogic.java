package co.edu.uniandes.csw.bookstore.ejbs;

import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.api.IReviewLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import co.edu.uniandes.csw.bookstore.persistence.ReviewPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReviewLogic implements IReviewLogic {

    @Inject
    private ReviewPersistence persistence;

    @Inject
    private IBookLogic bookLogic;

    @Override
    public int countReviews(Long bookId) {
        return persistence.count();
    }

    @Override
    public List<ReviewEntity> getReviews(Long bookId) {
        return persistence.findAll(null, null, bookId);
    }

    @Override
    public ReviewEntity getReview(Long reviewId) {
        return persistence.find(reviewId);
    }

    @Override
    public ReviewEntity createReview(Long bookId, ReviewEntity review) {
        BookEntity book = bookLogic.getBook(bookId);
        review.setBook(book);
        return persistence.create(review);
    }

    @Override
    public ReviewEntity updateReview(Long bookId, ReviewEntity review) {
        BookEntity book = bookLogic.getBook(bookId);
        review.setBook(book);
        return persistence.update(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        ReviewEntity review = getReview(reviewId);
        persistence.delete(review.getId());
    }
}
