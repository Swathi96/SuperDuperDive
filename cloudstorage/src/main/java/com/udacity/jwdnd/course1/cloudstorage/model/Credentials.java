package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {

	private String url;
	private String username;
	private String password;
	private Integer credentialid;
	private String key;
	private Integer userId;
	
	public Credentials(Integer credentialid, String userName, String key, String password, String url,Integer userId) {
        this.userId = userId;
        this.credentialid = credentialid;
        this.username = userName;
        this.password = password;
        this.key = key;
        this.url = url;
    } 
	public Credentials() {}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCredentialid() {
		return credentialid;
	}
	public void setCredentialid(Integer credentialid) {
		this.credentialid = credentialid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
