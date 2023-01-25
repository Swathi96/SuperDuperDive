package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
@Service
public class NotesService {
	NotesMapper notesMapper;
	
	public NotesService(NotesMapper notesMapper) {
		this.notesMapper=notesMapper;
	}
	public int storeNotes(Note notes) {
	
		return notesMapper.insert(new Note(null, notes.getTitle(), notes.getDescription(), notes.getUserId()));
	}

	public int update(Note notes) {
		
		return notesMapper.update(new Note(notes.getNoteid(), notes.getTitle(), notes.getDescription(), notes.getUserId()));
	}

	public List<Note> getNotes(Integer userId) {
		
		
		List<Note> notes =  notesMapper.viewAllNotes(userId);
		return notes;
	}

	public Note getNote(Integer noteid) {
		return notesMapper.viewNotes(noteid);
	}

	public int deleteNote(Integer noteid) {
		return notesMapper.delete(noteid);
	}
	
	public boolean isNotesAvailable(String title) {
	    return notesMapper.getNote(title) == null;
	}
}
