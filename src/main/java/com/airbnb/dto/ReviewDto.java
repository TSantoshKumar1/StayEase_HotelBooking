package com.airbnb.dto;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;

public class ReviewDto {

    private Long id;

    private String content;

    private Property property;

    private PropertyUser propertyUser;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
