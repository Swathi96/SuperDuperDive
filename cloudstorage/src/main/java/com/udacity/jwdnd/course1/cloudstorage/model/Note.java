package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

	private String title;
	private String description;
	private Integer userId;
	private Integer noteid;

	public Note(Integer noteid, String title, String description, Integer userId) {
	
		this.title = title;
		this.userId = userId;
		this.description = description;
		this.noteid = noteid;
	}
	public Note() {
		
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getNoteid() {
		return noteid;
	}

	public void setNoteid(Integer noteid) {
		this.noteid = noteid;
	}

}
