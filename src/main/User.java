package main;

import interfaces.List;

/**
 * Allows us to create User objects.
 */
public class User {
	
	private int ID;
	private String name;
	private List<Book> list;
	
	//Constructors
	public User( int ID, String name, List<Book> list ) {
		this.ID = ID;
		this.name = name;
		this.list = list;
	}
	
	//Setters
	public void setId(int id) {
		this.ID = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.list = checkedOutList;
	}
	//Getters
	public int getId() {
		return this.ID;
	}

	public String getName() {
		return this.name;
	}

	public List<Book> getCheckedOutList() {
		return this.list;
	}
	
	/**
	 * Allows the correct displaying of the User when printed to screen.
	 */
	public String toString() {
		return ID + " " + name + list;
		
	}

}
