package pl.sii.conference.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Service
public class EmailService {
    private static final String FILE_PATH = "resources\\static\\confirmation.txt";
    private static final String RESERVATION_MESSAGE = "Confirmation of reservation for lecture: ";
    //TODO: create file if not exists

    void sendConfirmation(String email, String lectureTitle) throws FileNotFoundException {
        PrintWriter writeToFile = new PrintWriter(FILE_PATH);
        LocalDateTime localDateTime = LocalDateTime.now();
        writeToFile.println(localDateTime + " " + email + " " + RESERVATION_MESSAGE + lectureTitle);
        writeToFile.close();
    }
}
