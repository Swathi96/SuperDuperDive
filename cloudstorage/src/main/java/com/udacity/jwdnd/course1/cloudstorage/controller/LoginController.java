
package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

	private UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

    @GetMapping()
    public String loginView() {
        return "login";
    }

	@PostMapping()
	public String login(@ModelAttribute("user") User user, Model model) {
		String loginError = null;
		try {
			User userLoggedIn = userService.getUser(user.getUsername());
			if (userLoggedIn == null) {
				loginError = "User not found in the data base. please sign in";
				model.addAttribute("loginError", loginError);
				return "login";

			} else {
				return "home";
			}

		} catch (Exception e) {
			loginError = "Some problem, please try to sign up";
			model.addAttribute("loginError", loginError);
			return "login";
		}
	}
}