package frontend.view.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import backend.manager.Manager;
import backend.others.Const;
import backend.others.Messages;
import backend.others.Messages.UILabel;
import backend.users.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class LogInController implements Initializable, IController {
	
	@FXML
	ChoiceBox<String> chBxUsers;
	@FXML
	Button btnSignIn;
	@FXML
	Button btnRegister;
	@FXML
	Label labelSignIn;
	@FXML
	Label labelRegistration;
	@FXML
	Label labelLogIn;
	
	public void setUp(){
		btnSignIn.setText(Messages.getUILabel(UILabel.SIGN_IN));
		btnRegister.setText(Messages.getUILabel(UILabel.REGISTER));
		labelSignIn.setText(Messages.getUILabel(UILabel.SIGN_IN_AS_USER));
		labelRegistration.setText(Messages.getUILabel(UILabel.DONT_HAVE_ACCOUNT_REGISTER));
		labelLogIn.setText(Messages.getUILabel(UILabel.LOG_IN));
		
		ArrayList<String> users = new ArrayList<String>();
		
		for (Professor professor : Manager.INSTANCE.professors) {
			users.add(professor.getName() + " " + professor.getLastnameFather() + " " + professor.getLastnameMother());
		}
		
		for (Student student : Manager.INSTANCE.students) {
			users.add(student.getName() + " " + student.getLastnameFather() + " " + student.getLastnameMother());
		}
		
		for (Assistant assistant : Manager.INSTANCE.assistants) {
			users.add(assistant.getName() + " " + assistant.getLastnameFather() + " " + assistant.getLastnameMother());
		}
		
		for (Admin admin : Manager.INSTANCE.admins) {
			users.add(admin.getName() + " " + admin.getLastnameFather() + " " + admin.getLastnameMother());
		}
		
		chBxUsers.setItems(FXCollections.observableArrayList(users));
		
		
	}
	
	public void btnSignIn_Pressed(){
		URL location = getClass().getResource(Const.MAIN_MENU);
		ViewUtilities.openView(location, "Menu Principal");
	}
	
	public void btnRegister_Pressed(){
		URL location = getClass().getResource(Const.USER_REGISTRATION);
		ViewUtilities.openView(location, "Registro");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}
}
