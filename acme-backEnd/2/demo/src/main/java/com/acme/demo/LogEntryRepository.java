package com.acme.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {
    List<LogEntry> findBySearchField(String searchField);
}
