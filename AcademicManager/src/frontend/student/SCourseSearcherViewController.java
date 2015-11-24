package frontend.student;

import java.net.URL;
import java.util.ArrayList;

import backend.courses.Assistantship;
import backend.courses.Course;
import backend.courses.Laboratory;
import backend.courses.Lecture;
import backend.courses.Schedule.DayModuleTuple;
import backend.enums.AcademicSemester;
import backend.interfaces.ICourse;
import backend.others.Messages;
import backend.others.Messages.UILabel;
import backend.users.Assistant;
import backend.users.Professor;
import frontend.main.MCourseSearcherSelectorViewController;
import frontend.others.ViewUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SCourseSearcherViewController extends MCourseSearcherSelectorViewController {

	@FXML
	Button btnAddCourse;
	@FXML
	Button btnRemoveCourse;
	@FXML
	Button btnSaveSemester;
	@FXML
	Button btnCleanCourses;
	@FXML
	Label labelSearchCourses;
	@FXML
	Label labelSemester;
	@FXML
	Label labelSchedule;
	@FXML
	Label labelCourseInfo;
	@FXML
	Label labelStatusBar;
	@FXML
	ListView<String> listDetails;
	@FXML
	ListView<String> listCoursesInSemester;
	@FXML
	ComboBox<String> chSelectSemester;
	@FXML
	GridPane gridSchedule;

	public static URL view = Object.class.getResource("/frontend/student/SCourseSearcherView.fxml");
	public ArrayList<Course> courses = new ArrayList<Course>();
	public Label[][] schedule = new Label[10][7];

	@Override
	public void setUp() {
		super.setUp();
		
		chSelectSemester.setCursor(Cursor.HAND);
		chSelectSemester.setItems(FXCollections.observableArrayList("1","2"));
		chSelectSemester.getSelectionModel().selectFirst();
		updateCoursesShow(AcademicSemester.getAcademicSemester(chSelectSemester.getSelectionModel().getSelectedItem()));
	
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				schedule[i][j] = new Label("");
				schedule[i][j].setFont(new Font("Arial", 12));;
			}
		}
		
		chBxSelectedCourse.setOnAction((event) -> {
			String rawCourseInfo = chBxSelectedCourse.getSelectionModel().getSelectedItem();
			String[] parsed = getParsedInitialsSectionName(rawCourseInfo);
			String initials = parsed[0];
			int section = Integer.valueOf(parsed[1]);
			String name = parsed[2];
			
			ArrayList<String> details = new ArrayList<String>();			
			for (Course course : coursesToShow) {
				if (course.getInitials().equals(initials) && course.getSection() == section && course.getName().equals(name)) {										
					details.add(course.getInitials());			
					if (course.getLecture() != null) {						
						details.add(Messages.getUILabel(UILabel.LECTURE));
						Lecture lecture = course.getLecture();																
						String teachers = Messages.getUILabel(UILabel.PROFESSOR) + ": ";
						if (lecture.getProfessors().size() > 1) {
							details.add(teachers);
							for(Professor p : lecture.getProfessors()) {
								details.add(p.getName() + " " + p.getLastnameFather());
							}
						}
						else {
							Professor p = lecture.getProfessors().get(0);
							teachers += p.getName() + " " + p.getLastnameFather();
							details.add(teachers);
						}	
						String schedule = "Horario: ";
						for(DayModuleTuple t : lecture.getSchedule().getModules()) {
							schedule += (t.day.getDayString() + " " + t.module.getInt() + " ");
						}
						details.add(schedule);				
						details.add("Sala: " + lecture.getClassroom().getInitials());
						details.add("Cupos: " + lecture.getClassroom().getSize());						
						
					} if (course.getAssistantship() != null) {
						details.add(" ");
						details.add(Messages.getUILabel(UILabel.ASSISTANTSHIP));
						
						Assistantship assistantship = course.getAssistantship();
						String assistants = Messages.getUILabel(UILabel.ASSISTANT) + ": ";
						if (assistantship.getAssistants().size() > 1) {
							details.add(assistants);
							for(Assistant p : assistantship.getAssistants()) {
								details.add(p.getName() + " " + p.getLastnameFather());
							}
						}
						else {
							Assistant p = assistantship.getAssistants().get(0);
							assistants += p.getName() + " " + p.getLastnameFather();
							details.add(assistants);
						}	
						String schedule = "Horario: ";
						for(DayModuleTuple t : assistantship.getSchedule().getModules()) {
							schedule += (t.day.getDayString() + " " + t.module.getInt() + " ");
						}
						details.add(schedule);				
						details.add("Sala: " + assistantship.getClassroom().getInitials());
					} if (course.getLaboratory() != null) {
						details.add(Messages.getUILabel(UILabel.LABORATORY));
						
						Laboratory laboratory = course.getLaboratory();
						String teachers = Messages.getUILabel(UILabel.PROFESSOR) + ": ";
						if (laboratory.getProfessors().size() > 1) {
							details.add(teachers);
							for(Professor p : laboratory.getProfessors()) {
								details.add(p.getName() + " " + p.getLastnameFather());
							}
						}
						else {
							Professor p = laboratory.getProfessors().get(0);
							teachers += p.getName() + " " + p.getLastnameFather();
							details.add(teachers);
						}	
						String schedule = "Horario: ";
						for(DayModuleTuple t : laboratory.getSchedule().getModules()) {
							schedule += (t.day.getDayString() + " " + t.module.getInt() + " ");
						}
						details.add(schedule);				
						details.add("Sala: " + laboratory.getClassroom().getInitials());
						details.add("Cupos: " + laboratory.getClassroom().getSize());
					}
					break;
				}
			}		
			listDetails.setItems(FXCollections.observableArrayList(details));
			labelStatusBar.setText("");
		});
		
		chSelectSemester.setOnAction((event) -> {
			updateCoursesShow(AcademicSemester.getAcademicSemester(chSelectSemester.getSelectionModel().getSelectedItem()));
		});
			
		schedule[1][0].setText("08:30");
		schedule[2][0].setText("10:00");
		schedule[3][0].setText("11:30");
		schedule[5][0].setText("14:00");
		schedule[6][0].setText("15:30");
		schedule[7][0].setText("17:00");
		schedule[8][0].setText("18:30");
		schedule[9][0].setText("20:00");
		schedule[0][1].setText("L");
		schedule[0][2].setText("M");
		schedule[0][3].setText("W");
		schedule[0][4].setText("J");
		schedule[0][5].setText("V");
		schedule[0][6].setText("S");
			
		for (int i = 0; i < 10; i++) {
			schedule[i][0].setStyle("-fx-background-color: #ff8;");
			schedule[i][0].setText(ViewUtilities.reSize(schedule[i][0].getText(), 7));
		}
		for (int j = 0; j < 7; j++) {
			schedule[0][j].setStyle("-fx-background-color: #ff8;");
			schedule[0][j].setText(ViewUtilities.reSize(schedule[0][j].getText(), 15));
		}	
		refresh();
	}
	
	@Override
	public void updateCoursesShow(AcademicSemester semester) {
		ArrayList<String> coursesStrings = new ArrayList<String>();
		for (Course course : coursesToShow) {
			if (course.getSemester() == semester || course.getSemester() == AcademicSemester.BOTH
					|| semester == AcademicSemester.BOTH) {
				coursesStrings.add(getParsedCourse(course.getInitials(), course.getSection(), course.getName()));
			}
		}
		chBxSelectedCourse.setItems(FXCollections.observableArrayList(coursesStrings));
		ViewUtilities.autoComplete(chBxSelectedCourse);
	}

	public void btnAddCourse_Pressed() {
		if (!chBxSelectedCourse.getSelectionModel().isEmpty()
				& chBxSelectedCourse.getItems().contains(chBxSelectedCourse.getSelectionModel().getSelectedItem())) {
			
			String rawCourseInfo = chBxSelectedCourse.getSelectionModel().getSelectedItem();
			String[] parsed = getParsedInitialsSectionName(rawCourseInfo);
			String initials = parsed[0];
			int section = Integer.valueOf(parsed[1]);
			String name = parsed[2];			
			
			for (Course course : coursesToShow) {
				if (course.getInitials().equals(initials) && course.getSection() == section && course.getName().equals(name)) {									
					if(course.getCourses() != null) {
						for (ICourse i : course.getCourses()) {						
							ArrayList<DayModuleTuple> t = i.getSchedule().getModules();
							ArrayList<DayModuleTuple> s;											
							for (Course c : courses) {						
								s = c.getLecture().getSchedule().getModules();
								for (DayModuleTuple sm : s) {
									for (DayModuleTuple tm : t) {
										if (sm.day.equals(tm.day) && sm.module.equals(tm.module)) {
											labelStatusBar.setText("Se ha detectado tope de horario con " + c.getName());
											return;
										}
									}
								}
							}	
						}
					}					
					
					ObservableList<String> currentCourses = listCoursesInSemester.getItems();
					String parsedCourse = getParsedCourse(initials, section, name);				
					
					if(!currentCourses.contains(parsedCourse)) {
						currentCourses.add(parsedCourse);
						listCoursesInSemester.setItems(FXCollections.observableArrayList(currentCourses));
						
						if (course.getLecture() != null) {
							ArrayList<DayModuleTuple> tc = course.getLecture().getSchedule().getModules();
							for (DayModuleTuple tm : tc) {
								int day = tm.day.getInt();
								int mod = tm.module.getInt();
								if (mod > 3) {
									mod++;
								}
								schedule[mod][day].setStyle("-fx-text-fill: #1ab;");
								schedule[mod][day].setText(ViewUtilities.reSize(course.getInitials(), 10));
							}
						} 
						if (course.getAssistantship() != null) {
							ArrayList<DayModuleTuple> ta = course.getAssistantship().getSchedule().getModules();
							for (DayModuleTuple tm : ta) {
								int day = tm.day.getInt();
								int mod = tm.module.getInt();
								if (mod > 3) {
									mod++;
								}
								schedule[mod][day].setStyle("-fx-text-fill: #f70;");
								schedule[mod][day].setText(ViewUtilities.reSize(course.getInitials(),10));
							}
						} 
						if (course.getLaboratory() != null) {
							ArrayList<DayModuleTuple> tl = course.getLaboratory().getSchedule().getModules();
							for (DayModuleTuple tm : tl) {
								int day = tm.day.getInt();
								int mod = tm.module.getInt();
								if (mod > 3) {
									mod++;
								}
								schedule[mod][day].setStyle("-fx-text-fill: #060;");
								schedule[mod][day].setText(ViewUtilities.reSize(course.getInitials(),10));
							}
						}
						refresh();
						courses.add(course);
						break;
					}
				}
			}
		} else {
			// TODO Uncomment when function is created
			// ViewUtilities.showAlert(Messages.getUILabel(UILabel.ERROR_SELECTION)
			// + "(" + Messages.getUILabel(UILabel.ADD_COURSE) + ")");
		}
	}

	public void btnRemoveCourse_Pressed() {
		if (!listCoursesInSemester.getSelectionModel().isEmpty()) {
			
			String rawCourseInfo = listCoursesInSemester.getSelectionModel().getSelectedItem();
			String[] parsed = getParsedInitialsSectionName(rawCourseInfo);
			String initials = parsed[0];
			int section = Integer.valueOf(parsed[1]);
			String name = parsed[2];
			
			for (Course course : coursesToShow) {
				if (course.getInitials().equals(initials) && course.getSection() == section
						&& course.getName().equals(name)) {					
					
					ObservableList<String> currentCourses = listCoursesInSemester.getItems();
					currentCourses.remove(getParsedCourse(initials, section, name));
					listCoursesInSemester.setItems(FXCollections.observableArrayList(currentCourses));
					
					for (ICourse i : course.getCourses()) {
						ArrayList<DayModuleTuple> t = i.getSchedule().getModules();
						for (DayModuleTuple tm : t) {
							int day = tm.day.getInt();
							int mod = tm.module.getInt();
							if (mod > 3) {
								mod++;
							}				
							schedule[mod][day].setText("");
							refresh();
						}					
					}		
					courses.remove(course);				
					break;
				}
			}
		} else {
			// TODO Uncomment when function is created
			// ViewUtilities.showAlert(Messages.getUILabel(UILabel.ERROR_SELECTION)
			// + "(" + Messages.getUILabel(UILabel.REMOVE_COURSE) + ")");
		}
	}

	public void btnSaveSemester_Pressed() {
		
	}

	public void btnCleanCourses_Pressed() {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 7; j++) {
				schedule[i][j].setStyle("-fx-background-color: white;");
				schedule[i][j].setText("");
			}
		}
		refresh();
		listCoursesInSemester.setItems(FXCollections.observableArrayList(""));
		courses.clear();
	}
	
	@SuppressWarnings("static-access")
	private void refresh() {
		Node node = gridSchedule.getChildren().get(0);
		gridSchedule.getChildren().clear();
		gridSchedule.getChildren().add(0,node);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {				
				if(!schedule[i][j].getText().trim().equals(""))
				{	
					gridSchedule.setHalignment(schedule[i][j], HPos.CENTER);
					gridSchedule.add(schedule[i][j], j, i);
				}
			}
		}
	}	
}