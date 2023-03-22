package com.zcart.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import com.zcart.customexceptions.CustomException_InvalidInput;
import com.zcart.interfaces.Interface_User_Login_Features;

public class User_Login_Features implements Interface_User_Login_Features{
	
	HashMap<Integer, Integer> cart_item_quant= new HashMap<Integer,Integer>();
	Set<Entry<Integer,Integer>> productid_set;
	Iterator<Entry<Integer,Integer>> iterator;
	int class_level_cart_value=0;
	static String class_level_username;
	
	
	public void getProductDatalist() {
		System.out.println("<<<Following are Details of all Products avalaible at Zkart>>>");
		
		Connection connection=ComConnectionUserdata.getComConnectionUserdata();
		try {
			PreparedStatement ps =connection.prepareStatement("Select * from productdata"); 
			ResultSet rs=ps.executeQuery();//4)create qwery and pass to execute.statement//gets handled by SQLException(for non select type commands use any, executeupdate(String sql) etc)
				while(rs.next()) {
				System.out.println("Product id: "+rs.getInt(1));
				System.out.println("Product Name: "+rs.getString(2));
				System.out.println("Product Description: "+rs.getString(3));
				System.out.println("Product Price: "+rs.getInt(4));
				System.out.println("Product Stock left: "+rs.getInt(5));
				
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				}
			//System.out.println("Database Accessed");//>hence placed here ad if exception occurs does not jump back
		} catch (SQLException exc) {//it basically catches the Syntax error in Sql Qwery>if username is wrong it return a blank passwordsafe
			exc.printStackTrace();
		}
		addTocart();
		
	}
	public void addTocart() {
		//int cart_value=0;
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter '1' to add product to cart and view cart");
		System.out.println("Enter 2 buy");
		System.out.println("Enter '3' to Go Back to login page");
		System.out.print("Please Enter your Choice:");
		String input_option =sc1.next();
		
			if(input_option.equals("1")) {/*if 1 enters login page*/
				try {
				System.out.print("Add to Cart By Entering Product id: ");
				int input_Product_id=sc1.nextInt();
				System.out.print("Enter Quantity of prduct id "+input_Product_id+" to buy: ");
				int input_Product_Quantity=sc1.nextInt();
				cart_item_quant.put(input_Product_id, input_Product_Quantity);
				System.out.println("added to the cart!!!!!!!!");
				}
				catch(Exception e){
					System.out.println(e);
					addTocart();
				}
				System.out.println("*********************************************************");
				System.out.println(" Your CART>>>>>");
				System.out.println("*********************************************************");
				productid_set=cart_item_quant.entrySet();
				iterator =productid_set.iterator();
				int cart_value=0;
				while(iterator.hasNext()) {
					Entry<Integer,Integer> entry =iterator.next();
					System.out.println("product id >>>"+entry.getKey());
					System.out.println("Quantity >>>"+entry.getValue());
					
					Connection connection=ComConnectionUserdata.getComConnectionUserdata();
					int Product_price;
					try {
						PreparedStatement ps =connection.prepareStatement("select product_price from productdata where id =?"); 
						ps.setInt(1,entry.getKey());
						ResultSet rs=ps.executeQuery();
							while(rs.next()) {
							Product_price=rs.getInt(1);
							cart_value=cart_value+(Product_price*entry.getValue());
		
							}
						System.out.println("Database Accessed");//>hence placed here ad if exception occurs does not jump back
					} catch (SQLException exc) {//it basically catches the Syntax error in Sql Qwery>if username is wrong it return a blank passwordsafe
						exc.printStackTrace();
					}
					System.out.println("---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*");
				}
				
				get_cart_value(cart_value);
				addTocart();
				
			}
		
			else if(input_option.equals("2")) {
				buy_Cart();
			}
			else if(input_option.equals("3")) {/*if 2 */
				new General_Login().get_User_Input();
			}
			else if(input_option.equals("4")) {/*if 2 */
			}
		
		
			else  {/*when user mistakenly inputs 3 or 4 invalid input custom exception throws and again enters welcome page*/
				try {
					throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
				}
				catch(RuntimeException re){
					re.printStackTrace();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				finally {
					
					addTocart();
				//get_User_Input();/*loop till user enter 1 or 2*/
				}
				//sc1.close();	
		}

		
	}
	public void get_cart_value(int a) {
		System.out.println("Total Cart Value>>"+a);
		class_level_cart_value=a;
	}
	public void buy_Cart() {
		productid_set=cart_item_quant.entrySet();
		iterator =productid_set.iterator();
		while(iterator.hasNext()) {
			Entry<Integer,Integer> entry =iterator.next();//>>>Entry entry<String,Integer> =ir2.next();
			
			int input_Quantity =entry.getValue();
			Connection connection=ComConnectionUserdata.getComConnectionUserdata();
			int Product_Quantity=0;
			String qwery1="select product_quantity from productdata where id ="+entry.getKey()+"";
			String qwery2="update productdata set product_quantity=? where id="+entry.getKey()+"";
			
			try {
				PreparedStatement ps =connection.prepareStatement(qwery1);//binary keyword identifies username with case sensitive 
				//ResultSet set= ps.getResultSet();
				ResultSet rs=ps.executeQuery();//4)create qwery and pass to execute.statement//gets handled by SQLException(for non select type commands use any, executeupdate(String sql) etc)
					while(rs.next()) {
					Product_Quantity=rs.getInt(1);
					}
				} catch (SQLException exc) {//it basically catches the Syntax error in Sql Qwery>if username is wrong it return a blank passwordsafe
				exc.printStackTrace();
			}
			if(Product_Quantity>=input_Quantity){
				int updated_Quantity=Product_Quantity-input_Quantity;
				try {
					PreparedStatement ps1 =connection.prepareStatement(qwery2);
					ps1.setInt(1,updated_Quantity );
					ps1.execute();
				} catch (SQLException e) {
					System.out.println(e);

				}//binary keyword identifies username with case sensitive
				
			}
			else {
				try {
				throw new CustomException_InvalidInput("Invalid input:Please Enter Valid Input");
				}
				catch(Exception e) {
					System.out.println(e);
					addTocart();

				}
			}
				

		}
		payment();
	}
	public void payment() {
		if(class_level_cart_value>0) {
			System.out.println("Payement Successful!!>Amount>"+class_level_cart_value);	
			System.out.println("ThankYOU For Shooping at ZCart");
			
			
			
			productid_set=cart_item_quant.entrySet();
			iterator =productid_set.iterator();
			while(iterator.hasNext()) {
				Entry<Integer,Integer> entry =iterator.next();//>>>Entry entry<String,Integer> =ir2.next();
				int input_product_id=entry.getKey();
				int input_Quantity =entry.getValue();
				String qwery3="insert into userproducthistory(username,product_id,product_quantity_bought) values(?,?,?)";			
				Connection connection=ComConnectionUserdata.getComConnectionUserdata();
				try {
					PreparedStatement ps =connection.prepareStatement(qwery3);
					ps.setString(1,class_level_username);
					ps.setInt(2,input_product_id);
					ps.setInt(3,input_Quantity);
					ps.execute();
					System.out.println("data stored to database successful");
				} catch (SQLException exc) {
					System.out.println(exc);
				}
			}
			
			
			
			
		}
		else {
			System.out.println("<<<<<<Cart is Empty>>>>>>");
			addTocart();

		}
	}
	public void get_username(String username) {
		System.out.println(username);
		class_level_username=username;
	}

	
	
	
	
	
	
	 
}
