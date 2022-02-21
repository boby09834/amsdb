
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
import beans.ScheduleTimePoint;
import common.BaseJFrame;
import db.DbOps;

public class ScheduleRegistration extends BaseJFrame {

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

		setTitle("Schedule Registration");
		setPreferredSize(new java.awt.Dimension(980, 650));

		titleLabel.setText("Schedule Registration");
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

		String[] columns = new String[] { "Date", "Time", "Subject", "Speciality", "Type", "Degree", "Teacher" };
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
			JOptionPane.showMessageDialog(null, "No schedule uploaded to save");
		} else {
			List<ScheduleTimePoint> schedule = new ArrayList<>();
			@SuppressWarnings("unchecked")
			Enumeration<Vector<String>> enumeration = tm.getDataVector().elements();
			while (enumeration.hasMoreElements()) {
				Vector<String> nextScheduleData = enumeration.nextElement();
				ScheduleTimePoint nextSchedule = new ScheduleTimePoint(nextScheduleData.elementAt(0),
						nextScheduleData.elementAt(1), nextScheduleData.elementAt(2), nextScheduleData.elementAt(3),
						nextScheduleData.elementAt(4), nextScheduleData.elementAt(5), nextScheduleData.elementAt(6));
				if (DbOps.teacherExistsByName(nextSchedule.getTeacherName())) {
					Program program = new Program(nextSchedule.getSubject(), nextSchedule.getSpeciality(),
							nextSchedule.getType(), nextSchedule.getDegree(), "-1", "-1");
					if (DbOps.programExists(program)) {
						schedule.add(nextSchedule);
					} else {
						JOptionPane.showMessageDialog(null,
								"Program " + nextSchedule.getSubject() + ", " + nextSchedule.getSpeciality() + ", "
										+ nextSchedule.getType() + ", " + nextSchedule.getDegree()
										+ " does not exist in the database");
						return;
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Teacher " + nextSchedule.getTeacherName() + " does not exist in the database");
					return;
				}

			}
			DbOps.saveSchedule(schedule);
			JOptionPane.showMessageDialog(null, "Schedule saved successfully");
		}
	}

	public static void main(String args[]) {
		new ScheduleRegistration().setVisible(true);
	}

}
