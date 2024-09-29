package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                rs = ps.executeQuery();
                
                JsonArray sectionsArray = new JsonArray();
                
                rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                
                while (rs.next()){
                    
                    JsonObject sectionObj = new JsonObject();
                    
                    for (int i = 1; i <= columnCount; i++){
                        String columnName = rsmd.getColumnName(i);
                        Object value = rs.getObject(i);
                        sectionObj.put(columnName, value);
                    }
                    
                    sectionsArray.add(sectionObj);
                }
                
                result = sectionsArray.toString();
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}