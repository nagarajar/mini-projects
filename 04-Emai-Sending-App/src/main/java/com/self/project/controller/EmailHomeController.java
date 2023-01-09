package com.self.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.self.project.model.Email;
import com.self.project.service.EmailService;

@Controller
public class EmailHomeController {

	@Autowired
	private EmailService service;
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	
	//
	@PostMapping("/sendemail")
	public String sendMail(@ModelAttribute Email email,HttpSession session) {
		service.sendEmail(email);
		session.setAttribute("msg", "Email Sent Successfully");
		return "redirect:/";
	}
}
