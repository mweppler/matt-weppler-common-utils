package info.mattweppler.imageutils;

import javax.swing.ImageIcon;

public class ImageUtils
{
	/**
	 * CREATE IMAGE ICON
	 * 
	 * Returns an ImageIcon, or null if the path was invalid. 
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
	public ImageIcon createImageIcon(String path, String description)
	{
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}