package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Allows us to create Book objects.
 */
public class Book {
	
	private int ID;
	private String title;
	private String author;
	private String genre;
	private LocalDate checkOutDate;
	private boolean checkedOut;
	
	
	//Constructors
	public Book(int ID, String title, String author, String genre, LocalDate checkOutDate, boolean checkedOut) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkOutDate = checkOutDate;
        this.checkedOut = checkedOut;
        
    }
	
	//Setters
	public void setId(int id) {
		this.ID = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.checkOutDate = lastCheckOut;
	}
	
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	//Getters
	public int getId() {
		return this.ID;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public LocalDate getLastCheckOut() {
		return this.checkOutDate;
	}
	
	public boolean isCheckedOut() {
		return this.checkedOut;
	}
	
	/**
	 * Allows the correct displaying of Books when printed to the screen.
	 */
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		
		String result = this.title.toUpperCase() + " BY " + this.author.toUpperCase();
		return result;
	}
	
	/**
	 * Calculates the fee owed for a book.
	 * @return - amount owed
	 */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		LocalDate today = LocalDate.of(2023, 9, 15);
		LocalDate checkOutDate = getLastCheckOut();
		
		int daysOver = (int) ChronoUnit.DAYS.between(checkOutDate, today);
		float result = 0;
		
		if ( daysOver >= 31 ) {
			result = (float) (10 + 1.5 * (daysOver - 31));
		}
		
		return result;
	}
}
