package com.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.rest.entity.User;


@WebServlet("/json")
public class JsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	List<User> users = new ArrayList<>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		
		
		response.getWriter().write( new Gson().toJson(users));
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int id =  Integer.parseInt( request.getParameter("id"));
			int age =  Integer.parseInt( request.getParameter("age"));
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			
			if (name == null || email == null || password == null) {
				throw new Exception();				
			} else {
				User u = new User(id, name, email, password, age);
				users.add(u);
				response.setStatus(200);
			}
			

		} catch (Exception e) {
			response.setStatus(400);

		}
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id =  Integer.parseInt( request.getParameter("id"));
			int age =  Integer.parseInt( request.getParameter("age"));
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			if (name == null || email == null || password == null) {
				throw new Exception();				
			} else {
				User old = getUserById(id);
				if(old != null) {
					users.remove(old);
					User newUser = new User(id, name, email, password, age);
					users.add(newUser);
					response.setStatus(200);
				} else {
					response.setStatus(400);
				}
				
				
			}
			
			
		} catch (Exception e) {
			response.setStatus(400);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int id =  Integer.parseInt( request.getParameter("id"));

			User u = getUserById(id);
			
			if(u != null) {
				users.remove(u);
				response.setStatus(200);
			} else {
				response.setStatus(400);
			}
			

		} catch (Exception e) {
			response.setStatus(400);

		}
	}
	
	private User getUserById(int id) {
		for (User u : users) {
			if (u.getId() == id) {
				users.remove(u);
				return u;
			}
		}
		return null;
	}
}