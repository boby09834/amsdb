package beans;

import org.bson.Document;

public class Program {

	private String subject;
	private String speciality;
	private String type;
	private String degree;
	private String semester;
	private String totalHours;

	public Program(String subject, String speciality, String type, String degree, String semester, String totalHours) {
		this.subject = subject;
		this.speciality = speciality;
		this.type = type;
		this.degree = degree;
		this.semester = semester;
		this.totalHours = totalHours;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public static Document constructDbDocument(Program subject) {
		return new Document("subject", subject.getSubject()).append("speciality", subject.getSpeciality())
				.append("type", subject.getType()).append("degree", subject.getDegree())
				.append("semester", subject.getSemester()).append("totalHours", subject.getTotalHours());
	}

}
