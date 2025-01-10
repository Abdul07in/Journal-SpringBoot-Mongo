package com.majeed.journals.scheduler;

import com.majeed.journals.entity.Journal;
import com.majeed.journals.entity.User;
import com.majeed.journals.repository.UserRepositoryImpl;
import com.majeed.journals.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendEmail() {
        List<User> users = userRepository.getUsersForSentimental();
        for (User user : users) {
            List<Journal> journals = user.getJournals();
            List<String> filteredList = journals.stream().filter(x -> x.getDateTime().isAfter(LocalDateTime.now().minusDays(7))).map(Journal::getContent).collect(Collectors.toList());
            String entry = String.join(" | ", filteredList);

        }
    }
}
