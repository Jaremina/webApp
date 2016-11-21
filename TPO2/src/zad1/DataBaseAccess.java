package zad1;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBaseAccess extends CommandImplementation {


	private static final long serialVersionUID = 1L;

	private DataSource dataSource; // polaczenie z baza danych zapewniajace
									// pooling polaczen i separacji parametrow
									// polaczen od kodu inferfejs bazodanowego

	private String where = "";
	private boolean whereEmpty = true;

	public void init() {
		try {
			Context initialContext = new InitialContext();
			Context jndiContext = (Context) initialContext.lookup("java:comp/env");
			String dbName = (String) getParameter("dbName");
			dataSource = (DataSource) jndiContext.lookup(dbName);
		} catch (NamingException exc) {
			setStatusCode(1);
		}

	}

	public void execute() {

		clearResult();
		setStatusCode(0);
		Connection connection = null;
		String command = "SELECT pozycje.ISBN, autor.NAME, pozycje.TYTUL, wydawca.NAME, pozycje.ROK, pozycje.CENA FROM pozycje "
				+ "INNER JOIN autor ON pozycje.AUTID = autor.AUTID "
				+ "INNER JOIN wydawca ON pozycje.WYDID  = wydawca.WYDID ";
		String where = "";

		try {
			synchronized (this) {
				connection = dataSource.getConnection();
			}

			Statement statement = connection.createStatement();

			where = prepareWhere();

			System.out.println(command + where);

			ResultSet resultSet = statement.executeQuery(command + where);

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			int columCount = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				String result = "";
				for (int i = 1; i <= columCount; i++)
					result += resultSet.getObject(i) + " ";
				addResult(result);
			}
			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException exc) {
			setStatusCode(2);
			exc.printStackTrace();
		}

	}

	private String prepareWhere() {
		setWhere("");
		setWhereEmpty(true);

		String author = (String) getParameter("author");
		String title = (String) getParameter("title");
		String ISBN = (String) getParameter("ISBN");
		String publisher = (String) getParameter("publisher");

		String yearFrom = (String) getParameter("yearFrom");
		String yearTo = (String) getParameter("yearTo");
		String priceFrom = (String) getParameter("priceFrom");
		String priceTo = (String) getParameter("priceTo");

		
		if (author != null && !author.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("autor.NAME = '" + author + "' ");
		}
		
		if (title != null && !title.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("pozycje.TYTUL = '" + title + "' ");
		}

		if (ISBN != null && !ISBN.isEmpty() ) {
			checkIfWhereEmpty();
			addWhere("pozycje.ISBN = '" + ISBN + "' ");
		}

		if (publisher != null && !publisher.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("wydawca.NAME= '" + publisher + "' ");
		}

		if (yearFrom != null && !yearFrom.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("pozycje.ROK >= " + Integer.parseInt(yearFrom) + " ");
		}
		if (yearTo != null && !yearTo.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("pozycje.ROK <= " + Integer.parseInt(yearTo) + " ");
		}
		if (priceFrom != null && !priceFrom.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("pozycje.CENA >= " + Double.parseDouble(priceFrom) + " ");
		}
		if (priceTo != null && !priceTo.isEmpty()) {
			checkIfWhereEmpty();
			addWhere("pozycje.CENA <= " + Double.parseDouble(priceTo) + " ");
		}

		return where;
	}

	private void checkIfWhereEmpty() {
		if (isWhereEmpty()) {
			setWhere("WHERE ");
			setWhereEmpty(false);
		} else {
			addWhere("AND ");
		}
	}

	private void setWhere(String where) {
		this.where = where;
	}

	private void addWhere(String addWhere) {
		this.where += addWhere;
	}

	private boolean isWhereEmpty() {
		return whereEmpty;
	}

	private void setWhereEmpty(boolean whereEmpty) {
		this.whereEmpty = whereEmpty;
	}

}
