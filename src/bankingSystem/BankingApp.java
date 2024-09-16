package bankingSystem;

import java.util.Scanner;


public class BankingApp {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		

		
        UserFunction newUser = new UserFunction();
        BalanceFunction balance = new BalanceFunction();
        Verification ver = new Verification();
        TransactionHistory history = new TransactionHistory();
        
        if(ver.userVerification()) {
        	int input = 1;
    		while(input != 0) {
    			
    			System.out.println("Hello and welcome to Aadi's Rich Bank.");
    			System.out.println("What can we help you with today");
    			System.out.println("1. View account details");
    			System.out.println("2. View balance");
    			System.out.println("3. Withdraw money");
    			System.out.println("4. Deposit Money");
    			System.out.println("5. Create new user");
    			System.out.println("6. View transaction history");
    			System.out.println("0. Exit");
    			
    			input = scan.nextInt();
    			
    			switch(input) {
    			case 0 : 
    				System.out.println("Thank you for banking with us have a nice day.");
    			break;
    			
    			case 1 :
    				newUser.printUsers();
    			break;
    			
    			case 2 : 
    				balance.displayBalance();
    			break;
    			
    			case 3 :
    				balance.withdraw();
    			break;
    			
    			case 4 :
    				balance.deposit();
    			break;
    			
    			case 5 : 
    				newUser.insertUser();
    			break;
    			
    			case 6 : 
    				history.printTransactionHistory();
    			break;
    			
    			default :
    				System.out.println("Invalid option. Please try again.");
    			}
    		}
    		scan.close();
    			}
        	}
        }

