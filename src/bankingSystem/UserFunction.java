	package bankingSystem;
	
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.Scanner;
	import connectionJDBC.DatabaseConnection;
	
	public class UserFunction {
		Scanner scan = new Scanner(System.in);
		private DatabaseConnection dbConnection = new DatabaseConnection();
		Verification ver = new Verification();
		
		
		
		public void insertUser() {
			String insertuser = "INSERT INTO user_info(name, accountNumber, email, user_password) VALUES (?, ?, ?, ?)";
			
			
			try (Connection conn = dbConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(insertuser)){
				
				System.out.println("Please enter name: ");
				String name = scan.nextLine();
				System.out.println("Please enter account number: ");
				String accountNumber = scan.nextLine();
				System.out.println("Please enter email: ");
				String email = scan.nextLine();
				System.out.println("Please enter password to protect account: ");
				String password = scan.nextLine();
				
				pstmt.setString(1, name);
				pstmt.setString(2, accountNumber);
			    pstmt.setString(3, email);
			    pstmt.setString(4, password);
			    
			    int rowsAffected = pstmt.executeUpdate();
		        if (rowsAffected > 0) {
		            System.out.println("User inserted successfully.");
		        } else {
		            System.out.println("Failed to insert user.");
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());	    
			}
			
			 
		}
		
		public void printUsers() {
			
	    	
	    	String userAccount = Verification.getVerifiedAccountNumber();
	    	String userPassword = Verification.getVerifiedAccountPassword();
			
		    String printuser = "SELECT * FROM user_info WHERE accountnumber = ? AND user_password = ?"; 
		    try (Connection conn = dbConnection.connect();
		    	 PreparedStatement pstmt = conn.prepareStatement(printuser))
		    	  {
		    		
		    	pstmt.setString(1, userAccount);
		        pstmt.setString(2, userPassword);

		        try (ResultSet rs = pstmt.executeQuery()){
		        	boolean userFound = false;
		            while(rs.next()) {
		            	int id = rs.getInt("id");	            
			            String name = rs.getString("name");
			            String accountNumber = rs.getString("accountnumber");
			            String email = rs.getString("email");
			            String password = rs.getString("user_password");
			            
			            
			            if(userAccount.equals(accountNumber) && userPassword.equals(password)) {
			            	System.out.println("ID: " + id);
				            System.out.println("Name: " + name);
				            System.out.println("Account Number: " + accountNumber);
				            System.out.println("Email: " + email);
				            System.out.println("-----------------------------");
				            userFound = true;
			            		}
			            	}if (!userFound) {
		                System.out.println("User not found with account number: " + userAccount);
			            }
		            
		            }
		            	        
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
	}
