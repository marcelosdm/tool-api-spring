package com.marcelo.tools.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "tools")
public class Tool {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private String category;

	@NotBlank
	private String tags;

	public Tool() {
		super();
	}

	public Tool(Long id, @NotBlank String name, @NotBlank String description, @NotBlank String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
