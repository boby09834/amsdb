package ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beans.Teacher;
import common.BaseJFrame;
import db.DbOps;

public class TeacherRegistration extends BaseJFrame {

	private JTextField usernameField;
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField emailField;
	private JButton registerBtn;
	private JButton removeBtn;
	private JButton backBtn;
	private JLabel userLabel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel emailLabel;
	private JLabel passLabel;
	private JPasswordField passField;

	@Override
	protected void initComponents() {

		userLabel = new JLabel();
		nameLabel = new JLabel();
		phoneLabel = new JLabel();
		emailLabel = new JLabel();
		registerBtn = new JButton();
		removeBtn = new JButton();
		backBtn = new JButton();
		usernameField = new JTextField();
		nameField = new JTextField();
		phoneField = new JTextField();
		passLabel = new JLabel();
		passField = new JPasswordField();
		emailField = new JTextField();

		setTitle("Teacher Register/Remove");
		setPreferredSize(new java.awt.Dimension(650, 650));

		titleLabel.setText("Teacher Registration or Deregistration");
		titleLabel.setBounds(0, 0, 650, 50);

		userLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		userLabel.setText("Username");
		panel.add(userLabel);
		userLabel.setBounds(90, 70, 140, 40);
		panel.add(usernameField);
		usernameField.setBounds(250, 70, 290, 40);

		passLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		passLabel.setText("Password");
		panel.add(passLabel);
		passLabel.setBounds(90, 140, 140, 40);
		panel.add(passField);
		passField.setBounds(250, 140, 290, 40);

		nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		nameLabel.setText("Name");
		panel.add(nameLabel);
		nameLabel.setBounds(90, 210, 150, 40);
		panel.add(nameField);
		nameField.setBounds(250, 210, 290, 40);

		phoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		phoneLabel.setText("Phone");
		panel.add(phoneLabel);
		phoneLabel.setBounds(90, 280, 150, 40);
		panel.add(phoneField);
		phoneField.setBounds(250, 280, 290, 40);

		emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
		emailLabel.setText("Email");
		panel.add(emailLabel);
		emailLabel.setBounds(90, 350, 150, 40);
		panel.add(emailField);
		emailField.setBounds(250, 350, 290, 40);

		registerBtn.setText("Register");
		registerBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerAction(evt);
			}
		});
		panel.add(registerBtn);
		registerBtn.setBounds(90, 500, 130, 40);

		removeBtn.setText("Remove");
		removeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeAction(evt);
			}
		});
		panel.add(removeBtn);
		removeBtn.setBounds(270, 500, 130, 40);

		backBtn.setText("Back");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(450, 500, 130, 40);

	}

	private void backAction(java.awt.event.ActionEvent evt) {
		Admin nw = new Admin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void registerAction(java.awt.event.ActionEvent evt) {
		String username = usernameField.getText();
		String pass = String.valueOf(passField.getPassword());
		String name = nameField.getText();
		String email = emailField.getText();
		String phone = phoneField.getText();
		if (username.length() == 0 || pass.length() == 0 || name.length() == 0 || email.length() == 0
				|| phone.length() == 0) {
			JOptionPane.showMessageDialog(null, "Some fields are EMPTY!");
		} else if (DbOps.teacherExists(username)) {
			JOptionPane.showMessageDialog(null, "Teacher with username '" + username + "' already exists");
		} else {
			DbOps.addTeacher(new Teacher(username, name, email, phone, pass));
			JOptionPane.showMessageDialog(null, "Teacher registration successful");
			usernameField.setText(null);
			passField.setText(null);
			nameField.setText(null);
			emailField.setText(null);
			phoneField.setText(null);
		}

	}

	private void removeAction(java.awt.event.ActionEvent evt) {
		String username = usernameField.getText();
		if (username.length() == 0) {
			JOptionPane.showMessageDialog(null, "Username field is EMPTY!");
		} else if (DbOps.teacherExists(username)) {
			DbOps.deleteTeacher(username);
			JOptionPane.showMessageDialog(null, "Teacher with username '" + username + "' sucessfully removed");
		} else {
			JOptionPane.showMessageDialog(null, "Teacher with username '" + username + "' does not exist");
		}
	}

	public static void main(String args[]) {
		new TeacherRegistration().setVisible(true);
	}

}
