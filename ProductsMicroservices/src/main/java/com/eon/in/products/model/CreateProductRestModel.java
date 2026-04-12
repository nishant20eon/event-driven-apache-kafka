package com.eon.in.products.model;

import java.math.BigDecimal;

public class CreateProductRestModel {

        private String title;
        private String description;
        private BigDecimal price;

        // Getters and setters for the fields

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

        // default constructor
        public CreateProductRestModel() {
        }
        // parameterized constructor for easier object creation
        public CreateProductRestModel(String name, String description, BigDecimal price) {
            this.title = name;
            this.description = description;
            this.price = price;
        }

        // implement toString() method for better logging and debugging
        @Override
        public String toString() {
            return "CreateProductModel{" +
                    "name='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", price=" + price +
                    '}';
        }
}
