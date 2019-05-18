package pl.sii.conference.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    private EmailService emailService = new EmailService();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testSendConfirmation_GivenEmailAndTitle_ExpectCorrectMessageWrittenToFile() throws IOException {
        File tempFile = testFolder.newFile("file.txt");

        String email = "test@gmail.com";
        String title = "abcd";
        String reservationMessage = "Confirmation of reservation for lecture: ";
        String delimiter = "||";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = formatter.format(LocalDateTime.now());
        String expected = time + delimiter + email + delimiter + reservationMessage + title;

        emailService.setFilePath(tempFile.getPath());
        emailService.sendConfirmation(email, title);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        Assert.assertEquals(1, lines.size());
        Assert.assertEquals(expected, lines.get(0));
    }
}
