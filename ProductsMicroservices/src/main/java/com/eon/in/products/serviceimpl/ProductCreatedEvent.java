package com.eon.in.products.serviceimpl;

import java.math.BigDecimal;

public class ProductCreatedEvent {

    private String title;
    private String description;
    private BigDecimal price;
    private String productId;

    // create constructor for the event
    public ProductCreatedEvent(String title, String description, BigDecimal price, String productId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.productId = productId;
    }

    // default constructor for deserialization
    public ProductCreatedEvent() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    // implement toString() method for better logging and debugging
    @Override
    public String toString() {
        return "ProductCreatedEvent{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productId='" + productId + '\'' +
                '}';
    }



}
