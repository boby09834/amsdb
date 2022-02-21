package beans;

import org.bson.Document;

public class Student {

	private String studentId;
	private String name;
	private String email;
	private String phone;
	private String speciality;
	private String degree;
	private boolean present;

	public Student(String studentId, String name, String email, String phone, String speciality, String degree) {
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.speciality = speciality;
		this.degree = degree;
	}

	public Student(String studentId, String name, String email, String phone, String speciality, String degree,
			boolean present) {
		this(studentId, name, email, phone, speciality, degree);
		this.present = present;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", speciality=" + speciality + ", degree=" + degree + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}

	public Student(String studentId) {
		this.studentId = studentId;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public static Document constructDbDocument(Student student) {
		return new Document().append("studentId", student.getStudentId()).append("name", student.getName())
				.append("phone", student.getPhone()).append("email", student.getEmail())
				.append("degree", student.getDegree()).append("speciality", student.getSpeciality());
	}

	public static Student fromDocument(Document nextStudent) {
		return new Student(nextStudent.getString("studentId"), nextStudent.getString("name"),
				nextStudent.getString("phone"), nextStudent.getString("email"), nextStudent.getString("speciality"),
				nextStudent.getString("degree"));

	}

}
