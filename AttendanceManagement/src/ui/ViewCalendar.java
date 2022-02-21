package ui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import beans.ScheduleTimePoint;
import beans.Teacher;
import common.BaseJFrame;
import db.DbOps;

public class ViewCalendar extends BaseJFrame {

	private JButton registerBtn;
	private JButton backBtn;
	private JCalendar calendar;
	private JTable jTable;

	private List<ScheduleTimePoint> schedule;

	public ViewCalendar(Teacher teacher) {
		super(teacher);
	}

	@Override
	protected void initComponents() {
		registerBtn = new JButton();
		backBtn = new JButton();
		calendar = new JCalendar();
		jTable = new JTable();

		setTitle("Calendar");
		setPreferredSize(new java.awt.Dimension(650, 650));

		titleLabel.setText("Calendar");
		titleLabel.setBounds(0, 0, 650, 50);

		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				onDateChange();

			}
		});
		JLabel label = new JLabel("Select date");
		label.setFont(new java.awt.Font("Segoe UI", 1, 24));
		label.setLayout(new BorderLayout());
		label.add(calendar, BorderLayout.EAST);
		panel.add(label);
		label.setBounds(90, 70, 380, 200);

		String[] columns = new String[] { "Time", "Subject", "Speciality", "Type", "Degree" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jTable.setModel(tableModel);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(jTable);
		listScroller.setViewportView(jTable);
		listScroller.setBounds(40, 300, 550, 150);
		panel.add(listScroller);
		onDateChange();

		registerBtn.setText("Mark");
		registerBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				markAction(evt);
			}
		});
		panel.add(registerBtn);
		registerBtn.setBounds(120, 500, 130, 40);

		backBtn.setText("Back");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
		panel.add(backBtn);
		backBtn.setBounds(360, 500, 130, 40);
	}

	private void backAction(java.awt.event.ActionEvent evt) {
		TeacherLoggedIn nw = new TeacherLoggedIn(teacher);
		nw.setVisible(true);
		this.setVisible(false);
	}

	private void onDateChange() {
		schedule = DbOps.getTeacherSchedule(calendar.getDate(), teacher);
		DefaultTableModel tm = (DefaultTableModel) jTable.getModel();
		tm.setRowCount(0);
		for (ScheduleTimePoint next : schedule) {
			tm.addRow(new String[] { next.getTime(), next.getSubject(), next.getSpeciality(), next.getType(),
					next.getDegree() });
		}
	}

	private void markAction(java.awt.event.ActionEvent evt) {
		int rowSelected = jTable.getSelectedRow();
		if (rowSelected < 0) {
			JOptionPane.showMessageDialog(null, "No subject selected");
		} else {
			MarkAttendance nw = new MarkAttendance(schedule.get(rowSelected), teacher);
			nw.setVisible(true);
			this.setVisible(false);
		}
	}

	public static void main(String args[]) {
		new ViewCalendar(new Teacher("gspasova", "Galina Spasova", "", "", "")).setVisible(true);
	}

}
