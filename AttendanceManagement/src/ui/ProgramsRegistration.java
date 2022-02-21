
package ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import beans.Program;
import common.BaseJFrame;
import db.DbOps;

public class ProgramsRegistration extends BaseJFrame {

	private JButton savebtn;
	private JButton uploadbtn;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private JButton backBtn;

	@Override
	protected void initComponents() {

		savebtn = new JButton();
		uploadbtn = new JButton();
		jScrollPane = new JScrollPane();
		jTable = new JTable();
		backBtn = new JButton();

		setTitle("Programs registration");
		setPreferredSize(new java.awt.Dimension(980, 650));

		titleLabel.setText("Programs registration");
		titleLabel.setBounds(210, 0, 510, 40);

		uploadbtn.setText("Upload");
		uploadbtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				uploadActionPerformed(evt, jTable);
			}
		});
		panel.add(uploadbtn);
		uploadbtn.setBounds(50, 60, 100, 30);

		savebtn.setText("Save");
		savebtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}
		});
		panel.add(savebtn);
		savebtn.setBounds(170, 60, 100, 30);

		backBtn.setText("Back");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(810, 60, 100, 30);

		String[] columns = new String[] { "Subject", "Speciality", "Type", "Degree", "Semester", "Total hours" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		jTable.setModel(tableModel);
		jScrollPane.setViewportView(jTable);
		panel.add(jScrollPane);
		jScrollPane.setBounds(0, 110, 970, 490);

	}

	private void backAction(java.awt.event.ActionEvent evt) {
		Admin nw = new Admin();
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
		if (tm.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "No program uploaded to save");
		} else {
			List<Program> program = new ArrayList<>();
			@SuppressWarnings("unchecked")
			Enumeration<Vector<String>> enumeration = tm.getDataVector().elements();
			while (enumeration.hasMoreElements()) {
				Vector<String> nextSubject = enumeration.nextElement();
				program.add(new Program(nextSubject.elementAt(0), nextSubject.elementAt(1), nextSubject.elementAt(2),
						nextSubject.elementAt(3), nextSubject.elementAt(4), nextSubject.elementAt(5)));
			}
			DbOps.saveProgram(program);
			JOptionPane.showMessageDialog(null, "Program saved successfully");
		}
	}

	public static void main(String args[]) {
		new ProgramsRegistration().setVisible(true);
	}

}
