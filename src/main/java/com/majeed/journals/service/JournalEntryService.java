package com.majeed.journals.service;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.entity.User;
import com.majeed.journals.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    public List<Journal> getAllJournals() {
        return journalEntryRepository.findAll();
    }


    @Transactional
    public Journal saveJournal(Journal journal, String username) {
        Journal saved = null;
        try {
            User user = userService.findUserByUsername(username);
            journal.setDateTime(LocalDateTime.now());
            saved = journalEntryRepository.save(journal);
            user.getJournals().add(journal);
            userService.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving journal : ", e);
        }
        return saved;
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public Boolean deleteJournal(ObjectId id, User user) {
        boolean removed = false;
        try {
            removed = user.getJournals().removeIf(journal -> journal.getId().equals(id));
            if (removed) {
                userService.updateUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting journal : ", e);
        }
        return removed;
    }

    public Journal saveJournal(Journal journal) {
        journal.setDateTime(LocalDateTime.now());
        return journalEntryRepository.save(journal);
    }
}
