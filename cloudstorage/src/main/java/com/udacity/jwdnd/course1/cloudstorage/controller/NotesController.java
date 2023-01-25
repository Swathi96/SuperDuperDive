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
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@RequestMapping("/notes")
@Controller
public class NotesController {

	NotesService noteService;
	UserService userService;
	List<Note> noteList = new ArrayList<Note>();

	public NotesController(NotesService noteService, UserService userService) {
		this.noteService = noteService;
		this.userService = userService;
	}


	@GetMapping()
	public String viewNotes(Authentication auth, Model model) {
		try {
			String userName = auth.getName();
			User user = userService.getUser(userName);
			model.addAttribute("notes", noteService.getNotes(user.getUserId()));
		} catch (Exception e) {
			model.addAttribute("failureMessage",
					"Something went wrong, your notes were not found. Please try again!");
		}
		return "redirect:/home";
	}

	@PostMapping()
	public String addNotes(@ModelAttribute Note note, Authentication auth, Model model) {
		try {
			String userName = auth.getName();
			User user = userService.getUser(userName);
			
			if (note.getNoteid() != null) {
				note.setUserId(user.getUserId());
				noteService.update(note);
				model.addAttribute("successMessage", "Note updated successfully");
			} else {
				Note newNote = new Note(null, note.getTitle(), note.getDescription(), user.getUserId());
				newNote.setUserId(user.getUserId());
				noteService.storeNotes(newNote);
				model.addAttribute("successMessage", "Note created successfully");
			}
			model.addAttribute("notes", noteService.getNotes(user.getUserId()));
		} catch (Exception e) {

			model.addAttribute("failureMessage",
					"Something went wrong, your changes were not saved. Please try again!");
		}
		return "result";
	}

	@GetMapping("/delete")
	public String deleteNotes(Authentication auth, @RequestParam("id") Integer noteid, Model model) {
		try {
			String userName = auth.getName();

			User user = userService.getUser(userName);
			noteService.deleteNote(noteid);
			model.addAttribute("notes", noteService.getNotes(user.getUserId()));
			model.addAttribute("noteError", "Success");
			model.addAttribute("successMessage", "Note deleted successfully");
		} catch (Exception e) {

			model.addAttribute("failureMessage",
					"Something went wrong, your changes were not saved. Please try again!");
		}
		return "result";
	}



	@GetMapping("/view")
	public String viewNote(@RequestParam("id") Integer noteid, Model model) {

		model.addAttribute("note", noteService.getNote(noteid));
		model.addAttribute("noteError", "Success");
		return "redirect:/home";

	}
}
