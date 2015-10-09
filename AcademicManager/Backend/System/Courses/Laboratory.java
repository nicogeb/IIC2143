package System.Courses;

import java.util.ArrayList;

import System.Users.Professor;
import Tools.Interfaces.ICourse;
import Tools.Interfaces.IProfessors;

public class Laboratory implements ICourse, IProfessors {
	
	private ArrayList<Professor> professors;
	private Classroom classroom;
	private Schedule schedule;	
	
	public Laboratory(ArrayList<Professor> professors, Classroom classroom, Schedule schedule) {
		this.professors = professors;
		this.classroom = classroom;
		this.schedule = schedule;
	}
		
	//IProfessors methods
	@Override
	public void addProfessor(Professor professor) {		
		this.professors.add(professor);
	}
	
	@Override
	public void removeProfessor(Professor professor) {
		this.professors.remove(professor);
	}
	
	@Override
	public ArrayList<Professor> getProfessors() {
		return this.professors;
	}

	@Override
	public Professor getProfessor(int index) {
		return this.professors.get(index);
	}
	
	// ICourse methods
	@Override
	public Classroom getClassroom() {
		return this.classroom;
	}

	@Override
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	@Override
	public Schedule getSchedule() {
		return this.schedule;
	}

	@Override
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
