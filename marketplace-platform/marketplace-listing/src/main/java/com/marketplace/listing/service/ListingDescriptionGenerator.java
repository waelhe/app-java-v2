package com.marketplace.listing.service;

import com.marketplace.listing.entity.Listing;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class ListingDescriptionGenerator {

    private final ChatClient chatClient;

    public ListingDescriptionGenerator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String generateDescription(Listing listing) {
        String template = """
            Generate a compelling marketplace listing description.
            Title: {title}
            Type: {type}
            Category: {category}
            Base Price: {price} {currency}
            Address: {address}
            
            Please write a detailed, professional, and attractive description that highlights the features 
            and benefits of this {type} listing. Use a tone suitable for {category}.
            """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of(
            "title", listing.getTitle(),
            "type", listing.getType().name(),
            "category", listing.getCategory().getName(),
            "price", listing.getBasePrice(),
            "currency", listing.getCurrency(),
            "address", listing.getAddress() != null ? listing.getAddress() : "N/A"
        ));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
