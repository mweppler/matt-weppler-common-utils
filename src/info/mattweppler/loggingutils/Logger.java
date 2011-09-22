package info.mattweppler.loggingutils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger
{
	private JFrame frame;
	private Container content;
	private JTextArea textArea;
	
	public Logger()
	{		
		createLogWindow();
	}
	
	public static void createLoggerWithMessage(String message)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,550);
		Container content = frame.getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());
        content.setPreferredSize(new Dimension(550, 550));
        JTextArea textArea = new JTextArea(32,45);
        textArea.setEditable(false);
		if (message.equals("")) {
			message = "Nothing to display.";
		}
		//System.out.println(log);
		textArea.append(message);
		textArea.setCaretPosition(textArea.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(textArea);
        content.add(scrollPane, BorderLayout.CENTER);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        frame.setLocation(x,y);
        frame.setVisible(true);
	}
	
	private void createLogWindow()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,550);
        content = frame.getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());
        content.setPreferredSize(new Dimension(550, 550));
        textArea = new JTextArea(32,45);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
		content.add(scrollPane, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        frame.setLocation(x,y);
        frame.setVisible(true);
	}
	
	public void displayLog(String message)
	{
		if (message.equals("")) {
			message = "Nothing to display.";
		}
		//System.out.println(log);
		textArea.append(message);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
