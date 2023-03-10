package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller()
@RequestMapping("/signup")
public class SignupController {

	private final UserService userService;

	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String signupView(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping()
	public String signupUser(@ModelAttribute("user") User user, Model model,RedirectAttributes redirectAttributes) {
		String signupError = null;

		if (!userService.isUsernameAvailable(user.getUsername())) {
			signupError = "The username already exists. Login if account is already created";
			model.addAttribute("signupError", signupError);
			return "signup";
		}

		if (signupError == null) {
			int rowsAdded = userService.createUser(user);
			if (rowsAdded < 0) {
				signupError = "There was an error signing you up. Please try again.";
				model.addAttribute("signupError", signupError);
				return "signup";
			}
		}

		model.addAttribute("signupSuccess", true);
		model.addAttribute("successMessage","Sign-in successful, please login");
		redirectAttributes.addFlashAttribute("successMessage","Sign-in successful, please login");
		return "redirect:/login";
	}
}