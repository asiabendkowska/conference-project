package pl.sii.conference.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceTest {

    private LectureService lectureService = new LectureService();

    @Test
    public void testGetAllLectures_GivenCorrectNumbersOfLectures_ExpectCorrectSize(){
        int actualSize = 12;
        int expectedSize = lectureService.getAllLectures().size();
        Assert.assertEquals(expectedSize,actualSize);

    }
}
