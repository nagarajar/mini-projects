package com.mini.project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.mini.project.entity.User;

@Component
public class MailSenderUtil {

	@Autowired
	private JavaMailSender javaMailSender;
	
	private String from = "rajanagaraja36@gmail.com";
	
	private String url = "http://localhost:8080/unlock";
	
	public String sendUnlockMail(User user) {
		
		StringBuilder body = new StringBuilder(
				"Hi "+user.getFirstName()+" "+user.getLastName()+" :"+"\n\n" 
				+ "\t Welcome to IES family, your registration almost complete."+"\n\n"+
				"\t Please unlock your account using below details."+"\n\n"+
				"\t Temporary Password : "+user.getUserPwd()+"\n\n"+
				"\t Link to unlock account: "+url+"\n\n"+
				"Thanks,"+"\n"+
				"IES Team"
				);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(user.getEmail());
		message.setSubject("Unlock IES Account");
		message.setText(body.toString());
		
		javaMailSender.send(message);
		
		return "Please check your email to unlock account";
	}
	
	public String sendForgotPwdMail(User user) {
		
		StringBuilder body = new StringBuilder(
				"Hi "+user.getFirstName()+" "+user.getLastName()+" :"+"\n\n" 
				+ "\t Based on your request we are sharing your credentials."+"\n\n"+
				"\t Please login to your account using below details."+"\n\n"+
				"\t Password : "+user.getUserPwd()+"\n\n"+
				"Thanks,"+"\n"+
				"IES Team"
				);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(user.getEmail());
		message.setSubject("Password Details");
		message.setText(body.toString());
		javaMailSender.send(message);
		return "Please check your email for password details";
	}
	
	
}
