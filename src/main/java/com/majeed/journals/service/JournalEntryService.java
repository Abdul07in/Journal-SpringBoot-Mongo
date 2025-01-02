package com.majeed.journals.service;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalEntryService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }

    public List<Journal> getAllJournals() {
        return journalEntryRepository.findAll();
    }


    public Journal saveJournal(Journal journal) {
        return journalEntryRepository.save(journal);
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournal(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
