package ui;

import common.BaseJFrame;
import db.DbOps;

public class Welcome extends BaseJFrame {

	private JButton adminJbutton;
	private JButton teacherJButton;

	private static boolean dbInitizlized;

	public Welcome() {
		if (!dbInitizlized) {
			DbOps.initizlizeDb();
			dbInitizlized = true;
		}
	}

	@Override
	protected void initComponents() {
		adminJbutton = new JButton();
		teacherJButton = new JButton();

		setTitle("Attendance Management");
		setPreferredSize(new java.awt.Dimension(600, 400));

		titleLabel.setText("Welcome to Attendance Management System");
		titleLabel.setBounds(0, 0, 600, 60);

		adminJbutton.setText("Admin Login");
		adminJbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				adminActionPerformed(evt);
			}
		});
		panel.add(adminJbutton);
		adminJbutton.setBounds(140, 100, 290, 50);

		teacherJButton.setText("Teacher Login");
		teacherJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				teacherActionPerformed(evt);
			}
		});
		panel.add(teacherJButton);
		teacherJButton.setBounds(140, 200, 290, 50);
	}

	private void adminActionPerformed(java.awt.event.ActionEvent evt) {
		AdminLogin nw = new AdminLogin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void teacherActionPerformed(java.awt.event.ActionEvent evt) {
		TeacherLogin nw = new TeacherLogin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	public static void main(String args[]) {
		new Welcome().setVisible(true);
	}
}
