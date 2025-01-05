package com.majeed.journals.controller;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.entity.User;
import com.majeed.journals.service.JournalEntryService;
import com.majeed.journals.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    private User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(authentication.getName());
    }

    @GetMapping()
    public ResponseEntity<?> getJournalsByUser() {
        User user = getAuthUser();
        List<Journal> journals = user.getJournals();
        if (journals != null && !journals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(journals);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal found for username " + user.getUsername());
        }
    }

    @PostMapping()
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        try {
            journalEntryService.saveJournal(journal, getAuthUser().getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(journal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id) {
        List<Journal> journalsList = getAuthUser().getJournals().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!journalsList.isEmpty()) {
            Optional<Journal> journal = journalEntryService.getJournalById(id);
            if (journal.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(journal.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal found for id " + id);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        try {
            if (journalEntryService.deleteJournal(id, getAuthUser())) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Journal deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal found for id " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting Journal : " + e.getMessage());
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @RequestBody Journal journal) {
        User user = getAuthUser();
        List<Journal> journalsList = user.getJournals().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        try {
            if (!journalsList.isEmpty()) {
                Journal old = journalEntryService.getJournalById(id).orElse(null);
                if (old != null) {
                    old.setTitle(journal.getTitle() != null ? journal.getTitle() : old.getTitle());
                    old.setContent(journal.getContent() != null ? journal.getContent() : old.getContent());
                    old.setDateTime(LocalDateTime.now());
                    journalEntryService.saveJournal(old);
                    return ResponseEntity.status(HttpStatus.OK).body(old);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
