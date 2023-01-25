package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@RequestMapping("/credentials")
@Controller
public class CredentialsController {

	CredentialsService credService;
	UserService userService;
    List<Credentials> credentialsList = new ArrayList<>();
    
	public CredentialsController(CredentialsService credService, UserService userService) {
		this.credService = credService;
		this.userService = userService;
	}
	@GetMapping()
	public String loginView(Model model,Authentication auth) {
		String userName = auth.getName();
		User user = userService.getUser(userName);
		model.addAttribute("credentials",credService.getAllCredentials(user.getUserId()));
	
		return "home";
	}

    @PostMapping()
    public String addCredentials(@ModelAttribute("credential") Credentials credentials,Authentication auth,Model model) {
		String userName = auth.getName();
		

		try {
			User user = userService.getUser(userName);
			
			if (credentials.getCredentialid() != null) {
				credentials.setUserId(user.getUserId());
				credService.update(credentials);
				model.addAttribute("successMessage", "Credential updated successfully");
			} else {
				credService.storeCredentials(credentials,user);
				model.addAttribute("successMessage", "Credential created successfully");
			}
			model.addAttribute("credentials", credService.getAllCredentials(user.getUserId()));
		} catch (Exception e) {

			model.addAttribute("failureMessage",
					"Something went wrong, your changes were not saved. Please try again!");
		}
		return "result";
	
    	
    }
    
    @GetMapping("/delete")
    public String deleteCredentials(Authentication auth,@ModelAttribute("id") int credentialsId,Model model) {
    	
    	

		try {
			String userName = auth.getName();

			User user = userService.getUser(userName);
			credService.deleteCredential(credentialsId);

			model.addAttribute("credentials", credService.getAllCredentials(user.getUserId()));
			model.addAttribute("successMessage", "Credentials deleted successfully");
		} catch (Exception e) {

			model.addAttribute("failureMessage",
					"Something went wrong, your changes were not saved. Please try again!");
		}
		return "result";
	
    	
    }
  
    
    @GetMapping("/viewAll")
    public String viewCredentials(Authentication auth,Model model) {
    	String userName = auth.getName();
		User user = userService.getUser(userName);
    	credentialsList.addAll(credService.getAllCredentials(user.getUserId()));
		model.addAttribute("credentials", credentialsList);
		return "redirect:/";
    	
    }
    @GetMapping("/view")
    public String viewCredential(@ModelAttribute("id") int credentialsId,Model model) {
    	model.addAttribute("credentials", credentialsList); 
		Credentials credentialObj = credService.getCredentials(credentialsId);
		model.addAttribute("credential", credentialObj);
		return "redirect:/";
    	
    }
}
