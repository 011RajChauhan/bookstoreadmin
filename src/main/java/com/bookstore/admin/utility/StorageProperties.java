package com.bookstore.admin.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "C:\\My Space\\java_2020_learning\\Projects\\bookstore-admin\\src\\main\\resources\\static\\image\\books";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
