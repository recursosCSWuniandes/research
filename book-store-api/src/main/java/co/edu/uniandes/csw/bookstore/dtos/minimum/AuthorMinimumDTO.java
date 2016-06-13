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
package co.edu.uniandes.csw.bookstore.dtos.minimum;

import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import uk.co.jemos.podam.common.PodamStrategyValue;
import co.edu.uniandes.csw.crud.api.podam.strategy.DateStrategy;
import java.io.Serializable;

/**
 * @generated
 */
@XmlRootElement
public class AuthorMinimumDTO implements Serializable {

    private Long id;
    private String name;
    @PodamStrategyValue(DateStrategy.class)
    private Date birthDate;

    /**
     * @generated
     */
    public AuthorMinimumDTO() {
    }

    /**
     * @param entity
     * @generated
     */
    public AuthorMinimumDTO(AuthorEntity entity) {
	   if (entity!=null){
        this.id=entity.getId();
        this.name=entity.getName();
        this.birthDate=entity.getBirthDate();
       }
    }

    /**
     * @generated
     */
    public AuthorEntity toEntity() {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setBirthDate(this.getBirthDate());
    return entity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthdate) {
        this.birthDate = birthdate;
    }

}
