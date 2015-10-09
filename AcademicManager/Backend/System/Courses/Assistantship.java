package System.Courses;

import java.util.ArrayList;

import System.Users.Assistant;
import Tools.Interfaces.ICourse;
import Tools.Interfaces.IAssistants;

/**
 * Class that represents an Assistantship that is dictated.
 */
public class Assistantship implements ICourse, IAssistants {
	
	private ArrayList<Assistant> assistants; 
	private Classroom classroom;
	private Schedule schedule;
	
	/**
	 * Creates an instance of Assistantship
	 * @param assistants The assistants of this assistantship
	 * @param classroom The classroom where this assistantship is made
	 * @param schedule The schedule in which this assistantship is made
	 */
	public Assistantship(ArrayList<Assistant> assistants, Classroom classroom, Schedule schedule) {
		this.assistants = assistants;
		this.classroom = classroom;
		this.schedule = schedule;
	}
		
	// IAssistants methods
	@Override
	public void addAssistant(Assistant assistant) {		
		this.assistants.add(assistant);
	}
	
	@Override
	public void removeAssistant(Assistant assistant) {
		this.assistants.remove(assistant);
	}
	@Override
	public ArrayList<Assistant> getAssistants() {
		return this.assistants;
	}

	@Override
	public Assistant getAssistant(int index) {
		return this.assistants.get(index);
	}

	// ICourse methods
	@Override
	public Classroom getClassroom() {
		return classroom;
	}

	@Override
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	@Override
	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
