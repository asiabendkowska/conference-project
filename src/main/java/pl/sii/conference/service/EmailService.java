package pl.sii.conference.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Service
public class EmailService {
    private static final String filePath = "resources\\static\\confirmation.txt";
    private static final String reservationMessage = "Confirmation of reservation for lecture: ";

    void sendConfirmation(String email, String lectureTitle) throws FileNotFoundException {
        PrintWriter writeToFile = new PrintWriter(filePath);
        LocalDateTime localDateTime = LocalDateTime.now();
        writeToFile.println(localDateTime + " " + email + " " + reservationMessage + lectureTitle);
        writeToFile.close();
    }
}
