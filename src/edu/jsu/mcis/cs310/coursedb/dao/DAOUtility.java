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

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                
                //ArrayList<ArrayList<Object>> allRows = new ArrayList();
                
                while (rs.next()){
                    JsonObject record = new JsonObject();
                    
                    for (int i = 1; i <= columnCount; i++){
                        String columnKey = rsmd.getColumnName(i);
                        Object columnValue  = rs.getObject(i);
                        
                        
                        record.put(columnKey, columnValue);
                    }
                    
                    records.add(record);
                }
                
                /*for (ArrayList<Object> row : allRows){
                    JsonObject record = new JsonObject();
                    
                    for (int i = 0; i < columnCount; i++){
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = row.get(i);
                        
                        if (columnValue == null){
                            record.put(columnName, null);
                        } else {
                            record.put(columnName, columnValue);
                        }
                    }
                    
                    records.add(record);
                }*/
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
