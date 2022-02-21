package ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.BaseJFrame;
import db.DbOps;

public class TeacherLogin extends BaseJFrame {

	private JTextField usernameField;
	private JPasswordField passField;
	private JButton loginjButton;
	private JButton homejButton;
	private JLabel userjLabel;
	private JLabel passjLabel;

	@Override
	protected void initComponents() {

		userjLabel = new JLabel();
		passjLabel = new JLabel();
		usernameField = new JTextField();
		passField = new JPasswordField();
		loginjButton = new JButton();
		homejButton = new JButton();

		setTitle("Teacher Login");
		setPreferredSize(new java.awt.Dimension(550, 600));

		titleLabel.setText("Teacher Login");
		titleLabel.setBounds(1, -4, 550, 60);

		userjLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		userjLabel.setText("Username");
		panel.add(userjLabel);
		userjLabel.setBounds(50, 160, 130, 40);

		passjLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		passjLabel.setText("Password");
		panel.add(passjLabel);
		passjLabel.setBounds(50, 300, 150, 50);

		panel.add(usernameField);
		usernameField.setBounds(200, 170, 250, 40);
		panel.add(passField);
		passField.setBounds(200, 310, 250, 40);

		loginjButton.setText("LOGIN");
		loginjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginActionPerformed(evt);
			}
		});
		panel.add(loginjButton);
		loginjButton.setBounds(100, 470, 150, 40);

		homejButton.setText("HOME");
		homejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				homeActionPerformed(evt);
			}
		});
		panel.add(homejButton);
		homejButton.setBounds(300, 470, 140, 40);
	}

	private void homeActionPerformed(java.awt.event.ActionEvent evt) {
		Welcome nw = new Welcome();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void loginActionPerformed(java.awt.event.ActionEvent evt) {
		String enteredUser = usernameField.getText();
		String enteredPass = String.valueOf(passField.getPassword());

		if (DbOps.verifyTeacher(enteredUser, enteredPass)) {
			TeacherLoggedIn nw = new TeacherLoggedIn(DbOps.getTeacherByUsername(enteredUser));
			nw.setVisible(true);
			this.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Wrong username or password!");
		}
	}

	public static void main(String args[]) {
		new TeacherLogin().setVisible(true);
	}

}
