package co.edu.uniandes.csw.bookstore.api;

import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.List;

public interface IReviewLogic {

    public int countReviews(Long bookId);

    public List<ReviewEntity> getReviews(Long bookId);

    public ReviewEntity getReview(Long bookId, Long reviewId);

    public ReviewEntity createReview(Long bookId, ReviewEntity review);

    public ReviewEntity updateReview(Long bookId, ReviewEntity review);

    public void deleteReview(Long BookId, Long reviewId);
}
