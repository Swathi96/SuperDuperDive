package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileModel {

	private String filename, contentType;
	private long fileSize;
	private byte[] filedata;
	private Integer userId;
	private Integer fileId;

	public FileModel(Integer fileId, String filename, String contentType, long filesize, int userId,
			byte[] filedata) {
		this.filedata = filedata;
		this.filename = filename;
		this.contentType = contentType;
		this.userId = userId;
		this.setFileId(fileId);
	}

	public FileModel() {}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContenttype() {
		return contentType;
	}

	public void setContenttype(String contenttype) {
		this.contentType = contenttype;
	}

	public long getFilesize() {
		return fileSize;
	}

	public void setFilesize(long filesize) {
		this.fileSize = filesize;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
}
