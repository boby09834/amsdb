package beans;

import org.bson.Document;

public class ScheduleTimePoint {

	private String date;
	private String time;
	private String subject;
	private String speciality;
	private String type;
	private String degree;
	private String teacherName;

	public ScheduleTimePoint(String date, String time, String subject, String speciality, String type, String degree,
			String teacherName) {
		this.date = date.trim();
		this.time = time.trim();
		this.subject = subject.trim();
		this.speciality = speciality.trim();
		this.type = type.trim();
		this.degree = degree.trim();
		this.teacherName = teacherName.trim();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacher) {
		this.teacherName = teacher;
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

	public static Document constructDbDocument(ScheduleTimePoint subject) {
		return new Document("subject", subject.getSubject()).append("date", subject.getDate())
				.append("time", subject.getTime()).append("speciality", subject.getSpeciality())
				.append("type", subject.getType()).append("degree", subject.getDegree())
				.append("teacher", subject.getTeacherName());
	}

	public static ScheduleTimePoint fromDocument(Document nextSubject) {
		return new ScheduleTimePoint(nextSubject.getString("date"), nextSubject.getString("time"),
				nextSubject.getString("subject"), nextSubject.getString("speciality"), nextSubject.getString("type"),
				nextSubject.getString("degree"), nextSubject.getString("teacher"));
	}

}
