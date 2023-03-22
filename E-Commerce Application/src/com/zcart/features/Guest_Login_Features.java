package com.zcart.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.zcart.customexceptions.CustomException_InvalidInput;
import com.zcart.interfaces.Interface_Guest_Login_Features;

public class Guest_Login_Features implements Interface_Guest_Login_Features{
	public void getProductDatalist() {
		System.out.println("<<<Following are Details of all Products avalaible at Zkart>>>");
		
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
			//System.out.println("Database Accessed");//>hence placed here ad if exception occurs does not jump back
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		getBack_LoginPage();

	}
	public void getBack_LoginPage() {
		System.out.println("Enter '1' to get back to login page:");
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Please Enter your Choice:");
		String input_num=sc1.next();
		if(input_num.equals("1")) {
			new General_Login().get_User_Input();
		}
		else {
			try {
			throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
			}
			catch(Exception e) {
				System.out.println(e);
				getBack_LoginPage();
			}
		}
	}
	
	
	
	

}
