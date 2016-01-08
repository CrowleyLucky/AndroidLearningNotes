package com.crowley.fileincrementcopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.crowley.tools.Tools;
//TODO 1.加上处理过程中日志的实时显示；2.单击文本框的时候可以选择文件夹！
//     3.是否需要两个进度条？current file progress和globe progress---不需要了，本来就是增量备份，每次拷贝的文件不多
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int FRAME_HEIGHT = 600;
	public static final int FRAME_WIDTH = 800;
	public static final String FILE_RECORD_NAME = "FileIncrementCopy.txt";
	private JLabel sourceHint = null; 
	private JTextField sourceField = null;
	private JLabel destHint = null; 
	private JTextField destField = null;
	private JProgressBar progressBar = null;
	private JButton startCopyBtn= null;
	private JFileChooser sourceChooser = null;
	private JFileChooser destChooser = null;
	private JTextArea logArea = null;
	private JScrollPane pane = null;
	private String sourceBasePath = null;
	private String destBasePath = null;
	
	public MainFrame() {
		this.setLayout(null);
		this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLocation();
		this.setTitle("FileIncrementCopy                             " + 
				"Created by Crowley                    " + 
				"Copyright All Reserved       [2015-07-16]");
		this.addComponents();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	private void addComponents() {
		//add source folder hint
		sourceHint = new JLabel("Source Folder：");
		sourceHint.setSize(120, 30);
		sourceHint.setLocation(20, 10);
		sourceHint.setFont(new Font("Default",Font.ITALIC,15));
		this.add(sourceHint);
		
		//add source folder text field
		sourceField = new JTextField();
		sourceField.setText("Drag folder here or select a folder...");
		sourceField.setSize(500, 30);
		sourceField.setLocation(150, 10);
		sourceField.setFont(new Font("Default",Font.ITALIC,15));
		sourceField.setForeground(Color.BLACK);
		sourceField.setEditable(false);
		SourceFolderDropTarget sourceDropTarget = new SourceFolderDropTarget();
		sourceField.setDropTarget(sourceDropTarget);
		this.add(sourceField);
		
		//add a mouse click event on source folder
		sourceField.addMouseListener(new SourceMouseListener());
		
		
		//add source folder hint
		destHint = new JLabel("Dest Folder：");
		destHint.setSize(120, 30);
		destHint.setLocation(20, 50);
		destHint.setFont(new Font("Default",Font.ITALIC,15));
		this.add(destHint);
		
		//add destination folder text field
		destField = new JTextField();
		destField.setText("Drag folder here or select a folder...");
		destField.setSize(500, 30);
		destField.setLocation(150, 50);
		destField.setFont(new Font("Default",Font.ITALIC,15));
		destField.setForeground(Color.BLACK);
		destField.setEditable(false);
		DestFolderDropTarget destDropTarget = new DestFolderDropTarget();
		destField.setDropTarget(destDropTarget);
		this.add(destField);
		
		//add a mouse click event on destination folder
		destField.addMouseListener(new DestMouseListener());
		
		//add progress bar hint
		destHint = new JLabel("Progress：");
		destHint.setSize(120, 30);
		destHint.setLocation(20, 490);
		destHint.setFont(new Font("Default",Font.ITALIC,13));
		this.add(destHint);
		
		//add progress bar
		progressBar = new JProgressBar();
		progressBar.setSize(650, 15);
		progressBar.setLocation(95, 500);
		progressBar.setForeground(Color.GREEN);
		progressBar.setMaximum(100);progressBar.setValue(20);
		this.add(progressBar);
		
		//add start button
		startCopyBtn = new JButton();
		startCopyBtn.setText("Start");
		startCopyBtn.setSize(80, 30);
		startCopyBtn.setLocation(680, 50);
		startCopyBtn.addActionListener(new ButtonListener());
		this.add(startCopyBtn);
		
		//add a real-time log area
		logArea = new JTextArea();logArea.setForeground(Color.BLACK);
		logArea.setEnabled(true);
		logArea.setFont(new Font("Default",Font.ITALIC,15));
		logArea.setEditable(false);
		//logArea.setBackground(Color.RED);
		pane = new JScrollPane(logArea);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setSize(750, 350);
		pane.setLocation(20, 100);
		this.add(pane);
		
	}


	private void setLocation() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)((dimension.getWidth() - FRAME_WIDTH) / 2), (int)((dimension.getHeight() - FRAME_HEIGHT) / 2));
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!sourceField.getText().contains("\\")) {
				System.out.println("Source Folder is empty ...");
				JOptionPane.showMessageDialog(null, "Please select source folder first", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!destField.getText().contains("\\")) {
				System.out.println("Destination Folder is empty ...");
				JOptionPane.showMessageDialog(null, "Please select destination folder first", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(destField.getText().equals(sourceField.getText())) {
				System.out.println("Same folder path ...");
				JOptionPane.showMessageDialog(null, "Please select different folder", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			sourceBasePath = sourceField.getText();
			destBasePath = destField.getText();
			//Tools.searchNewFilesAndDirectorys(sourceBasePath, destBasePath, MainFrame.this);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					logArea.setText("");
					startCopyBtn.setEnabled(false);
					sourceField.setEnabled(false);
					destField.setEnabled(false);
					long[] results = Tools.searchNewFilesAndDirectorys(sourceBasePath, destBasePath, MainFrame.this);
					pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMaximum());
					
					int returnVal = JOptionPane.showOptionDialog(MainFrame.this, 
							"Are you sure you want to copy these files above?\n" + 
									" Files:" + results[0] + ",   Directories:" + results[1] + ",   Size:" + + (results[2] / 1024 / 1024) + "MB", 
							"Prompt", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, 
							null, 
							new String[]{"YES", "NO"}, 
							0);
					if(returnVal == 0) {
						Tools.startIncrementCopy(destBasePath, results[2], progressBar);
					} else {
						logArea.setText("");
					}
					startCopyBtn.setEnabled(true);
					sourceField.setEnabled(true);
					destField.setEnabled(true);
				}
			}).start();
			
		}
		
	}
	
	public void appendLog(String log) {
		logArea.append(log + "\n");
		pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMaximum());
	//	logArea.update(logArea.getGraphics());
		//logArea.updateUI();
		//logArea.setAutoscrolls(true);
		
	}
	
	private class DestMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			destChooser = new JFileChooser();
			destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = destChooser.showDialog(MainFrame.this, null);
			if(JFileChooser.APPROVE_OPTION == returnVal) {
				destField.setText(destChooser.getSelectedFile().getAbsolutePath());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	private class SourceMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			sourceChooser = new JFileChooser();
			sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = sourceChooser.showDialog(MainFrame.this, null);
			if(JFileChooser.APPROVE_OPTION == returnVal) {
				sourceField.setText(sourceChooser.getSelectedFile().getAbsolutePath());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	private class DestFolderDropTarget extends DropTarget {

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public synchronized void drop(DropTargetDropEvent event) {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			try {
				List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				if(files.size() != 1) {
					destField.setText("Only support one folder drag");
					destField.setForeground(Color.RED);
					return;
				}
				if(files.get(0).isFile()) {
					destField.setText("Please drag a folder insead of file");
					destField.setForeground(Color.RED);
					return;
				}
				destField.setText(files.get(0).getAbsolutePath());
				destField.setForeground(Color.BLACK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private class SourceFolderDropTarget extends DropTarget {

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public synchronized void drop(DropTargetDropEvent event) {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			try {
				List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				if(files.size() != 1) {
					sourceField.setText("Only support one folder drag");
					sourceField.setForeground(Color.RED);
					return;
				}
				if(files.get(0).isFile()) {
					sourceField.setText("Please drag a folder insead of file");
					sourceField.setForeground(Color.RED);
					return;
				}
				sourceField.setText(files.get(0).getAbsolutePath());
				sourceField.setForeground(Color.BLACK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
