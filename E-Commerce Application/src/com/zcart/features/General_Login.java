package com.zcart.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.zcart.customexceptions.CustomException_InvalidInput;
import com.zcart.customexceptions.CustomException_InvalidUsername;
import com.zcart.interfaces.Interface_General_Login;

public class General_Login implements Interface_General_Login {
	/*this method has the welcome page of Zcart,also takes input to register or login*/
	public void get_User_Input() {
		System.out.println("Welcome to Zkart");
		System.out.println("Enter '1' to login if u already have a Account");
		System.out.println("Enter '2' for Registration");
		System.out.println("Enter '3' for Login as Guest");
		System.out.println("Enter '4' for Admin Login");
		System.out.println("Enter '5' to exit:");
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Please Enter your Choice:");
		String input_num=sc1.next();

		if(input_num.equals("1")) {/*if 1 enters login page*/
			login_User();
			//sc1.close();	
		}
		else if(input_num.equals("2")) {/*if 2 */
			registrationForm();/*enters registration method*/
			get_User_Input();/*then enter login method*/
		}
		else if(input_num.equals("3")) {/*if 2 */
			new Guest_Login_Features().getProductDatalist();
		}
		else if(input_num.equals("4")) {/*if 2 */
			login_admin();
		}
		else if(input_num.equals("5")) {/*if 2 */
			exit();
		}
		
		
		else  {/*when user mistakenly inputs 3 or 4 invalid input custom exception throws and again enters welcome page*/
			try {
				throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
			}
			catch(RuntimeException re){
				System.out.println(re);
			}
			finally {
			get_User_Input();/*loop till user enter 1 or 2 or 3 or 4*/
			}
			//sc1.close();	
		}		
		
			
	}/*data filled earlier is stored in table here*/
	public void storeRegistrationDetails(String a,String b,String c,String d,String e,String f,long g) {
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement("insert into userdata(firstname,lastname,username,passwordsafe,city,mailid,mobilenumber) values(?,?,?,?,?,?,?)");
			ps.setString(1,a);
			ps.setString(2,b);
			ps.setString(3,c);
			ps.setString(4,d);
			ps.setString(5,e);
			ps.setString(6,f);
			ps.setLong(7,g);
			ps.execute();
			System.out.println("data stored to database successful");
		} catch (SQLException exc) {
			System.out.println(exc);
			System.out.println("Please enter Unique username:'"+c+"' is all ready taken");
		}
		
		
		

	}
	public void registrationForm() {/*form of registration filled */
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your First name:");
		String firstname =sc.next();
		System.out.print("enter your Last name:");
		String lastname=sc.next();
		System.out.print("enter suitable username:");
		String username=sc.next();//have problem with nextLine() :soultion at stackoverflow url=https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo#:~:text=It's%20because%20when%20you%20enter,buffer%20from%20the%20first%20input.
		System.out.print("enter suitable password:");
		String password=sc.next();
		System.out.print("enter your city:");
		String city=sc.next();
		System.out.print("enter your mail id:");
		String mailid=sc.next();
		System.out.print("enter your mobile number:");
		long mobnumber=sc.nextLong();
		storeRegistrationDetails(firstname,lastname,username,password,city,mailid,mobnumber);/*data from the form is passed to store method*/
		//sc.close();
		
		
		
	}
	public void login_User()  {
		
		Scanner sc2 = new Scanner(System.in);
		System.out.print("Please Enter your Username For Login: ");
		String username1=sc2.next();
		System.out.print("Please Enter your Password for login: ");
		String input_password=sc2.next();
		
		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		String fetched_password="";
		try {
			PreparedStatement ps =connection.prepareStatement("Select passwordsafe from userdata where binary username=?");
			ps.setString(1,username1);
			ResultSet rs=ps.executeQuery();//create qwery and pass to execute.statement//gets handled by SQLException(for non select type commands use any, executeupdate(String sql) etc)
				while(rs.next()) {
				fetched_password=rs.getString(1);
				}
			//System.out.println("Database Accessed");//>hence placed here ad if exception occurs does not jump back
		} catch (SQLException exc) {
			System.out.println(exc);
		}
		
		
		
		if(input_password.equals(fetched_password)) {
			new User_Login_Features().get_username(username1);
			new User_Login_Features().getProductDatalist();
			//sc2.close();
		
		}
		
		else if(input_password!=fetched_password){
			try {
				throw new CustomException_InvalidUsername("Invalid Input:Password and Username does not match");
				
			}
			catch(RuntimeException re){
				System.out.println(re);
			}
			
			get_User_Input();
			
			//sc2.close();
		}
		
	}
	public void login_admin()  {
		
		Scanner sc3 = new Scanner(System.in);
		System.out.print("Admin Enter your Username For Login: ");
		String username1=sc3.next();
		System.out.print("Admin Enter your Password for login: ");
		String input_password=sc3.next();
		
		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		String fetched_password="";
		try {
			PreparedStatement ps =connection.prepareStatement("Select Adminpasswordsafe from admindata where binary Adminusername=?");
			ps.setString(1,username1);
			ResultSet rs=ps.executeQuery();
				while(rs.next()) {
				fetched_password=rs.getString(1);
				}
			//System.out.println("Database Accessed");//>hence placed here ad if exception occurs does not jump back
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		
		
		
		if(input_password.equals(fetched_password)) {
			System.out.println("*---*---*---*---*---*---*---*---*---*---*---*");
			System.out.println("Admin Logged In");
			System.out.println("*---*---*---*---*---*---*---*---*---*---*---*");
			new Admin_Login_Features().adminInterface();
			//sc3.close();
		}
		
		else if(input_password!=fetched_password){
			try {
				throw new CustomException_InvalidUsername("Invalid Input:Password and Username does not match");
				
			}
			catch(RuntimeException re){
				System.out.println(re);
			}
			get_User_Input();
			
			//sc2.close();
		}
		
	}
	public void login_guest() {
		System.out.println("*---*---*---*---*---*---*---*---*---*---*---*");
		System.out.println("Guest Logged In");//>>access to another method(guest login features) or class which will show products
		System.out.println("*---*---*---*---*---*---*---*---*---*---*---*");

	}
	public void exit() {
		System.out.println("Adios Amigos!!!Do visit Zcart Again");
		System.exit(0);
	}
	

}
