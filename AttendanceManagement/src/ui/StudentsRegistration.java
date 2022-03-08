
package ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import beans.Student;
import common.BaseJFrame;
import db.DbOps;

public class StudentsRegistration extends BaseJFrame {

	private JButton savebtn;
	private JButton uploadbtn;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private JTextField studentIdField;
	private JLabel studentIdLabel;
	private JButton removeBtn;
	private JButton backBtn;

	@Override
	protected void initComponents() {
		savebtn = new JButton();
		uploadbtn = new JButton();
		jScrollPane = new JScrollPane();
		jTable = new JTable();
		studentIdField = new JTextField();
		studentIdLabel = new JLabel();
		removeBtn = new JButton();
		backBtn = new JButton();

		setTitle("View Attendance");
		setPreferredSize(new java.awt.Dimension(980, 650));

		titleLabel.setText("Register Students");
		titleLabel.setBounds(210, 0, 510, 40);

		uploadbtn.setText("Upload");
		uploadbtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				uploadActionPerformed(evt, jTable);
			}
		});
		panel.add(uploadbtn);
		uploadbtn.setBounds(50, 60, 110, 30);

		savebtn.setText("Save");
		savebtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}
		});
		panel.add(savebtn);
		savebtn.setBounds(170, 60, 110, 30);

		studentIdLabel.setText("Student id");
		panel.add(studentIdLabel);
		studentIdLabel.setBounds(370, 60, 150, 30);
		panel.add(studentIdField);
		studentIdField.setBounds(440, 60, 110, 30);

		removeBtn.setText("Remove");
		removeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeActionPerformed(evt);
			}
		});
		panel.add(removeBtn);
		removeBtn.setBounds(560, 60, 110, 30);

		backBtn.setText("Back");
		backBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(810, 60, 110, 30);

		String[] columns = new String[] { "Student Id", "Name", "Email", "Phone", "Speciality", "Degree" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		jTable.setModel(tableModel);
		jScrollPane.setViewportView(jTable);
		panel.add(jScrollPane);
		jScrollPane.setBounds(0, 110, 970, 490);
		addPrinting(jTable);
	}

	private void backAction(java.awt.event.ActionEvent evt) {
		Admin nw = new Admin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
		if (tm.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "No students uploaded to save");
		} else {
			List<Student> students = new ArrayList<>();
			@SuppressWarnings("unchecked")
			Enumeration<Vector<String>> enumeration = tm.getDataVector().elements();
			while (enumeration.hasMoreElements()) {
				Vector<String> nextStudent = enumeration.nextElement();
				students.add(new Student(nextStudent.elementAt(0), nextStudent.elementAt(1), nextStudent.elementAt(2),
						nextStudent.elementAt(3), nextStudent.elementAt(4), nextStudent.elementAt(5)));
			}
			DbOps.saveStudents(students);
			JOptionPane.showMessageDialog(null, "Students saved successfully");

		}

	}

	private void removeActionPerformed(java.awt.event.ActionEvent evt) {
		if (studentIdField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Student id is empty");
		} else {
			Student studentToRemove = DbOps.getStudentById(studentIdField.getText());
			DbOps.removeStudent(studentToRemove);
			JOptionPane.showMessageDialog(null, "Student " + studentToRemove.getName() + " removed successfully");
		}
	}

	public static void main(String args[]) {
		new StudentsRegistration().setVisible(true);
	}

}
