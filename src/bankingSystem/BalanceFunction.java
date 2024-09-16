package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connectionJDBC.DatabaseConnection;

public class BalanceFunction {

    Scanner scan = new Scanner(System.in);
    private DatabaseConnection dbConnection = new DatabaseConnection();

    public void displayBalance() {
        String displayBalanceQuery = "SELECT * FROM user_balance WHERE accountnumber = ?";

        String userAccount = Verification.getVerifiedAccountNumber();
        String userPassword = Verification.getVerifiedAccountPassword();

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(displayBalanceQuery)) {

            pstmt.setString(1, userAccount);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean userFound = false;

                while (rs.next()) {
                    String accountNumber = rs.getString("accountnumber");
                    String accountBalance = rs.getString("balance");
                    String password = rs.getString("user_password");

                    if (userAccount.equals(accountNumber) && userPassword.equals(password)) {
                        System.out.println("Account Number: " + accountNumber);
                        System.out.println("Balance: $" + accountBalance);
                        System.out.println("-----------------------------");
                        userFound = true;
                        break;
                    }
                }

                if (!userFound) {
                    System.out.println("User not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw() {
        String withdrawQuery = "UPDATE user_balance SET balance = ? WHERE accountnumber = ?";
        String checkBalanceQuery = "SELECT balance FROM user_balance WHERE accountnumber = ?";

        String accountNumber = Verification.getVerifiedAccountNumber();
        System.out.println("Please enter amount to be withdrawn: ");
        int amount = scan.nextInt();
        scan.nextLine();

        try (Connection conn = dbConnection.connect();
             PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceQuery);
             PreparedStatement withdrawStmt = conn.prepareStatement(withdrawQuery)) {

            checkBalanceStmt.setString(1, accountNumber);
            ResultSet rs = checkBalanceStmt.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("balance");

                if (currentBalance >= amount) {
                    int newBalance = currentBalance - amount;

                    withdrawStmt.setInt(1, newBalance);
                    withdrawStmt.setString(2, accountNumber);

                    int rowsAffected = withdrawStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Withdrawal successful. New balance: $" + newBalance);

                        
                        String insertTransaction = "INSERT INTO transaction_history (accountnumber, transaction_type, amount) VALUES (?, ?, ?)";
                        try (PreparedStatement historyStmt = conn.prepareStatement(insertTransaction)) {
                            historyStmt.setString(1, accountNumber);
                            historyStmt.setString(2, "Withdrawal");
                            historyStmt.setDouble(3, amount);

                            historyStmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Failed to update balance.");
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deposit() {
        String depositQuery = "UPDATE user_balance SET balance = ? WHERE accountnumber = ?";
        String checkBalanceQuery = "SELECT balance FROM user_balance WHERE accountnumber = ?";

        String accountNumber = Verification.getVerifiedAccountNumber();
        System.out.println("Please enter amount to deposit: ");
        int amount = scan.nextInt();
        scan.nextLine();

        try (Connection conn = dbConnection.connect();
             PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceQuery);
             PreparedStatement depositStmt = conn.prepareStatement(depositQuery)) {

            checkBalanceStmt.setString(1, accountNumber);
            ResultSet rs = checkBalanceStmt.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("balance");

                if (amount > 0) {
                    int newBalance = currentBalance + amount;

                    depositStmt.setInt(1, newBalance);
                    depositStmt.setString(2, accountNumber);

                    int rowsAffected = depositStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Deposit successful. New balance: $" + newBalance);

                        
                        String insertTransaction = "INSERT INTO transaction_history (accountnumber, transaction_type, amount) VALUES (?, ?, ?)";
                        try (PreparedStatement historyStmt = conn.prepareStatement(insertTransaction)) {
                            historyStmt.setString(1, accountNumber);
                            historyStmt.setString(2, "Deposit");
                            historyStmt.setDouble(3, amount);

                            historyStmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Failed to update balance.");
                    }
                } else {
                    System.out.println("Invalid deposit amount.");
                }
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
