package com.zcart.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Map.Entry;

import com.zcart.customexceptions.CustomException_InvalidInput;
import com.zcart.interfaces.Interface_Admin_Login;

public class Admin_Login_Features implements Interface_Admin_Login{
	
	public void adminInterface() {
		Scanner sc1 = new Scanner(System.in);
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("Enter '1' to Check product Details: ");
		System.out.println("Enter '2' to Change Quantity of Existing Product :");
		System.out.println("Enter '3' to Add new Product or Delete Product :");
		System.out.println("Enter '4' to Check Registered User Details :");
		System.out.println("Enter '5' to Check User History :");
		System.out.println("Enter '6' to go back to Login Page");
		
		System.out.print("Please Enter your Choice:");
		String input_option =sc1.next();
		
			if(input_option.equals("1")) {/*if 1 enters login page*/
				 getProductDatalist();
				 adminInterface();
			}
		
			else if(input_option.equals("2")) {
				change_Quantity();
				adminInterface();

			}
			else if(input_option.equals("3")) {
				manupulate_Product();
				adminInterface();
			}
			else if(input_option.equals("4")) {
				getUserDetails();
				adminInterface();

			}
			else if(input_option.equals("5")) {
				getUserHistory();
				adminInterface();

			}
			else if(input_option.equals("6")) {
				new General_Login().get_User_Input();

			}
		
		
			else  {/*when user mistakenly inputs 6 or 7 invalid input custom exception throws and again enters welcome page*/
				try {
					throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
				}
				catch(RuntimeException re){
					System.out.println(re);
				
				}
				adminInterface();/*loop till user enter 1 or 2*/
				
			}

		
	}
	public void getProductDatalist() {
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("<<<Following are Details of all Products avalaible at Zkart>>>");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement("Select * from productdata");
			ResultSet rs=ps.executeQuery();
				while(rs.next()) {
				System.out.println("Product id: "+rs.getInt(1));
				System.out.println("Product Name: "+rs.getString(2));
				System.out.println("Product Description: "+rs.getString(3));
				System.out.println("Product Price: "+rs.getInt(4));
				System.out.println("Product Stock left: "+rs.getInt(5));
				
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				}
			//System.out.println("Database Accessed");
		} catch (SQLException exc) {
			System.out.println(exc);
		}
	}

	public void change_Quantity() {
		getProductDatalist();
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Enter product id to Change its Avaliable_Quantity : ");
		String product_id =sc1.next();
		System.out.print("Enter New Quantity : ");
		int input_quant =sc1.nextInt();

		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		String qwery1="update productdata set product_quantity=? where id="+product_id+"";
		try {
			PreparedStatement ps =connection.prepareStatement(qwery1);
			ps.setInt(1,input_quant);
			ps.execute();
			System.out.println("data stored to database successful");
			
		} catch (SQLException exc) {
			System.out.println(exc);
			System.out.println("Please enter Valid id and  Valid Qunatity");
			adminInterface();
		}
		
	}
	public void manupulate_Product() {
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter '1' to Delete Existing Product: ");
		System.out.println("Enter '2' to Add new Product: ");
		
		System.out.print("Please Enter your Choice:");
		String input_option =sc1.next();
		
			if(input_option.equals("1")) {/*if 1 enters login page*/
				getProductDatalist();
				deleteProduct();
				adminInterface();
			}
		
			else if(input_option.equals("2")) {
				addProduct();
				adminInterface();

			}
			else  {/*when user mistakenly inputs 3 or 4 invalid input custom exception throws and again enters welcome page*/
				try {
					throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
				}
				catch(RuntimeException re){
					System.out.println(re);
				}
				finally {
					adminInterface();
				}	
			}
			
		
	}
	public void deleteProduct() {
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Enter product id to Delete : ");
		String product_id =sc1.next();
	
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		String qwery1="delete from productdata where id="+product_id+"";
		try {
			PreparedStatement ps =connection.prepareStatement(qwery1);
			ps.execute();
			System.out.println("Product Deleted Sucessfully");
			
		} catch (SQLException exc) {
			System.out.println(exc);
			System.out.println("Please enter Valid id");
			adminInterface();
		}
		
		
	}
	public void addProduct() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Product Name:");
		String product_name =sc.nextLine();
		System.out.print("enter Product Description: ");
		String product_descrip=sc.nextLine();
		System.out.print("enter Product Price : ");
		int product_price=sc.nextInt();//have problem with nextLine() :soultion at stackoverflow url=https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo#:~:text=It's%20because%20when%20you%20enter,buffer%20from%20the%20first%20input.
		System.out.print("enter Product Quantity: ");
		int product_quantity=sc.nextInt();
		
		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement("insert into productdata(product_name,product_description,product_price,product_quantity)values(?,?,?,?)");
			ps.setString(1,product_name);
			ps.setString(2,product_descrip);
			ps.setInt(3, product_price);
			ps.setInt(4,product_quantity );
			ps.execute();
			System.out.println("data stored to database successful");
		} catch (SQLException exc) {
			System.out.println(exc);
		}
	}
	public void getUserDetails() {
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("<<<Following are User Details>>>");	
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement("Select * from userdata");
			ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					System.out.println("First Name: "+rs.getString(1));
					System.out.println("Last Name : "+rs.getString(2));
					System.out.println("Password  : "+rs.getString(3));
					System.out.println("city      : "+rs.getString(4));						
					System.out.println("Mail id   : "+rs.getString(5));	
					System.out.println("Mobile no : "+rs.getLong(6));	
					System.out.println("Username  : "+rs.getString(7));	
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				}
					//System.out.println("Database Accessed");
		}
		catch (SQLException exc) {
			System.out.println(exc);
		}
		
	}
	public void getUserHistory() {
		System.out.print("Please Enter Username of User to get User History :");	
		Scanner sc = new Scanner(System.in);
		String username= sc.next();
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		String qwery="Select * from userproducthistory where username='"+username+"'";
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement(qwery);
			ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					System.out.println("Serial No             : "+rs.getInt(1));
					System.out.println("Username              : "+rs.getString(2));
					System.out.println("Product_id of product : "+rs.getInt(3));
					System.out.println("Quantity of product   : "+rs.getInt(4));
					System.out.println("Time of Transaction   : "+rs.getString(5));						
				
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				}
					//System.out.println("Database Accessed");
		}
		catch (SQLException exc) {
			System.out.println(exc);
		}
		
		
		
		
		
		
	}
	
	
	

}
