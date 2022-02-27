package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection
{
	public static Connection doconnect()
	{
		Connection con=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/Registration","root","123");
			System.out.println("Connected.....");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return con;
		
	}
	
	public static void main(String[] args)
	{
		doconnect();
	}

}
