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
        return bookLogic.getBook(bookId).getReviews();
    }

    @Override
    public ReviewEntity getReview(Long bookId, Long reviewId) {
        return persistence.find(bookId, reviewId);
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
    public void deleteReview(Long BookId, Long reviewId) {
        ReviewEntity review = getReview(BookId, reviewId);
        persistence.delete(review.getId());
    }
}
