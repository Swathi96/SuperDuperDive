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
	public String loginView(Model model, Authentication auth) {
		String userName = auth.getName();
		User user = userService.getUser(userName);
		model.addAttribute("credentials", credService.getAllCredentials(user.getUserId()));

		return "home";
	}

	@PostMapping()
	public String addCredentials(@ModelAttribute("credential") Credentials credentials, Authentication auth,
			Model model) {
		String userName = auth.getName();
		boolean isCredentialAdded = false;
		try {
			User user = userService.getUser(userName);

			if (credentials.getCredentialid() != null) {
				credentials.setUserId(user.getUserId());
				isCredentialAdded = credService.update(credentials);
				if (isCredentialAdded) {
					model.addAttribute("successMessage", "Credential updated successfully");
				} else {
					model.addAttribute("failureMessage",
							"Unable to make save changes on credentials. Please try again!");
				}
			} else {
				isCredentialAdded = credService.storeCredentials(credentials, user);
				if (isCredentialAdded) {
					model.addAttribute("successMessage", "Credential created successfully");
				} else {
					model.addAttribute("failureMessage",
							"Unable to make save changes on credentials. Please try again!");
				}
			}
			model.addAttribute("credentials", credService.getAllCredentials(user.getUserId()));
		} catch (

		Exception e) {

			model.addAttribute("failureMessage", "Unable to make save changes on credentials. Please try again!");
		}
		return "result";

	}

	@GetMapping("/delete")
	public String deleteCredentials(Authentication auth, @ModelAttribute("id") int credentialsId, Model model) {

		try {
			String userName = auth.getName();
			boolean isCredDeleted = false;
			User user = userService.getUser(userName);
			isCredDeleted = credService.deleteCredential(credentialsId);
			if (isCredDeleted) {
				model.addAttribute("successMessage", "Credential deleted successfully");
			} else {

				model.addAttribute("failureMessage", "Unable to delete credential. Please try again!");
			}
			model.addAttribute("credentials", credService.getAllCredentials(user.getUserId()));

		} catch (Exception e) {

			model.addAttribute("failureMessage", "Unable to delete credentials. Please try again!");
		}
		return "result";

	}

	@GetMapping("/viewAll")
	public String viewCredentials(Authentication auth, Model model) {
		try {
			String userName = auth.getName();
			User user = userService.getUser(userName);
			credentialsList.addAll(credService.getAllCredentials(user.getUserId()));
			model.addAttribute("credentials", credentialsList);
		} catch (Exception e) {

			model.addAttribute("failureMessage",
					"Sorry, Unable to show all the credentials stored by you. Please try again!");

		}
		return "redirect:/";

	}

	@GetMapping("/view")
	public String viewCredential(@ModelAttribute("id") int credentialsId, Model model) {
		try {
			model.addAttribute("credentials", credentialsList);
			Credentials credentialObj = credService.getCredentials(credentialsId);
			model.addAttribute("credential", credentialObj);
		} catch (Exception e) {

			model.addAttribute("failureMessage", "Unable to view selected Credential. Please try again!");
		}
		return "redirect:/";

	}
}
