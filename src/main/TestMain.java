package main;

import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import interfaces.List;

public class TestMain {

	/*
	 * You can use this method for testing. If you run it as is 
	 * you should be able to generate the same report as report/expected_report.txt
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			LocalDate date = LocalDate.of(2022, 11, 1);
			LocalDate date2 = LocalDate.of(2022, 9, 4);
			List<Book> exampleBooks = new ArrayList<>();
			List<Book> exampleBooks2 = new ArrayList<>();
			float feesDue = 0;
			
			LibraryCatalog lc = new LibraryCatalog();
			Book carosBook = new Book(lc.getBookCatalog().size() + 1, "Caro's Biography", "Kevin Martes", "Biography", date, false);
			Book kevinsBook = new Book(lc.getBookCatalog().size() + 2, "Kevin's Biography", "Carolina Carrero", "Biography", date2, false);
			List<Book> catalog = lc.getBookCatalog();
			List<User> users = lc.getUsers();
			
			lc.addBook("Caro's Biography", "Kevin Martes", "Biography");
			lc.addBook("Kevin's Biography", "Carolina Carrero", "Biography");
			
			List<User> exampleUsers = new ArrayList<>();
			
			User kevin = new User(users.size() + 1, "Kevin Martes", exampleBooks);
			exampleUsers.add(kevin);
			
			User caro = new User(users.size() + 2,"Carolina DelMar", exampleBooks);
			exampleUsers.add(caro);
			
//			exampleBooks.add(catalog.get(5));
//			exampleBooks.add(catalog.get(13));
//			exampleBooks2.add(kevinsBook);
//			exampleBooks2.add(catalog.get(15));
			
//			System.out.println(lc.checkOutBook(kevinsBook.getId()));
//			System.out.println(lc.getBookAvailability(kevinsBook.getId()));
//			
//			System.out.println(lc.checkOutBook(5));
//			System.out.println(lc.getBookAvailability(5));
//			
//			System.out.println(lc.checkOutBook(13));
//			System.out.println(lc.getBookAvailability(13));
//			
//			System.out.println(lc.checkOutBook(15));
//			System.out.println(lc.getBookAvailability(15));
			
//			users.get(2).setCheckedOutList(exampleBooks);
			users.add(kevin);
			users.add(caro);
//			
//			for (Book x : kevin.getCheckedOutList() ) {			
//				float sum = 0;
//				sum += x.calculateFees();
//				System.out.println("Kevin's fee: " + sum);
//			}
//			
//			for (Book x : users.get(2).getCheckedOutList() ) {			
//				float sum = 0;
//				sum += x.calculateFees();
//				System.out.println("Jane Doe's fee: " + sum);
//			}
//			
//			for (Book x : caro.getCheckedOutList() ) {			
//				float sum = 0;
//				sum += x.calculateFees();
//				System.out.println("Caro's fee: " + sum);
//			}
			
			for ( User x : users ) {
				System.out.println(x.getId() + " | " + x.getName() + " | " + x.getCheckedOutList() + "\n");
			}
			
			for ( Book x : catalog) {
					//System.out.println(x);
					System.out.println(x.getId() + " | " + x.getTitle() + " | " + x.getAuthor() + " | " + x.getGenre() + " | " + x.getLastCheckOut() + " | " + x.isCheckedOut() + "\n");
			}
			
			lc.generateReport();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
