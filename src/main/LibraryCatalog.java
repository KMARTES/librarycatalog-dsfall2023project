package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

/**
 * This class creates a Library Catalog object that contains a list of users and books.
 * It lets you control the availability of the books statuses and lets us see which users owe money.
 * 
 * This was implemented using ArrayLists due to it being simpler to use and easier to code (for me).
 */
public class LibraryCatalog {
	
	private List<Book> catalog;
	private List<User> userList;
	
	//Constructor	
	/**
	 * This constructor creates a new Library Catalog with its catalog and 
	 * userList private variables initialized to data in the documents scanned.
	 * @throws IOException
	 */
	public LibraryCatalog() throws IOException {
		
		this.catalog = getBooksFromFiles();
		this.userList = getUsersFromFiles();
	}
	
	//Methods
	
	/**
	 * This method is used to scan a file and create a list for the books of Library Catalog.
	 * @return - Sets this.catalog to the recovered list of books from the scanned file.
	 * @throws IOException
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		
		BufferedReader input = new BufferedReader( new FileReader("data/catalog.csv") );
		List<Book> bookList = new ArrayList<>();
		
		String line;
		boolean firstIndex = true;
		
		while( ( line = input.readLine() ) != null ) {
			if( firstIndex ) {
				firstIndex = false;
				continue;
			}
			
			String[] sections = line.split(",");
			
			int ID = Integer.parseInt( sections[0] );
			String title = sections[1];
			String author = sections[2];
			String genre = sections[3];
			LocalDate checkOutDate = LocalDate.parse( sections[4] );
			boolean checkedOut = Boolean.parseBoolean( sections[5] );
			
			Book readBook = new Book( ID, title, author, genre, checkOutDate, checkedOut );
			bookList.add(readBook);
			
		}
		return bookList;
	}
	
	/**
	 * This method is used to scan a file and create a list for the users of Library Catalog.
	 * @return - Sets this.userList to the recovered list of users from the scanned file.
	 * @throws IOException
	 */
	private List<User> getUsersFromFiles() throws IOException {
		
		BufferedReader input =  new BufferedReader( new FileReader("data/user.csv") );
		List<User> userList = new ArrayList<>();
		
		String line;
		boolean firstIndex = true;
		
		while( (line = input.readLine()) != null ) {
			if( firstIndex ) {
				firstIndex = false;
				continue;
			}
			
			String[] sections = line.split(",");
			
			int ID = Integer.parseInt( sections[0] );
			String name = sections[1];
			
			List<Book> list = new ArrayList<>();
			
			if ( sections.length == 3 ) {
				String[] bookIDs = sections[2].replace("{", "").replace("}", "").split(" ");
				
				for( String x : bookIDs ) {
					int bookID = Integer.parseInt( x );
					
					for (Book y : getBooksFromFiles() ) {
						if ( y.getId() == bookID ) {
							list.add(y);
							break;
						}
					}
				} 
			}
			
			User readUser = new User ( ID, name, list);
			userList.add(readUser);
		}
		return userList;
	}
	
	/**
	 * Returns the list stored in private this.catalog.
	 * @return - this.catalog
	 */
	public List<Book> getBookCatalog() {
		
		return this.catalog;
	}
	
	/**
	 * Returns the list stored in private this.userList
	 * @return - this.userList
	 */
	public List<User> getUsers() {
		
		return this.userList;
	}
	
	/**
	 * Adds a book with the stated params to the catalog of Library Catalog
	 * @param title - name of the book to be added
	 * @param author - name of the author of the book
	 * @param genre - genre category of the book
	 */
	public void addBook(String title, String author, String genre) {
		
		this.catalog.add(new Book( this.catalog.size() + 1, title, author, genre, LocalDate.of(2023, 9, 15), false));
		
	}
	
	/**
	 * Removes a book with the stated param from the catalog of Library Catalog
	 * @param id - number identification of book to be removed
	 */
	public void removeBook(int id) {
		
		for( Book x : catalog ) {
			if( x.getId() == id ) {
				this.catalog.remove(x);
			}
		}
	}	
	
	/**
	 * Sets book with stated param to checked-out
	 * @param id - number identification of book to be removed
	 * @return - true if book was not checked-out and check-out was successful,
	 * false if book was already checked-out
	 */
	public boolean checkOutBook(int id) {
		
		for( Book x : this.catalog ) {
			if( x.getId() == id ) {
				if( x.isCheckedOut() == false ) {
					x.setCheckedOut(true);
					x.setLastCheckOut( LocalDate.of(2023,9,15) );
					return true;
				} else return false;
			}
		}
		return false;
	}
	
