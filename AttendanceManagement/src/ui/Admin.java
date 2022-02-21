package ui;

import common.BaseJFrame;

public class Admin extends BaseJFrame {

	private JButton logoutjButton;
	private JButton regTeacherjButton;
	private JButton regStudentButton;
	private JButton addScheduleBtn;
	private JButton addPlanBtn;

	@Override
	protected void initComponents() {
		logoutjButton = new JButton();
		regTeacherjButton = new JButton();
		regStudentButton = new JButton();
		addScheduleBtn = new JButton();
		addPlanBtn = new JButton();

		setTitle("Admin");
		setPreferredSize(new java.awt.Dimension(600, 600));

		titleLabel.setText("Admin");
		titleLabel.setBounds(51, 0, 490, 50);

		regTeacherjButton.setText("Register/Remove Teacher");
		regTeacherjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerTeacherActionPerformed(evt);
			}
		});
		panel.add(regTeacherjButton);
		regTeacherjButton.setBounds(140, 90, 300, 50);

		regStudentButton.setText("Register/Remove Student");
		regStudentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerStudentActionPerformed(evt);
			}
		});
		panel.add(regStudentButton);
		regStudentButton.setBounds(140, 190, 300, 50);

		addScheduleBtn.setText("Add Schedule");
		addScheduleBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addScheduleActionPerformed(evt);
			}
		});
		panel.add(addScheduleBtn);
		addScheduleBtn.setBounds(140, 290, 300, 50);

		addPlanBtn.setText("Add Programs Plan");
		addPlanBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPlanActionPerformed(evt);
			}
		});
		panel.add(addPlanBtn);
		addPlanBtn.setBounds(140, 390, 300, 50);

		logoutjButton.setText("LOGOUT");

		logoutjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutActionPerformed(evt);
			}
		});
		panel.add(logoutjButton);
		logoutjButton.setBounds(210, 490, 160, 50);

	}

	private void logoutActionPerformed(java.awt.event.ActionEvent evt) {
		AdminLogin nw = new AdminLogin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void registerTeacherActionPerformed(java.awt.event.ActionEvent evt) {
		TeacherRegistration nw = new TeacherRegistration();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void registerStudentActionPerformed(java.awt.event.ActionEvent evt) {
		StudentsRegistration nw = new StudentsRegistration();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void addScheduleActionPerformed(java.awt.event.ActionEvent evt) {
		ScheduleRegistration nw = new ScheduleRegistration();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void addPlanActionPerformed(java.awt.event.ActionEvent evt) {
		ProgramsRegistration nw = new ProgramsRegistration();
		nw.setVisible(true);
		this.setVisible(false);
	}

	public static void main(String args[]) {
		new Admin().setVisible(true);
	}

}
