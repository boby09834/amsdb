package db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import beans.Presence;
import beans.Program;
import beans.ScheduleTimePoint;
import beans.Student;
import beans.Teacher;
import common.Constants;

public class DbOps implements Constants {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");

	public static boolean verifyAdmin(String enteredUser, String enteredPass) {
		return Db.getDatabase().getCollection(ADMINS)
				.find(new Document().append(USERNAME, enteredUser).append("pass", enteredPass)).iterator().hasNext();
	}

	public static boolean verifyTeacher(String enteredUser, String enteredPass) {
		return Db.getDatabase().getCollection("teachers")
				.find(new Document().append("username", enteredUser).append("password", enteredPass)).iterator()
				.hasNext();
	}

	public static void initizlizeDb() {
		MongoDatabase db = Db.getDatabase();
		if (!db.getCollection("admins").find(new Document("username", "admin")).iterator().hasNext()) {
			db.getCollection("admins").insertOne(new Document().append("username", "admin").append("pass", "admin"));
		}
	}

	public static void addSubject(String title, int numberHours) {
		Db.getDatabase().getCollection("subjects")
				.insertOne(new Document().append("title", title).append("hours", numberHours));
	}

	public static List<String> getAllSubjects() {
		List<String> result = new ArrayList<>();
		MongoCursor<Document> iter = Db.getDatabase().getCollection("subjects").find().cursor();
		while (iter.hasNext()) {
			result.add(iter.next().getString("title"));
		}
		Collections.sort(result);
		return result;
	}

	public static void addTeacher(Teacher teacher) {
		MongoDatabase db = Db.getDatabase();
		if (!db.getCollection("teachers").find(new Document("username", teacher.getUsername())).iterator().hasNext()) {
			db.getCollection("teachers").insertOne(Teacher.constructDbDocument(teacher));
		}
	}

	public static boolean teacherExists(String username) {
		return Db.getDatabase().getCollection("teachers").find(new Document("username", username)).iterator().hasNext();
	}

	public static boolean teacherExistsByName(String name) {
		return Db.getDatabase().getCollection("teachers").find(new Document("name", name)).iterator().hasNext();
	}

	public static void deleteTeacher(String username) {
		Db.getDatabase().getCollection("teachers").deleteOne(new Document("username", username));

	}

	public static void saveStudents(List<Student> students) {
		MongoDatabase db = Db.getDatabase();
		for (Student nextStudent : students) {
			if (!db.getCollection("students").find(new Document("studentId", nextStudent.getStudentId())).iterator()
					.hasNext()) {
				db.getCollection("students").insertOne(Student.constructDbDocument(nextStudent));
			}
		}

	}

	public static void main(String[] args) {
		searchStudentsPresence(new Teacher("", "Galina Spasova", "", "", ""), "Simona").forEach(next -> {
			System.out.println(next);
		});
	}

	public static List<Presence> searchStudentsPresence(Teacher teacher, String textToSearch) {
		MongoDatabase db = Db.getDatabase();
		List<Presence> result = new ArrayList<>();

		// get all students that match the search keyword
		Set<Student> students = new HashSet<>();
		students.addAll(searchStudentsByField(textToSearch, "studentId"));
		students.addAll(searchStudentsByField(textToSearch, "name"));
		// students.addAll(searchStudentsByField(textToSearch, "phone"));
		students.addAll(searchStudentsByField(textToSearch, "email"));

		for (Student nextStudent : students) {
			// find in presence all that have the student and the teacher
			FindIterable<Document> cursor = db.getCollection("presence").find(new Document("teacher", teacher.getName())
					.append(nextStudent.getStudentId(), new BasicDBObject("$exists", true)));
			MongoCursor<Document> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				Document nextPresence = iterator.next();

				int totalHours = Integer.parseInt(db.getCollection("program")
						.find(new Document("subject", nextPresence.getString("subject"))
								.append("speciality", nextPresence.getString("speciality"))
								.append("type", nextPresence.getString("type"))
								.append("degree", nextPresence.getString("degree")))
						.iterator().next().getString("totalHours"));

				// construct new presence object or already found student
				Presence studentPresence = new Presence(nextStudent.getStudentId(), nextStudent.getName(),
						nextPresence.getString("subject"), nextPresence.getString("speciality"),
						nextPresence.getString("type"), nextPresence.getString("degree"),
						Boolean.valueOf(((Document) nextPresence.get(nextStudent.getStudentId())).getString("present"))
								? 1
								: 0,
						totalHours);
				// check if this student is already added in the collection and update it if so
				Presence toUpdatePresence = result.stream().filter(next -> next.equals(studentPresence)).findFirst()
						.orElse(null);
				if (toUpdatePresence != null) {
					toUpdatePresence
							.setPresentHours(studentPresence.getPresentHours() + toUpdatePresence.getPresentHours());
				} else {
					result.add(studentPresence);
				}
			}
		}

