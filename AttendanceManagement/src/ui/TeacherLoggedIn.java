package ui;

import beans.Teacher;
import common.BaseJFrame;

public class TeacherLoggedIn extends BaseJFrame {

	private JButton logoutjButton;
	private JButton markAttendanceBtn;
	private JButton checkAttendanceBtn;

	public TeacherLoggedIn(Teacher teacher) {
		super(teacher);
	}

	@Override
	protected void initComponents() {
		logoutjButton = new JButton();
		markAttendanceBtn = new JButton();
		checkAttendanceBtn = new JButton();

		setTitle("Teacher module");
		setPreferredSize(new java.awt.Dimension(640, 400));

		titleLabel.setText("Welcome " + teacher.getName());
		titleLabel.setBounds(51, 0, 500, 50);

		markAttendanceBtn.setText("Mark attendance");
		markAttendanceBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				markAttendanceAction(evt);
			}
		});
		panel.add(markAttendanceBtn);
		markAttendanceBtn.setBounds(140, 90, 300, 50);

		checkAttendanceBtn.setText("Check attendance");
		checkAttendanceBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchStudent(evt);
			}
		});
		panel.add(checkAttendanceBtn);
		checkAttendanceBtn.setBounds(140, 190, 300, 50);

		logoutjButton.setText("LOGOUT");
		logoutjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutActionPerformed(evt);
			}
		});
		panel.add(logoutjButton);
		logoutjButton.setBounds(200, 290, 160, 50);

	}

	private void logoutActionPerformed(java.awt.event.ActionEvent evt) {
		TeacherLogin nw = new TeacherLogin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void markAttendanceAction(java.awt.event.ActionEvent evt) {
		ViewCalendar nw = new ViewCalendar(teacher);
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void searchStudent(java.awt.event.ActionEvent evt) {
		CheckAttendance nw = new CheckAttendance(teacher);
		nw.setVisible(true);
		this.setVisible(false);
	}

	public static void main(String args[]) {
		new TeacherLoggedIn(new Teacher("gspasova", "Galina Spasova", "test", "test", "test")).setVisible(true);
	}
}
