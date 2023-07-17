package com.acme.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "logs")
public class LogEntry {
    @Id
    private String id;
    private LocalDateTime timestamp;
    private String searchField;
    private String responseBody;

    public LogEntry(LocalDateTime timestamp, String searchField, String responseBody) {
        this.timestamp = timestamp;
        this.searchField = searchField;
        this.responseBody = responseBody;
    }

    public LogEntry() {
    }

    public LogEntry(String id, LocalDateTime timestamp, String searchField, String responseBody) {
        this.id = id;
        this.timestamp = timestamp;
        this.searchField = searchField;
        this.responseBody = responseBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
// Agrega los getters y setters necesarios
}
