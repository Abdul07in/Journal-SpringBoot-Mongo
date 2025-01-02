package com.majeed.journals.repository;

import com.majeed.journals.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<Journal, ObjectId> {
}
