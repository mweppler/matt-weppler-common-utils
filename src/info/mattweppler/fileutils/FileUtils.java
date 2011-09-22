/*
 * FileUtils
 * Created on May 20, 2011 21:30 PM
 * @author Matthew Weppler
 * copyright 2011 InterDev Inc.
 */
package info.mattweppler.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileUtils
{
	/**
     * METHOD: GET FILE LISTING
     * 
     * 
     * 
     * @param directory
     * @return
     * @throws FileNotFoundException
     */
    public static List<File> getFileListing(File directory) throws FileNotFoundException
    {
    	validateDirectory(directory);
    	List<File> result = getFileListingNoSort(directory);
    	Collections.sort(result);
    	return result;
    }
    
    /**
     * METHOD: GET FILE LISTING NO SORT
     * 
     * 
     * 
     * @param directory
     * @return
     * @throws FileNotFoundException
     */
    private static List<File> getFileListingNoSort(File directory) throws FileNotFoundException{
    	List<File> result = new ArrayList<File>();
    	File[] filesAndDirs = directory.listFiles();
    	List<File> filesDirs = Arrays.asList(filesAndDirs);
    	for(File file : filesDirs) {
    		result.add(file); //always add, even if directory
    		if ( ! file.isFile() ) {
    			//must be a directory, recursive call!
    			List<File> deeperList = getFileListingNoSort(file);
    			result.addAll(deeperList);
    		}
    	}
    	return result;
    }
	    
    /**
     * METHOD: READ CSV TO KEY VALUE PAIRS
     * 
     * 
     * 
     * @param csvFile
     * @param swapKeyValue
     * @return
     */
    public static HashMap<String,String> readCsvToKeyValuePairs(File csvFile, boolean swapKeyValue)
    {
    	HashMap<String,String> csvKeyValue = new HashMap<String,String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(csvFile));
            while (scanner.hasNextLine()){
                String tempStr = scanner.nextLine();
                if (swapKeyValue) {
                	String hKey = tempStr.substring((tempStr.indexOf(",") + 1),tempStr.length());
                	String hValue = tempStr.substring(0, tempStr.indexOf(","));
                	csvKeyValue.put(hKey, hValue);
                } else {
                	String hKey = tempStr.substring(0, tempStr.indexOf(","));
                	String hValue = tempStr.substring((tempStr.indexOf(",") + 1),tempStr.length());
                	csvKeyValue.put(hKey, hValue);
                }
            }
        } catch (FileNotFoundException fnfe) {
        	//fnfe.printStackTrace();
        } finally{
            scanner.close();
        }
    	return csvKeyValue;
    }
    
    /**
     * METHOD: READ FILE CONTENTS TO STRING
     * 
     * 
     * 
     * @param file
     * @return
     */
    public static String readFileContentsToString(File file)
    {
        int inChar;
        StringBuilder strBuilder = new StringBuilder();
        try {
            FileInputStream fileIns = new FileInputStream(file);
            while ((inChar = fileIns.read()) != -1)
                strBuilder.append((char) inChar);
            fileIns.close();
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        } catch (NumberFormatException nfe) { }
        return strBuilder.toString();
    }
    
    /**
     * METHOD: READ FILE CONTENTS TO STRING
     * 
     * 
     * 
     * @param filePath
     * @return
     */
    public static String readFileContentsToString(String filePath)
    {
        int inChar;
        StringBuilder strBuilder = new StringBuilder();
        try {
            FileInputStream fileIns = new FileInputStream(new File(filePath));
            while ((inChar = fileIns.read()) != -1)
                strBuilder.append((char) inChar);
            fileIns.close();
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        } catch (NumberFormatException nfe) { }
        return strBuilder.toString();
    }
    
    /**
     * METHOD: READ FILE BY LINE
     * 
     * Reads a file line by line and returns an array list of strings.
     * 
     * @param file
     * @return strings
     */
    public static ArrayList<String> readFileByLine(File file)
    {
        ArrayList<String> strings = new ArrayList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNextLine()){
                strings.add(scanner.nextLine());
            }
        } catch (FileNotFoundException fnfe) {
            // Ignore for now...
        } finally{
            scanner.close();
        }
        return strings;
    }
    
    /**
     * METHOD: RETRIEVE ARRAY LIST OF STRING ARRAYS FROM FILE
     * 
     * 
     * 
     * @param file
     * @return
     */
    public static ArrayList<String[]> retrieveArrayListOfStringArraysFromFile(File file)
    {
    	ArrayList<String[]> tokens = new ArrayList<String[]>();
        for (String string : FileUtils.readFileByLine(file)) {
            String[] result = string.split("\\,");
            tokens.add(result);
        }
        return tokens;
    }
        
    /**
     * METHOD: RETRIEVE STRING ARRAY OF FILENAMES IN DIRECTORY WITH EXTENSION
     * 
     * Retrieves filenames with extension from directory as a String array.
     * 
     * @param path as String ex: "/Users/matt"
     * @param filter as String ex: ".txt"
     * @param ignoreCase as boolean - Should the case of the extension be ignored?
     * @return String[]
     * @throws Exception "Path supplied not Directory"
     */
    public static String[] retrieveStringArrayOfFileNamesInDirectoryWithExtension(String path, final String filter, final boolean ignoreCase) throws Exception
    {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new Exception("The path you supplied is not a Directory.");
        }
        FilenameFilter theFilter = new FilenameFilter() {
            public boolean accept(File d, String f) {
                
                return (ignoreCase) ? f.toLowerCase().endsWith(filter) : f.endsWith(filter);
            }
        };
        return directory.list(theFilter);
    }
    
    /**
     * METHOD: VALIDATE DIRECTORY
     * 
     * Test if the given directory is a valid directory.
     * @param directory
     * @throws FileNotFoundException
     */
    public static void validateDirectory(File directory) throws FileNotFoundException
    {
    	if (directory == null) {
    		throw new IllegalArgumentException("Directory should not be null.");
    	}
    	if (!directory.exists()) {
    		throw new FileNotFoundException("Directory does not exist: " + directory);
    	}
    	if (!directory.isDirectory()) {
    		throw new IllegalArgumentException("Is not a directory: " + directory);
    	}
    	if (!directory.canRead()) {
    		throw new IllegalArgumentException("Directory cannot be read: " + directory);
    	}
    }
}