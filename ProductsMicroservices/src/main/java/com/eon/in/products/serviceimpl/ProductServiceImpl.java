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
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    @Override
    public String createProduct(CreateProductRestModel createProductRestModel) throws ExecutionException, InterruptedException {

        String productId = UUID.randomUUID().toString();

        // TODO: persist the product to the database (e.g., using a repository or DAO)
        // For now, we will just log the product creation event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                createProductRestModel.getTitle(),
                createProductRestModel.getDescription(),
                createProductRestModel.getPrice(),
                productId
        );

        LOGGER.info("Before publishing a ProductCreatedEvent to Kafka: {}", productCreatedEvent);
        // Publish the event to Kafka, and wait for the send operation to complete before returning the product ID (synchronous send)
        SendResult<String,ProductCreatedEvent> result = kafkaTemplate.send("products-created-events-topic",productId, productCreatedEvent).get();

        LOGGER.info("Partition: {}, Offset: {}, Timestamp: {}, Topic: {}",
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().timestamp(),
                result.getRecordMetadata().topic()
        );

        LOGGER.info("After publishing a ProductCreatedEvent to Kafka: {}", productCreatedEvent);

        return productId;

    }
}
