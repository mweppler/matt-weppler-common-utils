/**
 * GUIPrompt
 * Created on July 11, 2011 11:30 PM
 * @author Matthew Weppler
 */
package info.mattweppler.guiprompt;

import info.mattweppler.imageutils.ImageUtils;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GuiPrompt
{	
    /**
	 * METHOD: GENERIC STRING PROMPT
	 * 
	 * Prompts the user for a generic string (general purpose).
	 * 
	 * @param message - A string message to display to the user
	 * @param tooltip - A string tooltip to display.
	 * @param titlebar - A string displayed in the dialogs titlebar (can be null).
	 * @return string
	 */
	public static String genericStringPrompt(String message, String tooltip, String titlebar)
	{
		//Not yet implemented.
//		String userDir = System.getProperty("user.dir");
//      userDir = new StringBuilder(userDir).append(File.separatorChar).append("calicon.png").toString();
//		ImageIcon icon = new ImageUtils().createImageIcon(userDir,"Calendar");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(message);
		final JTextField jtf = new JTextField();
		jtf.setToolTipText(tooltip);
		jtf.grabFocus();
		panel.add(label, BorderLayout.NORTH);
		panel.add(jtf, BorderLayout.SOUTH);
		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.PLAIN_MESSAGE, //JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = (titlebar != null) ? 
				optionPane.createDialog(titlebar) : optionPane.createDialog(null);
		dialog.setVisible(true);
		Integer result = (Integer) optionPane.getValue();
		dialog.dispose();
		
		if (result.intValue() == JOptionPane.OK_OPTION) {
			return jtf.getText();
		} else {
			return null;
		}
	}
	
    /**
     * METHOD: OPEN FILE DIALOG
     * 
     * Choose file dialog
     * 
     * @return fileToEncrypt
     */
    public static File openFileDialog()
    { 
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setDialogTitle("Select a file:");
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fc.showDialog(new JFrame(), "Select") != JFileChooser.APPROVE_OPTION) {
        	// Will only return approve_option when user selects a valid directory.
        	return null;
        }
        return fc.getSelectedFile();
    }
    
    /**
	 * METHOD: PASSWORD PROMPT
	 * 
	 * Prompts the user for a password, password is masked.
	 * 
	 * @param message - A string message to display to the user
	 * @return password
	 */
	public static String passwordPrompt(String message) {
		String password;
		final JPasswordField jpf = new JPasswordField();
		jpf.grabFocus();
		JOptionPane jop = new JOptionPane(jpf,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = jop.createDialog(message);
		dialog.setVisible(true);
		Integer result = (Integer) jop.getValue();
		dialog.dispose();
		if (result.intValue() == JOptionPane.OK_OPTION) {
			password = new String(jpf.getPassword());
		} else {
			password = null;
		}
		return password;
	}
}
