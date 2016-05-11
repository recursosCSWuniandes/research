package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.api.IReviewLogic;
import co.edu.uniandes.csw.bookstore.dtos.basic.ReviewBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewService {

    @Inject
    private IReviewLogic logic;

    @GET
    public List<ReviewBasicDTO> getReviews(@PathParam("bookId") Long bookId) {
        List<ReviewEntity> reviews = logic.getReviews(bookId);
        return reviews.parallelStream().map(ReviewBasicDTO::new).collect(toList());
    }

    @GET
    @Path("{reviewId: \\d+}")
    public ReviewBasicDTO getReview(@PathParam("bookId") Long bookId, @PathParam("reviewId") Long reviewId) {
        ReviewEntity review = logic.getReview(bookId, reviewId);
        return new ReviewBasicDTO(review);
    }

    @POST
    public ReviewBasicDTO createReview(@PathParam("bookId") Long bookId, ReviewBasicDTO dto) {
        ReviewEntity entity = dto.toEntity();
        entity = logic.createReview(bookId, entity);
        return new ReviewBasicDTO(entity);
    }

    @PUT
    @Path("{reviewId: \\d+}")
    public ReviewBasicDTO updateReview(@PathParam("bookId") Long bookId, @PathParam("reviewId") Long reviewId, ReviewBasicDTO dto) {
        ReviewEntity entity = dto.toEntity();
        entity.setId(reviewId);
        entity = logic.updateReview(bookId, entity);
        return new ReviewBasicDTO(entity);
    }

    @DELETE
    @Path("{reviewId: \\d+}")
    public void deleteReview(@PathParam("bookId") Long bookId, @PathParam("reviewId") Long reviewId) {
        logic.deleteReview(bookId, reviewId);
    }
}