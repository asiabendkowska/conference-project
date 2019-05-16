package pl.sii.conference.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class EmailService {
    private static final String FILE_PATH = "src/main/resources/confirmation.txt";
    private static final String RESERVATION_MESSAGE = "Confirmation of reservation for lecture: ";



    void sendConfirmation(String email, String lectureTitle) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(now + " " + email + " " + RESERVATION_MESSAGE + lectureTitle + "\n");
        }
    }
}
