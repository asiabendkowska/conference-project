package pl.sii.conference.service;

import org.springframework.stereotype.Service;
import pl.sii.conference.domain.model.Lecture;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {

    private static List<Lecture> lectureList = new ArrayList<>(12);

    static {
        lectureList.add(new Lecture(1, "React", 1, 1));
        lectureList.add((new Lecture(2, "Angular", 2,1 )));
        lectureList.add((new Lecture(3, "Vaadin", 3,1 )));
        lectureList.add((new Lecture(4, "AJAX", 4,1 )));
        lectureList.add(new Lecture(5, "Hibernate", 1, 2));
        lectureList.add(new Lecture(6, "REST", 2, 2));
        lectureList.add(new Lecture(7, "Spring", 3, 2));
        lectureList.add(new Lecture(8, "SQL", 4, 2));
        lectureList.add(new Lecture(9, "JUnit", 1, 3));
        lectureList.add(new Lecture(10, "Mockito", 2, 3));
        lectureList.add(new Lecture(11, "TDD", 3, 3));
        lectureList.add(new Lecture(12, "Selenium", 4, 3));

    }

    public List<Lecture> getAllLectures() {
        return lectureList;
    }
}
