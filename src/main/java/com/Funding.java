package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Funding {
	
	private Connection connect() {
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rest_api?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String readFunding() {
		
		String output = ""; 
		 
		try   {    
			  
			  Connection con = connect(); 
		 
			   if (con == null)    {
				   return "Error while connecting to the database.."; 
				   
			   } 
			    
		   // Prepare the html table to be displayed    
		  output = "<table border='1'><tr>"
		  		+ "<th>Provider Name</th>"
		  		+ "<th>Contact No</th>"
		  		+ "<th>Email</th>"
		  		+ "<th>Amount</th>"
		  		+ "<th>Address</th>"
		  		+ "<th>Update</th>"
		  		+ "<th>Remove</th></tr>"; 
		 
		   String query = "select * from funding_management";    
		   Statement stmt = con.createStatement();    
		   ResultSet rs = stmt.executeQuery(query); 
		 
		   // iterate through the rows in the result set    
		   while (rs.next()) {     
			   String fId = Integer.toString(rs.getInt("fund_id"));     
			   String name = rs.getString("funding_provider_name"); 
			   String contactNo = rs.getString("contact_no"); 
			   String email = rs.getString("email"); 
			   String amount= rs.getString("amount");
			   String address = rs.getString("address");     
		 
		    // Add into the html table
			   output += "<tr><td><input name='hiddenIDUpdate' type='hidden' value='" + fId + "'>"+ name + "</td>"; 
			   output += "<td>" + contactNo + "</td>"; 
			   output += "<td>" + email + "</td>"; 
			   output += "<td>" + amount + "</td>"; 
			   output += "<td>" + address + "</td>";     
		 
		    // buttons     
			   output += "<td><input name='btnUpdate' type='button'"
			   		+ "value='Update' class='btnUpdate btn btn-secondary'></td>" 
			   		+ "<td><input name='btnRemove' type='button' value='Remove'"
			   		+ "class='btnRemove btn btn-danger' data-fid='"      
			   		+ fId +"'></td></tr>";    
		   } 
		 
		   con.close(); 
		 
		   // Complete the html table    
		   output += "</table>";   
		   
		}catch (Exception e)   {    
			output = "Error while reading the Users.";    
			System.err.println(e.getMessage());   
		} 
		 
		  return output; 
		  
	}
	
	public String insertFunding(String name, String contact_no, String email, String amount, String address) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while conncting to the database for inserting..";
				
			}
			
			String query = "INSERT INTO funding_management(`funding_provider_name`, `contact_no`, `email`, `amount`, `address`) VALUES (?,?,?,?,?)";
			
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, contact_no);
			ps.setString(3, email);
			ps.setString(4, amount);
			ps.setString(5, address);
			
			ps.executeUpdate();
			ps.close();
			
			String newFunding = readFunding();
			output = "{\"status\":\"success\", \"data\": \""+newFunding+"\"}";
		//	output = "Insert Successfully";
			
		}catch (Exception e) {
			// TODO: handle exception
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the users.\"}";
			System.out.println(e);
		}
		
		return output;
		
	}
	
	public String updateFunding(String ID, String name, String contact_no, String email, String amount, String address)  {   
		
		String output = ""; 
	 
		try   {    
			Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for updating."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "UPDATE funding_management SET funding_provider_name=?, contact_no=?, email=?, amount=?, address=?"
	   		+ "WHERE fund_id=?"; 
	 
	   PreparedStatement ps = con.prepareStatement(query); 
	 
	      
	    ps.setString(1, name);
		ps.setString(2, contact_no);
		ps.setString(3, email);
		ps.setString(4, amount);
		ps.setString(5, address);       
		ps.setInt(6, Integer.parseInt(ID)); 
	 
	      
	   ps.execute();    
	   con.close(); 
	 
	   String newFunding = readFunding();
	   output = "{\"status\":\"success\", \"data\": \""+newFunding+"\"}";
	//   output = "Updated successfully";   
	   
		}catch (Exception e)   {    
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";    
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	  
	}
	
	public String deleteFunding(String fId)  {   
		
		String output = ""; 
	 
	  try   {    
		  Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for deleting."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "delete from funding_management where fund_id=?"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setInt(1, Integer.parseInt(fId)); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newFunding = readFunding();
	   output = "{\"status\":\"success\", \"data\": \""+newFunding+"\"}";  
	   
	  }catch (Exception e)   {    
		  output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";    
		  System.err.println(e.getMessage());   
	  } 
	 
	  return output;  
	  
	}

}
