package com.spd.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spd.entity.User;
import com.spd.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showIndexPage(HttpSession session) {
		System.out.println("UserController -- showIndexPage()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "index";
	}

	@GetMapping("/viewBooks")
	public String viewBooks(HttpSession session) {
		System.out.println("UserController  -- viewBooks()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "viewBooks";
	}

	@GetMapping("/addBook")
	public String addBooks(HttpSession session) {
		System.out.println("UserController  -- addBooks()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "addBooks";
	}

	@GetMapping("/editBook")
	public String editBook(HttpSession session) {
		System.out.println("UserController  -- editBook()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "editBook";
	}

	@GetMapping("/deleteBook")
	public String deleteBook(HttpSession session) {
		System.out.println("UserController  -- deleteBook()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "deleteBook";
	}

	@GetMapping("/placeOrder")
	public String placeOrder(HttpSession session) {
		System.out.println("UserController  -- placeOrder()");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "placeOrder";
	}

	@GetMapping("/login")
	public String loginPage(@RequestParam(value = "error",required = false)String error,
			@RequestParam(value = "logout",required = false)String logout,HttpSession session) {
		System.out.println("loginPage");
		List<String> myroles = getUserRoles();
		session.setAttribute("MyRoles", myroles);
		return "mylogin";

	}

	@GetMapping("/logout")
	public String logoutPage(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("**logoutPage**");
		List<String> myroles = getUserRoles();
		System.out.println("myroles");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping("/showRegister")
	public String showRegister(Model model) {
		System.out.println("**showRegisterPage**");
		User myuser=new User(); 
		model.addAttribute("myuser",myuser); 

		return "register";
	}

	@PostMapping("/registerMyUser") 
	public String registerUser(@ModelAttribute("myuser") User user, BindingResult 
			bindingResult,Model model) { 
		System.out.println("** registerUser **"); 
		//Do Validations 
		userService.registerUser(user); 
		return "index"; 
	} 

	public List<String> getUserRoles(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<String> myroles = new ArrayList<String>();
		if(auth != null) {
			Collection<GrantedAuthority> col =  (Collection<GrantedAuthority>) auth.getAuthorities();
			for(GrantedAuthority gauth: col) 
				myroles.add(gauth.getAuthority());

		}
		return myroles;
	}

}
