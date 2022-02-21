
package ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import beans.ScheduleTimePoint;
import beans.Student;
import beans.Teacher;
import common.BaseJFrame;
import db.DbOps;

public class MarkAttendance extends BaseJFrame {

	private JButton savebtn;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private JButton backBtn;

	public MarkAttendance(ScheduleTimePoint schedule, Teacher teacher) {
		super(teacher, schedule);
	}

	@Override
	protected void initComponents() {
		savebtn = new JButton();
		jScrollPane = new JScrollPane();
		jTable = new JTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		backBtn = new JButton();

		setTitle("Mark attendance");
		setPreferredSize(new java.awt.Dimension(980, 650));

		titleLabel.setText("Mark attendance for " + schedule.getTime() + ", " + schedule.getSubject() + ", "
				+ schedule.getSpeciality());
		titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 24));
		titleLabel.setBounds(110, 0, 750, 40);

		savebtn.setText("Save");
		savebtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}
		});
		panel.add(savebtn);
		savebtn.setBounds(50, 60, 100, 30);

		backBtn.setText("Back");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction();
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(810, 60, 100, 30);

		String[] columns = new String[] { "Student Id", "Name", "Email", "Phone", "Speciality", "Degree", "Presence" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

		jTable.setModel(tableModel);
		jScrollPane.setViewportView(jTable);
		panel.add(jScrollPane);
		jScrollPane.setBounds(0, 110, 970, 490);
		loadStudents();
	}

	private void loadStudents() {
		List<Student> students = DbOps.getStudentsForSubject(schedule);
		DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
		for (Student next : students) {
			Object[] nextDataRow = new Object[] { next.getStudentId(), next.getName(), next.getEmail(), next.getPhone(),
					next.getSpeciality(), next.getDegree(), true };
			tm.addRow(nextDataRow);
		}
	}

	private void backAction() {
		ViewCalendar nw = new ViewCalendar(teacher);
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
		if (tm.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "No students to save");
		} else {
			List<Student> students = new ArrayList<>();
			@SuppressWarnings("unchecked")
			Enumeration<Vector<Object>> enumeration = tm.getDataVector().elements();
			while (enumeration.hasMoreElements()) {
				Vector<Object> nextStudent = enumeration.nextElement();
				students.add(new Student((String) nextStudent.elementAt(0), (String) nextStudent.elementAt(1),
						(String) nextStudent.elementAt(2), (String) nextStudent.elementAt(3),
						(String) nextStudent.elementAt(4), (String) nextStudent.elementAt(5),
						(boolean) nextStudent.elementAt(6)));
			}

			DbOps.addPresence(students, schedule);
			JOptionPane.showMessageDialog(null, "Students presence saved successfully");
			backAction();
		}
	}

	public static void main(String args[]) {
		new MarkAttendance(new ScheduleTimePoint("", "14:20", "Mathematics", "Computer science", "", "", ""),
				new Teacher("", "Galina Spasova", "", "", "")).setVisible(true);
	}
}
