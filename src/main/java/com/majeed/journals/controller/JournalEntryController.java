package com.majeed.journals.controller;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getJournals() {
        List<Journal> journals = journalEntryService.getAllJournals();
        if(journals != null && !journals.isEmpty()) {
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        try {
            journal.setDateTime(LocalDateTime.now());
            journalEntryService.saveJournal(journal);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        if (journalEntryService.getJournalById(id).isPresent()) {
            return new ResponseEntity<>(journalEntryService.getJournalById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        try {
            journalEntryService.deleteJournal(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting Journal : " + e.getMessage());
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @RequestBody Journal journal) {
        Journal old = journalEntryService.getJournalById(id).orElse(null);
        if (old != null) {
            old.setTitle(journal.getTitle() != null ? journal.getTitle() : old.getTitle());
            old.setContent(journal.getContent() != null ? journal.getContent() : old.getContent());
            old.setDateTime(LocalDateTime.now());
            journalEntryService.saveJournal(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
