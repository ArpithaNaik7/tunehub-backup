package com.kodnest.controller;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.entity.Song;
import com.kodnest.entity.User;
import com.kodnest.serviceimpl.UserServiceImpl;
import com.konest.service.SongService;
import com.konest.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserServiceImpl serviceImpl;
	
	@Autowired
	SongService songService;
	
	@PostMapping("/register")
	
	public String userdata(@ModelAttribute User user) {
		
		//to check a user with email is present or not
		boolean userExists=userService.emailexists(user);//email with user is present
		
		if(userExists==false) {
		 userService.postUser(user);//saves user details
		 System.out.println("user added successfully");
		}
		else {
			System.out.println("duplicate user");
		}
		return "login";
	}
	
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session, Model model) {
		
		//to check a user with a mail is present or not
		if(userService.validUser(email, password)==true) {
			session.setAttribute("email",email);

			String role = userService.getRole(email);

			if(role.equals("admin")) {
				return"adminhome";
			}
			else {
				
				User user = userService.getUser(email);
				boolean userstatus = user.isPremium();
				List<Song> fetchAllSongs = songService.fetchAllSongs();
				model.addAttribute("songs", fetchAllSongs);
				
				model.addAttribute("ispremium", userstatus);
				return "customerhome";
			}
		}
		else {
			return "login";
		}
	
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/exploresongs")
	public String exploresongs(String email) {
		return email;
		
		
	}

}
