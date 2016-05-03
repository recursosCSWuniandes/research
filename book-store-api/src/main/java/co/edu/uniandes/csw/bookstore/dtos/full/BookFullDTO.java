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
package co.edu.uniandes.csw.bookstore.dtos.full;

import co.edu.uniandes.csw.bookstore.dtos.minimum.*;
import co.edu.uniandes.csw.bookstore.dtos.basic.BookBasicDTO;
import co.edu.uniandes.csw.bookstore.entities.*;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.List;
import java.util.ArrayList;

/**
 * @generated
 */
@XmlRootElement
public class BookFullDTO extends BookBasicDTO{

    @PodamExclude
    private List<ReviewMinimumDTO> reviews = new ArrayList<>();

    /**
     * @generated
     */
    public BookFullDTO() {
        super();
    }

    /**
     * @param entity
     * @generated
     */
    public BookFullDTO(BookEntity entity) {
        super(entity);      
        for (ReviewEntity obj : entity.getReviews()) {
            reviews.add(new ReviewMinimumDTO(obj));
        }
    }

    /**
     * @generated
     */
    @Override
    public BookEntity toEntity() {
        BookEntity entity = super.toEntity();
        List<ReviewEntity> reviewsList = new ArrayList<>();
        for (ReviewMinimumDTO reviews : this.getReviews()) {
            ReviewEntity obj=reviews.toEntity();
            obj.setBook(entity);
            reviewsList.add(obj);
        }
        entity.setReviews(reviewsList);
        return entity;
    }

    /**
     * @generated
     */
    public List<ReviewMinimumDTO> getReviews() {
        return reviews;
    }

    /**
     * @generated
     */
    public void setReviews(List<ReviewMinimumDTO> reviews) {
        this.reviews = reviews;
    }
}
