package com.kafka.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kafka.model.Login;
import com.kafka.model.UserTracking;

@Controller
@RequestMapping("/event")
public class TrackController {

	private Map<String,String> userAccountList = new HashMap<String, String>();
	public Map<String, String> getUserAccountList() {
		return userAccountList;
	}
	public void setUserAccountList(Map<String, String> userAccountList) {
		this.userAccountList = userAccountList;
	}

	@Autowired
	UserTracking userTracking;
	
	private KafkaTemplate<String, UserTracking> kafkaTemplate ;
	
	public TrackController(KafkaTemplate<String, UserTracking> kafkaTempalte) {
		this.kafkaTemplate = kafkaTempalte;
		 
		Map<String,String> userList = new HashMap<String, String>();
		 userList.put("abhishek", "inspiron15");
		 userList.put("ranjan", "inspiron15");
		 userList.put("jaiswal", "inspiron15");
		 
		 this.setUserAccountList(userList);
		
		//etUserAccountList(getUserAccountList().put("abhishek", "inspiron"));
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getHome(Model model) {
		model.addAttribute("login",new Login());
		return "index";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String postEvents(@ModelAttribute("login") Login login,BindingResult result,HttpServletRequest request, HttpServletResponse response,Model model) {
		
		String view = "";
		String activity ="";
		boolean valid = false; 
		
		if (result.hasErrors()) {
			return "index";
		}
		System.out.println("P1: "+request.getSession(true)); 	
		//create a session   object and store usesr's details..
 	     HttpSession session= request.getSession(true);
 	   
  	   
 	    // org.springframework.boot.web.servlet.server.Session session = new org.springframework.boot.web.servlet.server.Session();
 	     String UserName = login.getUserName();
 	     String password = login.getPassword();
 	     

 	    // String validatePassword = this.getUserAccountList().get(UserName);

 	     if (this.getUserAccountList().containsKey(UserName)){
 	    	String validatePassword = this.getUserAccountList().get(UserName);
 	    	if(validatePassword.equalsIgnoreCase(password)) 
 	    	{
 	    		System.out.println("Valid credentails..");
 	    		System.out.println("User: "+UserName + " has logged in the system.");
 	    		activity += "LoggedIN";
 	 	    	valid = true;
 	 	    	view="redirecthome";
 	 	    	view = "redirect:event/myaccount";
 	 	    	
 	 	    	//session & cookie config
// 	 	    	session.setMaxInactiveInterval(10*60);
// 	 	    	Cookie SessionSpecificCookie = new Cookie("session", session.getId());
// 	 	    	response.addCookie(SessionSpecificCookie);	
 	 	    	
 	 	    	
 	 	    		
 	 	    	
 	 	    	//Initialize session and UserTracking Object.
 	 	    	
 	 	    	session.setAttribute("userID",login.getUserName());
 				session.setAttribute("sessionId", request.getSession().getId());
 				session.setAttribute("activityLog", "loggedIn");
 				
 				userTracking.setSessionID(request.getSession().getId());
 				userTracking.setCustomerID(login.getUserName());
 				userTracking.setActivityList(activity);
 				
 	    	}
 	    	else {
 	    		System.out.println("Incorrect password!!!.");
 	    		System.out.println("Try after sometime.");
 	    		view = "index";
 				model.addAttribute("status", "Incorrect Password.");
 				
 	    	}
 	     }
 	     else {
 	    	 System.out.println("User: "+ UserName+ "doesn't exists in out system.");
 	    	 System.out.println("Kindly create account first");
 	    	view = "index";
			model.addAttribute("status", "Invalid User!!");
 	     }
		kafkaTemplate.send("ServiceRequest", userTracking);

		return view;
		}
	
	@RequestMapping(value = "/myaccount",method = RequestMethod.GET)
	public String getAccount(Model model,HttpServletRequest request, HttpServletResponse response) {
		//model.addAttribute(attributeName, attributeValue);
		//HttpSession session =
			model.addAttribute("status", "Welcome user, you have logged in");
		return "home";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.POST)
	public String logout(HttpSession session,HttpServletRequest request, HttpServletResponse response ) {
		
//		session.setAttribute("userID",login.getUserName());
//		session.setAttribute("sessionId", request.getSession().getId());
//		
//		userTracking.setSessionID(request.getSession().getId());
//		userTracking.setCustomerID(login.getUserName());
//		userTracking.setActivityList(activity);
		String sessionID =(String) session.getAttribute("sessionId");
		String userID = (String) session.getAttribute("userID");
		String activityLog = (String) session.getAttribute("activityLog");
		activityLog += "****User: "+userID+"-Logged out.";
		userTracking.setCustomerID(userID);
		userTracking.setSessionID(sessionID);
		userTracking.setActivityList(activityLog);
		
		
		
		
		kafkaTemplate.send("ServiceRequest", userTracking);
		session.invalidate();
		return "redirect:/event";
	}
	
	
	
	
	
}
