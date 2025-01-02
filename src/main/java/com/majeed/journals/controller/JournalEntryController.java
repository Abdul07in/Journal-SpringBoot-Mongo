package com.majeed.journals.controller;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping
    public List<Journal> getJournals() {
        return journalEntryService.getAllJournals();
    }

    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        journal.setDateTime(LocalDateTime.now());
        return ResponseEntity.ok(journalEntryService.saveJournal(journal));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        return ResponseEntity.of(journalEntryService.getJournalById(id));
    }

    @DeleteMapping("/id/{id}")
    public Boolean deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteJournal(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable ObjectId id, @RequestBody Journal journal) {
        Journal old = journalEntryService.getJournalById(id).orElse(null);
        if (old != null) {
            old.setTitle(journal.getTitle() != null ? journal.getTitle() : old.getTitle());
            old.setContent(journal.getContent() != null ? journal.getContent() : old.getContent());
            old.setDateTime(LocalDateTime.now());
            journalEntryService.saveJournal(old);
            return ResponseEntity.ok(journal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