		return result;
	}

	private static Set<Student> searchStudentsByField(String textToSearch, String fieldName) {
		Set<Student> result = new HashSet<>();
		MongoDatabase db = Db.getDatabase();

		BasicDBObject regexQuery = new BasicDBObject();
		BasicDBObject regex = new BasicDBObject("$regex", ".?" + textToSearch + ".?");
		regexQuery.put(fieldName, regex);

		FindIterable<Document> cursor = db.getCollection("students").find(regexQuery);
		MongoCursor<Document> iterator = cursor.iterator();
		while (iterator.hasNext()) {
			result.add(Student.fromDocument(iterator.next()));
		}
		return result;
	}

	public static void removeStudent(Student student) {
		Db.getDatabase().getCollection("students").deleteOne(new Document("studentId", student.getStudentId()));
	}

	public static Teacher getTeacherByUsername(String enteredUser) {
		Document teacherD = Db.getDatabase().getCollection("teachers").find(new Document("username", enteredUser))
				.iterator().next();
		return new Teacher(enteredUser, teacherD.getString("name"), teacherD.getString("email"),
				teacherD.getString("phone"), teacherD.getString("password"));
	}

	public static void saveProgram(List<Program> program) {
		MongoDatabase db = Db.getDatabase();
		for (Program nextSubject : program) {
			if (!db.getCollection("program")
					.find(new Document("subject", nextSubject.getSubject()).append("degree", nextSubject.getDegree())
							.append("speciality", nextSubject.getSpeciality()).append("type", nextSubject.getType()))
					.iterator().hasNext()) {
				db.getCollection("program").insertOne(Program.constructDbDocument(nextSubject));
			}
		}
	}

	public static void saveSchedule(List<ScheduleTimePoint> schedule) {
		MongoDatabase db = Db.getDatabase();
		for (ScheduleTimePoint nextTime : schedule) {
			if (!db.getCollection("schedule")
					.find(new Document("time", nextTime.getTime()).append("date", nextTime.getDate())
							.append("speciality", nextTime.getSpeciality()).append("subject", nextTime.getSubject()))
					.iterator().hasNext()) {
				db.getCollection("schedule").insertOne(ScheduleTimePoint.constructDbDocument(nextTime));
			}
		}

	}

	public static List<ScheduleTimePoint> getTeacherSchedule(Date date, Teacher teacher) {
		List<ScheduleTimePoint> result = new ArrayList<>();
		FindIterable<Document> teacherSchedule = Db.getDatabase().getCollection("schedule")
				.find(new Document("date", SDF.format(date)).append("teacher", teacher.getName()));
		MongoCursor<Document> cursor = teacherSchedule.iterator();
		while (cursor.hasNext()) {
			Document nextSubject = cursor.next();
			result.add(ScheduleTimePoint.fromDocument(nextSubject));
		}
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		result.sort(new Comparator<ScheduleTimePoint>() {
			@Override
			public int compare(ScheduleTimePoint o1, ScheduleTimePoint o2) {
				try {
					return dateFormat.parse(o1.getTime()).compareTo(dateFormat.parse(o2.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		return result;
	}

	public static List<Student> getStudentsForSubject(ScheduleTimePoint schedule) {
		List<Student> result = new ArrayList<>();
		FindIterable<Document> studentsIterable = Db.getDatabase().getCollection("students")
				.find(new Document("speciality", schedule.getSpeciality()).append("degree", schedule.getDegree()));
		MongoCursor<Document> cursor = studentsIterable.iterator();
		while (cursor.hasNext()) {
			Document nextStudent = cursor.next();
			result.add(Student.fromDocument(nextStudent));
		}
		return result;
	}

	public static void addPresence(List<Student> students, ScheduleTimePoint schedule) {
		MongoDatabase db = Db.getDatabase();
		Document studentsD = ScheduleTimePoint.constructDbDocument(schedule);
		db.getCollection("presence").deleteOne(studentsD);

		students.stream().forEach(next -> {
			studentsD.append(next.getStudentId(),
					Student.constructDbDocument(next).append("present", Boolean.toString(next.isPresent())));
		});
		db.getCollection("presence").insertOne(studentsD);
	}

	public static boolean programExists(Program program) {
		return Db.getDatabase().getCollection("program")
				.find(new Document("subject", program.getSubject()).append("speciality", program.getSpeciality())
						.append("type", program.getType()).append("degree", program.getDegree()))
				.iterator().hasNext();
	}

}
