package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connectionJDBC.DatabaseConnection;

public class Verification {
	
	Scanner scan = new Scanner (System.in);
	private DatabaseConnection dbConnection = new DatabaseConnection();
    private static boolean isVerified = false;
    private static String verifiedAccountNumber;
    private static String verifiedAccountPassword;
	
	public boolean userVerification() {
		
		while(!isVerified) {
			System.out.println("Login to your account-> ");
			System.out.println();
			System.out.println("Enter account number: ");
			String userAccount = scan.nextLine();
			System.out.println("Enter password:");
			String userPassword = scan.nextLine();
			
			String verifyUser = "SELECT accountnumber, user_password FROM user_info WHERE accountnumber = ? AND user_password = ?";
			
			try {Connection conn = dbConnection.connect(); 
				PreparedStatement pstmt = conn.prepareStatement(verifyUser);
				
				pstmt.setString(1, userAccount);
	            pstmt.setString(2, userPassword);
	            
				try (ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					isVerified = true;
					verifiedAccountNumber = userAccount;
					verifiedAccountPassword = userPassword;
					System.out.println("Verification successful.");
					return true;
				}else {
					System.out.println("Please check your credentials.");
					
					}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
    public static boolean isUserVerified() {
        return isVerified;
    }
    
    public static String getVerifiedAccountNumber() {
        return verifiedAccountNumber;
    }
    
    public static String getVerifiedAccountPassword() {
        return verifiedAccountPassword;
    }
}
