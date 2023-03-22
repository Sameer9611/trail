package com.zcart.features;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComConnectionUserdata {
	public static Connection getComConnectionUserdata() {
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_application","root","Lenovo@9611");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


}

