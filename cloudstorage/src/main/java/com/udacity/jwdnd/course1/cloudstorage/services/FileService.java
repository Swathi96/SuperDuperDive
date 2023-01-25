package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;

@Service
public class FileService {

	FileMapper fileMapper;

	public FileService(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	public int storeFile(FileModel file) {

		return fileMapper.insert(file);
	}

	public List<FileModel> getFiles(Integer userId) {
		return fileMapper.viewFiles(userId);
	}

	public FileModel getFile(Integer fileId) {
		return fileMapper.viewFile(fileId);
	}

	public int deleteFile(Integer fileId) {
		return fileMapper.delete(fileId);
	}
}
