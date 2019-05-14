package pl.sii.conference.view;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sii.conference.domain.model.Lecture;
import pl.sii.conference.service.LectureService;
import pl.sii.conference.service.ReservationService;
import pl.sii.conference.service.UserService;

import java.util.List;


@SpringUI
@StyleSheet({"http://localhost:8080/styles.css"})
public class MainView extends UI {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSessionDetails userSessionDetails;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.addComponent(new Label("IT CONFERENCE"));
        verticalLayout.addComponent(new Label("Schedule:"));

        GridLayout gridLayout = new GridLayout(4, 5);

        gridLayout.setWidth("600px");
        gridLayout.setHeight("200px");

        addLabelsToGrid(gridLayout);

        addLecturesToGrid(gridLayout);

        gridLayout.addStyleName("main-grid");
        verticalLayout.addComponent(gridLayout);

        if (userSessionDetails.isLoggedIn()) {
            //TODO: add view when logged in
            verticalLayout.addComponent(new Label( userSessionDetails.getLogin() + " is logged in!"));
        } else {
            addLoginToView(verticalLayout);
            verticalLayout.addComponent(new Label("If you don't have account please register"));
            addRegistrationToView(verticalLayout);
        }

        setContent(verticalLayout);
    }

    private void addLoginToView(VerticalLayout verticalLayout) {
        HorizontalLayout loginHorizontalLayout = new HorizontalLayout();

        TextField loginField = new TextField();
        loginField.setValue("user login");

        TextField emailField = new TextField();
        emailField.setValue("user email");

        Button loginButton = new Button("Log In", clickEvent -> {
            userService.userLogIn(loginField.getValue(), emailField.getValue());
            Page.getCurrent().reload();});
        loginButton.addStyleName("primary");

        loginHorizontalLayout.addComponent(loginField);
        loginHorizontalLayout.addComponent(emailField);
        loginHorizontalLayout.addComponent(loginButton);

        verticalLayout.addComponent(loginHorizontalLayout);
    }

    private void addRegistrationToView(VerticalLayout verticalLayout) {
        HorizontalLayout registerHorizontalLayout = new HorizontalLayout();

        TextField loginRegisterField = new TextField();
        loginRegisterField.setValue("user login");

        TextField emailRegisterField = new TextField();
        emailRegisterField.setValue("user email");

        Button registerButton = new Button("Register", clickEvent -> {
            userService.registerUser(loginRegisterField.getValue(), emailRegisterField.getValue());
            Notification.show("register successful");});
        registerButton.addStyleName("primary");

        registerHorizontalLayout.addComponent(loginRegisterField);
        registerHorizontalLayout.addComponent(emailRegisterField);
        registerHorizontalLayout.addComponent(registerButton);

        verticalLayout.addComponent(registerHorizontalLayout);

    }

    private void addLabelsToGrid(GridLayout gridLayout) {
        Label timeLabel = new Label("Time slot:");
        gridLayout.addComponent(timeLabel, 0, 0);
        Label timeSlot1 = new Label("01.06.2019 10:00");
        gridLayout.addComponent(timeSlot1, 0, 1);
        Label timeSlot2 = new Label("01.06.2019 12:00");
        gridLayout.addComponent(timeSlot2, 0, 2);
        Label timeSlot3 = new Label("02.06.2019 10:00");
        gridLayout.addComponent(timeSlot3, 0, 3);
        Label timeSlot4 = new Label("02.06.2019 12:00");
        gridLayout.addComponent(timeSlot4, 0, 4);
        Label category1 = new Label("Frontend");
        gridLayout.addComponent(category1, 1, 0);
        Label category2 = new Label("Backend");
        gridLayout.addComponent(category2, 2, 0);
        Label category3 = new Label("Tests");
        gridLayout.addComponent(category3, 3, 0);
    }

    private void addLecturesToGrid(GridLayout gridLayout) {
        List<Lecture> lectureList = lectureService.getAllLectures();
        for(Lecture lecture : lectureList) {
          Button quietButton = new Button(lecture.getTitle());
          quietButton.addStyleName("quiet");
          gridLayout.addComponent(quietButton, lecture.getCategoryId(), lecture.getTimeSlotId());
        }
    }
}