package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@RequestMapping("/file")
@Controller
public class FileController {

	FileService fileService;
	UserService userService;
	UserMapper userMapper;
	private final List<FileModel> fileList = new ArrayList<>();

	public FileController(FileService fileService, UserMapper userMapper) {
		this.fileService = fileService;
		this.userMapper = userMapper;
	}

	@GetMapping("/files")
	public String load(Authentication authentication, Model model) {

		String username = authentication.getName();
		int userid = userMapper.getUser(username).getUserId();
		fileList.addAll(fileService.getFiles(userid));
		model.addAttribute("files", fileList);
		return "home";
	}

	@GetMapping(value = "/view")
	public ResponseEntity<ByteArrayResource> viewFile(@RequestParam("id") int fileId) {
		FileModel file = fileService.getFile(fileId);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment:fileName=" + file.getFilename())
				.contentType(MediaType.parseMediaType(file.getContenttype()))
				.body(new ByteArrayResource(file.getFiledata()));
	}

	@GetMapping(value = "/delete")
	public String deleteFile(@RequestParam("id") Integer id, Model model, Authentication authentication) {
		try {
		fileService.deleteFile(id);
		String username = authentication.getName();
		int userid = userMapper.getUser(username).getUserId();
		model.addAttribute("files", fileService.getFiles(userid));
		model.addAttribute("successMessage", "File deleted successfully");
		} catch (Exception e) {
			model.addAttribute("failureMessage",
					"Unable to delete selected file. Please try again!");
		}
		return "result";
	}

	@PostMapping()
	public String postFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model,
			Authentication authentication) {
		FileModel file;
		try {
			String username = authentication.getName();
			int userid = userMapper.getUser(username).getUserId();
			file = new FileModel(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
					fileUpload.getSize(), userid, fileUpload.getBytes());
			this.fileService.storeFile(file);
			model.addAttribute("files", fileService.getFiles(userid));
			model.addAttribute("successMessage", "File updated successfully");
			
		} catch (Exception e) {
			model.addAttribute("failureMessage",
					"Unable to add file. Please try again!");
		}
		return "result";

	}
}
