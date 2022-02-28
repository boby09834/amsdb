
package ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import beans.Presence;
import beans.Teacher;
import common.BaseJFrame;
import db.DbOps;

public class CheckAttendance extends BaseJFrame {

	private JScrollPane jScrollPane;
	private JTable jTable;
	private JTextField studentIdField;
	private JButton removeBtn;
	private JButton backBtn;

	public CheckAttendance(Teacher teacher) {
		super(teacher);
	}

	@Override
	protected void initComponents() {

		jScrollPane = new JScrollPane();
		jTable = new JTable();
		studentIdField = new JTextField();
		removeBtn = new JButton();
		backBtn = new JButton();

		setTitle("View Attendance");
		setPreferredSize(new java.awt.Dimension(980, 650));

		titleLabel.setText("View Attendance");
		titleLabel.setBounds(210, 0, 510, 40);

		panel.add(studentIdField);
		studentIdField.setBounds(270, 60, 200, 30);

		removeBtn.setText("Search");
		removeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchAction(evt);
			}
		});
		panel.add(removeBtn);
		removeBtn.setBounds(490, 60, 100, 30);

		backBtn.setText("Back");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(810, 60, 100, 30);

		String[] columns = new String[] { "Student Id", "Name", "Subject", "Speciality", "Type", "Degree",
				"Present hours", "Total hours" };
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

	private void searchAction(java.awt.event.ActionEvent evt) {
		if (studentIdField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Search box is empty");
		} else {
			List<Presence> foundStudents = DbOps.searchStudentsPresence(teacher, studentIdField.getText());
			DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
			tm.setRowCount(0);
			for (Presence next : foundStudents) {
				tm.addRow(new Object[] { next.getStudentId(), next.getName(), next.getSubject(), next.getSpeciality(),
						next.getType(), next.getDegree(), next.getPresentHours(), next.getTotalHours() });
			}

		}
	}

	public static void main(String args[]) {
		new CheckAttendance(new Teacher("", "Galina Spasova", "", "", "")).setVisible(true);
	}

}
