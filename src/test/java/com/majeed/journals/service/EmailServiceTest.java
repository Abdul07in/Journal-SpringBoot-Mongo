package com.majeed.journals.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        Assertions.assertEquals(true, emailService.sendEmail("dkanoor786@gmail.com", "Test", "This is a test email"));
    }


    @Test
    public void testSendHtmlEmail() {
        String[] emails = {"dkanoor786@gmail.com" , "kanoor.aabra@gmail.com" , "mehmood.kanoor240@gmail.com"};
        Assertions.assertEquals(true , emailService.sendMultiMediaEmail(emails ,"OTP VERIFICATION", "762532" , "D:\\MI A2\\Pictures\\WhatsApp Images\\IMG-20191013-WA0003.jpg"));
    }
}
