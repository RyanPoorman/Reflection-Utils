package poorman.object.utils.tests.resources;

public class Student extends Human {
	private String firstName;
	private String lastName;
	private int studentId;
	private String grade;

	public Student(String fName, String lName, int id, String grade) {
		setFirstName(fName);
		setLastName(lName);
		setStudentId(id);
		setGrade(grade);
	}

	public Student() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
