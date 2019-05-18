package pl.sii.conference.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Data
public class EmailService {
    private static final String RESERVATION_MESSAGE = "Confirmation of reservation for lecture: ";

    private String filePath = "confirmation.log";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    void sendConfirmation(String email, String lectureTitle) throws IOException {
        String formattedTime = formatter.format(LocalDateTime.now());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(formattedTime + "||" + email + "||" + RESERVATION_MESSAGE + lectureTitle + "\n");
        }
    }
}
