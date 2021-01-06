package com.example.test;

/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description bean
 */
public class News {

	private String title;
	private String author;
	private String date;
	private String image;
	private String uniqueKey;
	private String url;

	public News() {
	}

	public News(String title, String author, String date, String image,
			String uniqueKey, String url) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
		this.image = image;
		this.uniqueKey = uniqueKey;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
