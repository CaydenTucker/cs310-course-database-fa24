package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private static final String QUERY_REGISTAR = "INSERT INTO REGISTRATIONS (studentid, termid, crn) VALUES (?, ?, ?)";
    private static final String QUERY_DROP = "DELETE FROM refistrations WHERE studentid = ? AND termid = ? AND crn = ?";
    private static final String QUARY_WITHDRAW = "DELETE FROM registrations WHERE studentid = ? AND termid = ?";
    private static final String QUERY_LISTING = "SELECT * FROM registrations WHERE studentid = ? AND termid = ?";
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_REGISTAR);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int affectedRows = ps.executeUpdate();
                result = (affectedRows > 0);
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(QUERY_DROP);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int affectedRows = ps.executeUpdate();
                result = (affectedRows > 0);
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(QUARY_WITHDRAW);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int rowsAffected = ps.executeUpdate();
                result = (rowsAffected > 0);
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        //StringBuilder result = new StringBuilder();
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(QUERY_LISTING);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                rs = ps.executeQuery();
                //rsmd = rs.getMetaData();
                
                result = DAOUtility.getResultSetAsJson(rs);
                //int columnCount = rsmd.getColumnCount();
                
                /*while (rs.next()){
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(rsmd.getColumnName(i))
                                .append(": ")
                                .append(rs.getString(i))
                                .append(", ");
                    }
                    result.append("\n");
                }*/
                
                
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
