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
                
                 // Prepare the SQL statement to insert a new registration
                ps = conn.prepareStatement(QUERY_REGISTAR);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the update and check if any rows were affected
                int affectedRows = ps.executeUpdate();
                result = (affectedRows > 0); // If one or more rows were inserted, return true
                
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
                
                // Prepare the SQL statement to delete a registration
                ps = conn.prepareStatement(QUERY_DROP);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the update and check if any rows were deleted
                result = ps.executeUpdate() > 0; 
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
                
                // Prepare the SQL statement to delete all registrations for a term
                ps = conn.prepareStatement(QUARY_WITHDRAW);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                // Execute the update and check if any rows were deleted
                result = ps.executeUpdate() > 0;
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                /* Prepare the SQL statement to retrieve the registrations*/
                ps = conn.prepareStatement(QUERY_LISTING);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                rs = ps.executeQuery(); // Execute the query
                
                // Convert the ResultSet into a JSON string
                result = DAOUtility.getResultSetAsJson(rs);
               
                
                
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
