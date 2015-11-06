package frontend.student;

import java.net.URL;
import java.util.ArrayList;

import backend.courses.Course;
import backend.courses.CoursedSemester;
import backend.interfaces.ICourse;
import backend.manager.Manager;
import backend.users.Student;
import frontend.main.MViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SShowScheduleController extends MViewController {

	@FXML
	Label labelShowScheduleWelcomeMessage;	
	@FXML
	ChoiceBox<String> chBxSemesters;
	@FXML
	TextArea txASchedule;

	Student user = (Student) Manager.INSTANCE.currentUser;
	public static URL view = Object.class.getResource("/frontend/student/SShowSchedule.fxml");

	public void setUp() {
		super.setUp();

		ArrayList<String> semesterInfo = new ArrayList<String>();
		for (CoursedSemester coursedSemester : user.getCurriculum().getCoursedSemesters()) {
			int year = coursedSemester.getYear();
			String semester = coursedSemester.getSemester().getSemesterNumber();
			semesterInfo.add(year + " - " + semester);
		}
		int currentYear = user.getCurriculum().getCurrentSemester().getYear();
		String currentSemester = user.getCurriculum().getCurrentSemester().getSemester().getSemesterNumber();
		semesterInfo.add(currentYear + " - " + currentSemester);
		chBxSemesters.setItems(FXCollections.observableArrayList(semesterInfo));
		chBxSemesters.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						showSchedule(newValue);
					}
				});
	}
	
	private void showSchedule(String rawString) {
		String[] split = rawString.split(" - ");
		int year = Integer.valueOf(split[0]);
		int semester = Integer.valueOf(split[1]);
		String allSchedulesString = "";
		if (year == Manager.getYear() && semester == Integer.valueOf(Manager.INSTANCE.currentSemester.getSemester().getSemesterNumber())) {
			for (Course course : user.getCurriculum().getCurrentSemester().getCourses()) {
				String initials = course.getInitials();
				String section = "" + course.getSection();
				String name = course.getName();
				String firstPart = initials + "-" + section + " - " + name + ": ";
				String courseSchedules = "";
				for (ICourse icourse : course.getCourses()) {
					String schedule = icourse.getSchedule().getSchedule();
					String newLine = firstPart + schedule;
					courseSchedules = courseSchedules == "" ? newLine : courseSchedules + "\n" + newLine;
				}
				allSchedulesString = allSchedulesString == "" ? courseSchedules : allSchedulesString + courseSchedules; 
			}
		} else {
			
		}
		txASchedule.setText(allSchedulesString);
	}
}
