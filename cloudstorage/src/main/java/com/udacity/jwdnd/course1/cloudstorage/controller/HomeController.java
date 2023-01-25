package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	FileService fileService;
	CredentialsService credentialService;
	NotesService noteService;
	UserService userService;
	EncryptionService encryptionService;

	public HomeController(FileService fileService, NotesService notesService, UserService userService,
			CredentialsService credentialService,EncryptionService encryptionService) {
		this.fileService = fileService;
		this.noteService = notesService;
		this.userService = userService;
		this.encryptionService =encryptionService;
		this.credentialService = credentialService;
	}

	@GetMapping
	public String displayHome(Authentication auth, 
			Model model) {

		if (userService.getUser(auth.getName()) != null && auth.isAuthenticated()) {
			int id = userService.getUser(auth.getName()).getUserId();
				model.addAttribute("files", fileService.getFiles(id));
				model.addAttribute("notes", noteService.getNotes(id));
				  model.addAttribute("encryptionService", encryptionService);
				model.addAttribute("credentials", credentialService.getAllCredentials(id));
			return "home";
		}

		return "login";
	}
	
	
}