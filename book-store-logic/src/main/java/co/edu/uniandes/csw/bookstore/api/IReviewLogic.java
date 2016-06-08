package co.edu.uniandes.csw.bookstore.api;

import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.List;

public interface IReviewLogic {
public int countReviews();
    public List<ReviewEntity> getReviews(Long bookid);
    public List<ReviewEntity> getReviews(Integer page, Integer maxRecords, Long bookid);
    public ReviewEntity getReview(Long reviewid);
    public ReviewEntity createReview(Long bookid, ReviewEntity entity);
    public ReviewEntity updateReview(Long bookid, ReviewEntity entity);
    public void deleteReview(Long id);
}
