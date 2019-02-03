package org.launchcode.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}

	@NotNull
	@Size(min=1, max=15)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Category() {
		
	}
	
	public Category(String name) {
		this.name = name;
	}
	
	@OneToMany
	@JoinColumn(name="category_id")
	private List<Cheese> cheeses = new ArrayList<>();

	public List<Cheese> getCheeses() {
		return cheeses;
	}

	public void setCheeses(List<Cheese> cheeses) {
		this.cheeses = cheeses;
	}
	
	
}
