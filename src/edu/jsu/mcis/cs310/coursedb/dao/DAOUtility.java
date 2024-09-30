package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;


public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

                ResultSetMetaData rsmd = rs.getMetaData();  // Get metadata from the ResultSet, such as column count
                int columnCount = rsmd.getColumnCount(); // Total number of columns
                
                
                while (rs.next()){
                    
                    // Create a JSON object for each row
                    JsonObject record = new JsonObject();
                    
                    for (int i = 1; i <= columnCount; i++){
                        String columnName = rsmd.getColumnName(i); // Get column name
                        Object columnValue  = rs.getObject(i); // Get the value from the current row at the current column
                        
                        // Convert the column value to a string if not null, otherwise store null
                        record.put(columnName, columnValue != null ? columnValue.toString() : null);
                       
                    }
                    
                    records.add(record);
                }
                
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
