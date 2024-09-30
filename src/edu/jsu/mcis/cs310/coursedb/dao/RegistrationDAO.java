package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    
    
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
                
                // SQL query to insert a new registration record
                String QUERY_REGISTER = "INSERT INTO registration (studentid, termid, crn) VALUES (?,?,?)";
                ps = conn.prepareStatement(QUERY_REGISTER);
                
                // Set the parameters for the query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the update (INSERT) and check if a row was affected
                result = ps.executeUpdate() > 0;
                
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
                
                // SQL query to delete the registration record
                String QUERY_DROP = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(QUERY_DROP);
                
                // Set the parameters for the query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                 // Execute the update (DELETE) and check if a row was affected
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
                
                // SQL query to delete all registrations for the student in the given term
                String QUARY_WITHDRAW = "DELETE FROM registration WHERE termid = ? AND studentid = ?";
                ps = conn.prepareStatement(QUARY_WITHDRAW);
                
                // Set the parameters for the query 
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                // Execute the update (DELETE) and check if rows were affected
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
                
                //SQL query to fetch the list of registrations for the student in the given term
                String QUERY_LISTING = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(QUERY_LISTING);
                
                //Set the parameters for the query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                //Execute the query (SELECT)
                rs = ps.executeQuery();
                
                //Convert the ResultSet into JSON format using a utility class
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
