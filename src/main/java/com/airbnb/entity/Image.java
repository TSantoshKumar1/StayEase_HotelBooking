package com.airbnb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUser propertyUser;

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}