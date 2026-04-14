package com.eon.in.products.serviceimpl;

import com.eon.in.products.model.CreateProductRestModel;
import com.eon.in.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    @Override
    public String createProduct(CreateProductRestModel createProductRestModel) {

        String productId = UUID.randomUUID().toString();

        // TODO: persist the product to the database (e.g., using a repository or DAO)
        // For now, we will just log the product creation event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                createProductRestModel.getTitle(),
                createProductRestModel.getDescription(),
                createProductRestModel.getPrice(),
                productId
        );

        // Publish the event to Kafka
        CompletableFuture<SendResult<String,ProductCreatedEvent>> future = kafkaTemplate.send("products-created-events-topic",productId, productCreatedEvent);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                // Handle the exception (e.g., log it, retry, etc.)
                LOGGER.error("Failed to send product created event: " + ex.getMessage());
            } else {
                // Optionally, you can log the successful send result
                LOGGER.info("Product created event sent successfully for product ID: " + result.getRecordMetadata());
            }
        });
        // Wait for the future to complete , kind of blocking the thread until the event is sent, you can choose to handle it asynchronously if needed
        // Note: In a real application, you might want to handle this more gracefully (e.g., by returning a response immediately and handling the event sending in the background)
        // For simplicity, we will just block here until the event is sent using future.join(), but be aware that this can lead to performance issues if the Kafka broker is slow or unavailable
        LOGGER.info("Synchronously waiting for the product created event to be sent for product ID: " + productId);
        future.join();
        LOGGER.info("Finished waiting for the product created event to be sent for product ID: " + productId);
        return productId;

    }
}
