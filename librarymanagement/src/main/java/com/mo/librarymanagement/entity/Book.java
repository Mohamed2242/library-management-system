package com.mo.librarymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Author is required.")
    private String author;

    @Min(value = 1440, message = "Publication year should be greater than or equal to 1440.")
    @Max(value = 2024, message = "Publication year should not be in the future.")
    private int publicationYear;

    @NotBlank(message = "ISBN is required.")
    //@Pattern(regexp = "\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d{1,3}", message = "ISBN format should be 'XXX-X-XXXX-XXXX-X'.")
    private String isbn;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}