package frontend.student;

import java.net.URL;
import java.util.ArrayList;

import backend.courses.Course;
import backend.manager.Manager;
import backend.users.Student;
import frontend.main.MViewController;
import frontend.others.Parser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * When creating a new ViewController this file allows to reduce the amount of
 * writing.
 */
public class SForumsViewController extends MViewController {

	@FXML
	ComboBox<String> cmBxCourses;
	@FXML
	ComboBox<String> cmBxForumEntry;
	@FXML
	TextArea txAForumEntryComments;
	@FXML
	TextField txFForumComment;
	@FXML
	Button btnPostComment;
	@FXML
	TextField txFNewForumEntry;
	@FXML
	Button btnPostNewForumEntry;
	
	Student user = (Student) Manager.INSTANCE.currentUser;
	public static URL view = Object.class.getResource("/frontend/student/SForumsView.fxml");
	ArrayList<Course> coursesToShow;
	
	@Override
	public void setUp() {
		super.setUp();
		
		if (user.getCurriculum().getCurrentSemester() == null) {
			super.btnBack_Pressed();
		}
		coursesToShow = user.getCurriculum().getCurrentSemester().getCourses();
		
		cmBxCourses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					showForums(Parser.getCourseForParsed(newValue, coursesToShow));
				}
			}
		});
		cmBxForumEntry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					
				}
			}
		});
	}
	
	public void showForums(Course course) {
		
	}
	
	public void btnPostComment_Pressed() {
		
	}
	
	public void btnPostNewForumEntry_Pressed() {
		
	}
}
