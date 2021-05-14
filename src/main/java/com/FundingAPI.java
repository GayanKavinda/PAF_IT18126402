package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FundingAPI")
public class FundingAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Funding Obj = new Funding();
	
    public FundingAPI() {
        super();
   
 }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.readFunding();
		
		response.getWriter().write(output.toString());

	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.insertFunding(request.getParameter("name"), 
				request.getParameter("contact_no"),
				request.getParameter("email"),
				request.getParameter("amount"),
				request.getParameter("address"));
		
		System.out.println(output);
		
		response.getWriter().write(output);
		
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ID = request.getParameter("ID");
		String name = request.getParameter("name");
		String contact_no = request.getParameter("contact_no");
		String email = request.getParameter("email");
		String amount = request.getParameter("amount");
		String address = request.getParameter("address");
		System.out.println("ID: "+ID);
	
		String output = Obj.updateFunding(ID,name, contact_no, email, amount, address);
		
		response.getWriter().write(output);
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("fId");

		String output = Obj.deleteFunding(id);
		
		System.out.println(output);
		
		response.getWriter().write(output.toString());
	
	}
	
	
	private static Map getParseMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			Scanner sc = new Scanner(request.getInputStream(), "UTF-8");
			String query = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";
			sc.close();
			
			String[] params = query.split("&");
			
			for(String param : params) {
				
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