	/**
	 * Sets book with stated param to available for checking-out
	 * @param id - number identification of book to be removed
	 * @return - true if book was checked-out and was successfully returned,
	 * false if it wasn't returned
	 */
	public boolean returnBook(int id) {
		
		for( Book x : this.catalog ) {
			if( x.getId() == id ) {
				if( x.isCheckedOut() == true ) {
					x.setCheckedOut(false);
					return true;
				} else return false;
			}
		}
		return false;
	}
	
	/**
	 * Verifies if book with stated param is available for check-out
	 * @param id - number identification of book to check
	 * @return - true if book is not checked-out, false if book is checked-out
	 */
	public boolean getBookAvailability(int id) {
		
		for( Book x : this.catalog ) {
			if( x.getId() == id ) {
				if( x.isCheckedOut() ==  false ) {
					return true;
				} 
			} 
		} 
		return false;
	}
	
	/**
	 * Returns the amount of books with the stated param
	 * @param title - name of the book to count
	 * @return - quantity of books with the same title
	 */
	public int bookCount(String title) {
		
		int count = 0;
		
		for( Book x : this.catalog ) {
			if( x.getTitle().equals(title) ) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Generates a detailed report of books checked-out and fees due by users
	 * @throws IOException
	 */
	public void generateReport() throws IOException {
		
		int adventureCount = 0;
		int fictionCount = 0;
		int classicsCount = 0;
		int mysteryCount = 0;
		int sciFiCount = 0;
		int otherCount = 0;
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		for( Book x : this.catalog ) {
			switch( x.getGenre() ) {
			case "Adventure":
				adventureCount++;
				break;
			case "Fiction":
				fictionCount++;
				break;
			case "Classics":
				classicsCount++;
				break;
			case "Mystery":
				mysteryCount++;
				break;
			case "Science Fiction":
				sciFiCount++;
				break;
			default:
				otherCount++;
				break;
			}
		}
		output += "Adventure\t\t\t\t\t" + (adventureCount) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (fictionCount) + "\n";
		output += "Classics\t\t\t\t\t" + (classicsCount) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (mysteryCount) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (sciFiCount) + "\n";
		output += "Other\t\t\t\t\t\t" + (otherCount) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (this.catalog.size()) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		int count = 0;
		for( Book x : this.catalog ) {
			if( x.isCheckedOut() == true ) {
				count++;
				output += x.toString() + "\n";
			}
		}
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (count) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		
		for( User x : this.userList ) {
			double userSum = 0.0;
			
			if ( x.getCheckedOutList() != null && !x.getCheckedOutList().isEmpty() ) {
				List<Book> checkedOutList = x.getCheckedOutList();
				
				for ( Book y : checkedOutList ) {
					userSum += y.calculateFees();
				}
			}
			
			if ( userSum > 0 ) {
				output += x.getName() + "\t\t\t\t\t$" + String.format("%.2f", userSum) + "\n";
			}
		}
		
		double totalFeeSum = 0.0;
		
		for( Book x : catalog ) {
			if( x.isCheckedOut() ) {
				totalFeeSum += x.calculateFees();
			}
		}
			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + String.format("%.2f", totalFeeSum) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		String folderPath = "src/report/";
		File folder = new File(folderPath);
		
		if ( !folder.exists() ) {
			folder.mkdirs();
		}
		
		String filePath = folderPath + "report.txt";
		File file = new File(filePath);
		
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		try {
			writer.write(output);
		} finally {
			writer.close();
			fileWriter.close();
		}
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	
	/**
	 * Returns a list of all the books that satisfy the lambda equation given as param
	 * @param func - lambda function
	 * @return - list of books 
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		List<Book> filteredBooks = new ArrayList<>();
		
		for ( Book x : this.catalog ) {
			if ( func.filter(x) ) {
				filteredBooks.add(x);
			}
		}
		return filteredBooks;
	}
	
	/**
	 * Returns a list of all the books that satisfy the lambda equation given as param
	 * @param func - lambda function
	 * @return - list of books 
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		List<User> filteredUser = new ArrayList<>();
		
		for ( User y : this.userList ) {
			if ( func.filter(y) ) {
				filteredUser.add(y);
			}
		}
		return filteredUser;
	}
	
}
