package ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.BaseJFrame;
import db.DbOps;

public class AdminLogin extends BaseJFrame {

	private JTextField adminId;
	private JPasswordField adminPass;
	private JButton loginjButton;
	private JButton homejButton;
	private JLabel userjLabel;
	private JLabel passjLabel;

	@Override
	protected void initComponents() {
		userjLabel = new JLabel();
		passjLabel = new JLabel();
		adminId = new JTextField();
		adminPass = new JPasswordField();
		loginjButton = new JButton();
		homejButton = new JButton();

		setPreferredSize(new java.awt.Dimension(500, 500));
		setTitle("Admin Login");

		titleLabel.setText("Admin Login");
		titleLabel.setBounds(1, -4, 500, 60);

		userjLabel.setFont(new java.awt.Font("Segoe UI", 1, 22));
		userjLabel.setText("Username");
		panel.add(userjLabel);
		userjLabel.setBounds(45, 100, 130, 40);

		passjLabel.setFont(new java.awt.Font("Segoe UI", 1, 22));
		passjLabel.setText("Password");
		panel.add(passjLabel);
		passjLabel.setBounds(45, 210, 150, 50);

		panel.add(adminId);
		adminId.setBounds(200, 100, 250, 40);
		panel.add(adminPass);
		adminPass.setBounds(200, 220, 250, 40);

		loginjButton.setText("LOGIN");
		loginjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginActionPerformed(evt);
			}
		});
		panel.add(loginjButton);
		loginjButton.setBounds(80, 370, 150, 40);

		homejButton.setText("HOME");
		homejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				homeActionPerformed(evt);
			}
		});
		panel.add(homejButton);
		homejButton.setBounds(280, 370, 140, 40);

	}

	private void homeActionPerformed(java.awt.event.ActionEvent evt) {
		Welcome nw = new Welcome();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void loginActionPerformed(java.awt.event.ActionEvent evt) {
		String enteredUser = adminId.getText();
		String enteredPass = String.valueOf(adminPass.getPassword());

		if (DbOps.verifyAdmin(enteredUser, enteredPass)) {
			Admin nw = new Admin();
			nw.setVisible(true);
			this.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Wrong username or password!");
		}
	}

	public static void main(String args[]) {
		new AdminLogin().setVisible(true);
	}

}
