/**
 * DatabaseUtils
 * Created on Sept 28, 2009 11:33 AM
 * Modified on May 20, 2011 21:07 PM
 * @author Matthew Weppler
 * copyright 2011 InterDev Inc.
 */
package info.mattweppler.databaseutils;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.mattweppler.fileutils.FileUtils;

public class DatabaseUtils
{
    private Connection connection;
    private HashMap<String, String> databaseSettings = new HashMap<String, String>();

    /**
     * CONSTRUCTOR: DATABASE UTILS
     */
    public DatabaseUtils()
    {
        String userDir = System.getProperty("user.dir");
        userDir = new StringBuilder(userDir).append(File.separatorChar).append("databaseConf.json").toString();
       	//System.out.println(userDir.toString());
        File confFile = new File(userDir);
        if (confFile.exists()) {
	        Gson gson = new Gson();
	 		Type collectionType = new TypeToken<HashMap<String, String>>(){}.getType();
	 		databaseSettings = gson.fromJson(FileUtils.readFileContentsToString(confFile), collectionType);
	 		//printConfigValues();
	 		setConnection();
        } else {
        	System.err.println("Database Configuration File Not Found."+"\nFile: "+userDir.toString());
        }
    }

    public Connection getConnection()
    {
        return this.connection;
    }

    public void setConnection()
    {
        try {
            String connectString = new String("jdbc:mysql://" + databaseSettings.get("host") + "/" + databaseSettings.get("database"));
            connection = DriverManager.getConnection(connectString, databaseSettings.get("username"), databaseSettings.get("password"));
        } catch (SQLException sqle) {        	
        	System.err.println("Unable to connect to database with the following settings:");
        	System.err.println("jdbc:mysql://" + databaseSettings.get("host") + "/" + databaseSettings.get("database"));
        	System.err.println("\twith user:"+databaseSettings.get("username")+" & pass:"+databaseSettings.get("password"));
        	//sqle.printStackTrace();
        	System.exit(1);
        }
    }

    public void closeConnection()
    {
    	try {
			this.connection.close();
		} catch (SQLException sqle) {
			//sqle.printStackTrace();
		}
    }
    
	public void printConfigValues()
    {
        ArrayList<String> values = new ArrayList<String>();
        values.addAll(databaseSettings.values());
        for (String vals : databaseSettings.values()) { 
            for (String keys : databaseSettings.keySet()) { 
                if (databaseSettings.get(keys) == vals)
                    System.out.println(keys + ":" + vals);
            } 
        }
    }
	
    public int returnNumberOfColumnsInResultSet(ResultSet recSet)
    {
        int numOfColumns = 0;
        try {
            ResultSetMetaData selectRepNumMD = recSet.getMetaData();
            numOfColumns = selectRepNumMD.getColumnCount();
            //System.out.println("Total Columns: " + numOfColumns);
        } catch (SQLException sqle) {
        	//sqle.printStackTrace();
        }
        return numOfColumns;
    }
    
    public int returnNumberOfRowsInResultSet(ResultSet recSet)
    {
        int numOfRows = 0;
        try {
            //Get num of rows
            recSet.last();
            numOfRows = recSet.getRow();
            recSet.beforeFirst();
            //System.out.println("Total Rows: " + numOfRows);
        } catch (SQLException sqle) {
        	//sqle.printStackTrace();
        }
        return numOfRows;
    }
}