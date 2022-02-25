package common;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import beans.ScheduleTimePoint;
import beans.Teacher;

public abstract class BaseJFrame extends JFrame {

	public static final String SQUARENESS_LOOK_AND_FEEL = "net.beeger.squareness.SquarenessLookAndFeel";
	public static final String WINDOWS_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	protected static class JButton extends javax.swing.JButton {
		public JButton() {
			super();
			setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			setFont(new java.awt.Font("Segoe UI", 0, 20));
		}
	}

	protected JLabel titleLabel;
	protected JPanel panel;
	protected Teacher teacher;
	protected ScheduleTimePoint schedule;

	public BaseJFrame(Teacher teacher, ScheduleTimePoint schedule) {
		setCommonLookAndFeel(WINDOWS_LOOK_AND_FEEL);
		this.teacher = teacher;
		this.schedule = schedule;
		initCommonComponents();
		setResizable(false);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}

	public BaseJFrame() {
		this(null);
	}

	public BaseJFrame(Teacher teacher) {
		this(teacher, null);
	}

	protected void setCommonLookAndFeel(String className) {
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void initCommonComponents() {
		panel = new JPanel(null);
		titleLabel = new JLabel();
		titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 28));
		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titleLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panel.add(titleLabel);
		initComponents();
		add(panel);
		pack();

	}

	protected abstract void initComponents();

	protected void uploadActionPerformed(java.awt.event.ActionEvent evt, JTable table) {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		filechooser.addChoosableFileFilter(filter);

		int i = filechooser.showOpenDialog(null);
		if (i == JFileChooser.APPROVE_OPTION) {
			File f = filechooser.getSelectedFile();
			Scanner fileIn;
			try {
				fileIn = new Scanner(f, StandardCharsets.UTF_8.displayName());
				DefaultTableModel tm = (DefaultTableModel) table.getModel();
				tm.setRowCount(0);
				if (fileIn.hasNextLine()) {
					verifyColumnsImported(fileIn.nextLine().split(","));
				}
				while (fileIn.hasNextLine()) {
					String line = fileIn.nextLine();
					String[] splitLine = line.split(",");
					tm.addRow(splitLine);
				}
				fileIn.close();
			} catch (FileNotFoundException fnf) {
				JOptionPane.showMessageDialog(null, "File not found and cannot be uploaded");
				fnf.printStackTrace();
			}
		}
	}

	protected void verifyColumnsImported(String[] columns) {
		// TODO Auto-generated method stub

	}

}
