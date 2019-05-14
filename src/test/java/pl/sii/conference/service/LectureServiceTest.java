package pl.sii.conference.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sii.conference.service.LectureService;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceTest {
   private LectureService lectureService = new LectureService();



    @Test
    public void getAllLectures_CorrectNumbersOfLEctures(){
        int actualSize = 12;
        int expectedSize = lectureService.getAllLectures().size();
        Assert.assertEquals(expectedSize,actualSize);

    }
}
