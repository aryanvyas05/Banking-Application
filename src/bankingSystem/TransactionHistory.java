package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import connectionJDBC.DatabaseConnection;

public class TransactionHistory {
	
	DatabaseConnection dbConnection = new DatabaseConnection();
	
    public void printTransactionHistory() {
    	String transaction_History = "SELECT * FROM transaction_history WHERE accountnumber = ?"; 
    	String userAccount = Verification.getVerifiedAccountNumber();
    	
        try (Connection conn = dbConnection.connect();
        	 PreparedStatement historyStmt = conn.prepareStatement(transaction_History))
        	  {
        	
        	historyStmt.setString(1, userAccount);

            try (ResultSet rs = historyStmt.executeQuery()){
                while(rs.next()) {
                	int transaction_id = rs.getInt("transaction_id");
                	String type = rs.getString("transaction_type");
    	            double amount = rs.getDouble("amount");	            
    	            Timestamp date = rs.getTimestamp("transaction_date");
    	            
    	            
    	            System.out.println("Transaction ID: " + transaction_id);
    	            System.out.println("Type: " + type);
    	            System.out.println("Amount: $" + amount);
    	            System.out.println("Date: " + date);
    		            System.out.println("-----------------------------");
                }           
                }
                	        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
