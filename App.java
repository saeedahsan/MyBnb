import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/";

    public static void main(String[] args) throws Exception {
		Class.forName(dbClassName);
		//Database credentials - Change this to locally configured MySQL username and password
		final String USER = "root";
		final String PASS = "";

        try {
            		//Establish connection with MySQL
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			
			//Create database
			Statement stmt = conn.createStatement();
			String dbName = "mybnb";
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
			stmt.close();
			conn.close();

			//Connect to database
			conn = DriverManager.getConnection(CONNECTION + dbName,USER,PASS);

			//Create tables
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Host " +
						 "(sin INTEGER, " +
						 " fname VARCHAR(255), " +
						 " lname VARCHAR(255), " +
						 " birth_date DATE, " +
						 " postal_code INTEGER, " +
						 " city VARCHAR(255), " +
						 " country VARCHAR(255), " +
						 " occupation VARCHAR(255), " +
						 " password VARCHAR(255), " +
						 " PRIMARY KEY ( sin ))";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS Renter " +
						 "(sin INTEGER, " +
						 " fname VARCHAR(255), " +
						 " lname VARCHAR(255), " +
						 " birth_date DATE, " +
						 " postal_code INTEGER, " +
						 " city VARCHAR(255), " +
						 " country VARCHAR(255), " +
						 " occupation VARCHAR(255), " +
						 " password VARCHAR(255), " +
						 " credit_card VARCHAR(255), " +
						 " PRIMARY KEY ( sin ))";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS Listing " +
						 "(lid INTEGER AUTO_INCREMENT, " +
						 " sin INTEGER, " +
						 " name VARCHAR(255), " +
						 " latitude FLOAT, " +
						 " longitude FLOAT, " +
						 " postal_code INTEGER, " +
						 " city VARCHAR(255), " +
						 " country VARCHAR(255), " +
						 " ltype VARCHAR(255), " +
						 " PRIMARY KEY ( lid ), " +
						 " FOREIGN KEY ( sin ) REFERENCES Host(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS Amenity " +
						 "(aname VARCHAR(255), " +
						 " PRIMARY KEY ( aname ))";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS ListingAmenity " +
						 "(lid INTEGER, " +
						 " aname VARCHAR(255), " +
						 " PRIMARY KEY ( lid, aname ), " +
						 " FOREIGN KEY ( lid ) REFERENCES Listing(lid) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( aname ) REFERENCES Amenity(aname) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS Availability" +
						 "(lid INTEGER, " +
						 " date DATE, " +
						 " price REAL, " +
						 " available BOOLEAN, " +
						 " PRIMARY KEY ( lid, date ), " +
						 " FOREIGN KEY ( lid ) REFERENCES Listing(lid) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS Booking " +
						 "(bid INTEGER AUTO_INCREMENT, " +
						 " sin INTEGER, " +
						 " lid INTEGER, " +
						 " status VARCHAR(255), " +
						 " start_date DATE, " +
						 " end_date DATE, " +
						 " PRIMARY KEY ( bid ), " +
						 " FOREIGN KEY ( sin ) REFERENCES Renter(sin) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( lid ) REFERENCES Listing(lid) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS HostRating " +
						"(hrating_id INTEGER AUTO_INCREMENT, " +
						 " host_sin INTEGER, " +
						 " renter_sin INTEGER, " +
						 " rating INTEGER, " +
						 " PRIMARY KEY ( hrating_id ), " +
						 " FOREIGN KEY ( host_sin ) REFERENCES Host(sin) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS HostComment " +
						"(hcomment_id INTEGER AUTO_INCREMENT, " +
						 " host_sin INTEGER, " +
						 " renter_sin INTEGER, " +
						 " comment TEXT, " +
						 " PRIMARY KEY ( hcomment_id ), " +
						 " FOREIGN KEY ( host_sin ) REFERENCES Host(sin) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS RenterRating " +
						"(rrating_id INTEGER AUTO_INCREMENT, " +
						 " host_sin INTEGER, " +
						 " renter_sin INTEGER, " +
						 " rating INTEGER, " +
						 " PRIMARY KEY ( rrating_id ), " +
						 " FOREIGN KEY ( host_sin ) REFERENCES Host(sin) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS RenterComment " +
						"(rcomment_id INTEGER AUTO_INCREMENT, " +
						 " host_sin INTEGER, " +
						 " renter_sin INTEGER, " +
						 " comment TEXT, " +
						 " PRIMARY KEY ( rcomment_id ), " +
						 " FOREIGN KEY ( host_sin ) REFERENCES Host(sin) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS ListingRating" +
						"(lrating_id INTEGER AUTO_INCREMENT, " +
						 " lid INTEGER, " +
						 " renter_sin INTEGER, " +
						 " rating INTEGER, " +
						 " PRIMARY KEY ( lrating_id ), " +
						 " FOREIGN KEY ( lid ) REFERENCES Listing(lid) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			stmt = conn.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS ListingComment" +
						"(lcomment_id INTEGER AUTO_INCREMENT, " +
						 " lid INTEGER, " +
						 " renter_sin INTEGER, " +
						 " comment TEXT, " +
						 " PRIMARY KEY ( lcomment_id ), " +
						 " FOREIGN KEY ( lid ) REFERENCES Listing(lid) ON DELETE CASCADE, " +
						 " FOREIGN KEY ( renter_sin ) REFERENCES Renter(sin) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			stmt.close();

			//Insert amenities
			stmt = conn.createStatement();
			sql = "INSERT IGNORE INTO Amenity (aname) VALUES ('Wifi'), ('Kitchen'), ('Washer'), ('Dryer'), ('Air conditioning'), ('Heating'), ('Dedicated workspace'), ('TV'), ('Hair dryer'), ('Iron'), ('Pool'), ('Hot tub'), ('Free parking'), ('EV charger'), ('Crib'), ('Gym'), ('BBQ grill'), ('Breakfast'), ('Indoor fireplace'), ('Smoking allowed'), ('Beachfront'), ('Waterfront'), ('Smoke alarm'), ('Carbon monoxide alarm')";
			stmt.executeUpdate(sql);
			stmt.close();

			//Read user input
			Scanner cmdScanner = new Scanner(System.in);
			String userCmd = "";
			int signedInSin = -1;
			boolean signedInAsHost = false;
			System.out.println("Welcome to MyBnB! Please enter a command:");

			//Loop until user enters "exit"
			while (!userCmd.equals("exit")) {
				userCmd = cmdScanner.nextLine();
				if (userCmd.equals("create_host")) {
					createHost(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("create_renter")) {
					createRenter(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("host_sign_in")) {
					if (signedInSin == -1) {
						signedInSin = hostSignIn(conn, cmdScanner);
						signedInAsHost = true;
					}
					else {
						System.out.println("You are already signed in!");
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("renter_sign_in")) {
					if (signedInSin == -1) {
						signedInSin = renterSignIn(conn, cmdScanner);
					}
					else {
						System.out.println("You are already signed in!");
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("logout")) {
					if (signedInSin == -1) {
						System.out.println("You are not signed in!");
					}
					else {
						signedInSin = -1;
						signedInAsHost = false;
						System.out.println("You have been signed out!");
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("delete_account")) {
					if (signedInSin == -1) {
						System.out.println("You are not signed in!");
					}
					else {
						deleteAccount(conn, cmdScanner, signedInSin, signedInAsHost);
						signedInSin = -1;
						signedInAsHost = false;
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("create_listing")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to create a listing!");
					}
					else {
						createListing(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("add_availability")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to add availability!");
					}
					else {
						addAvailability(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("update_price")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to update the price!");
					}
					else {
						updatePrice(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("remove_availability")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to remove availability!");
					}
					else {
						removeAvailability(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("book")) {
					if (signedInAsHost || signedInSin == -1) {
						System.out.println("You must be signed in as a renter to book a listing!");
					}
					else {
						book(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("cancel")) {
					cancel(conn, cmdScanner, signedInSin, signedInAsHost);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rate_host")) {
					if (signedInAsHost || signedInSin == -1) {
						System.out.println("You must be signed in as a renter to rate a host!");
					}
					else {
						rateHost(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("comment_on_host")) {
					if (signedInAsHost || signedInSin == -1) {
						System.out.println("You must be signed in as a renter to comment on a host!");
					}
					else {
						commentOnHost(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rate_renter")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to rate a renter!");
					}
					else {
						rateRenter(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("comment_on_renter")) {
					if (!signedInAsHost) {
						System.out.println("You must be signed in as a host to comment on a renter!");
					}
					else {
						commentOnRenter(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rate_listing")) {
					if (signedInAsHost || signedInSin == -1) {
						System.out.println("You must be signed in as a renter to rate a listing!");
					}
					else {
						rateListing(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("comment_on_listing")) {
					if (signedInAsHost || signedInSin == -1) {
						System.out.println("You must be signed in as a renter to comment on a listing!");
					}
					else {
						commentOnListing(conn, cmdScanner, signedInSin);
					}
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("search_by_location")) {
					searchByLocation(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("search_by_postal_code")) {
					searchByPostalCode(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("search_by_address")) {
					searchByAddress(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("bookings_date_range_report")) {
					bookingsDateRangeReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("listings_per_address_report")) {
					listingsPerAddressReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rank_hosts_listings_country_report")) {
					rankHostsByListingsPerCountryReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rank_hosts_listings_city_report")) {
					rankHostsByListingsPerCityReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("possible_commercial_hosts_report")) {
					possibleCommercialHostsReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rank_renters_bookings_report")) {
					rankRentersByBookingsReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("rank_renters_bookings_city_report")) {
					rankRentersByBookingsPerCityReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("most_cancellations_report")) {
					mostCancellationsReport(conn, cmdScanner);
					System.out.println("Please enter a command:");
				}
				else if (userCmd.equals("exit")) {
					System.out.println("Goodbye!");
				}
				else {
					System.out.println("Invalid command. Please try again.");
				}
			}

			conn.close();
			cmdScanner.close();
        } catch (SQLException e) {
            System.err.println("An error occured!");
            System.out.println(e);
        }
    }

	// Methods for various functionalities
	 static void createHost(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter your SIN:");
		userCmd = cmdScanner.nextLine();
		int sin = Integer.parseInt(userCmd);
		System.out.println("Please enter your first name:");
		userCmd = cmdScanner.nextLine();
		String fname = userCmd;
		System.out.println("Please enter your last name:");
		userCmd = cmdScanner.nextLine();
		String lname = userCmd;
		System.out.println("Please enter your birth date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String birthDate = userCmd;
	        LocalDate birthDateDate = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);
	        LocalDate currentDate = LocalDate.now();
	        Period age = Period.between(birthDateDate, currentDate);
	        if (age.getYears() < 18) {
			System.out.println("You must be at least 18 years old to create an account!");
			return;
		}
		System.out.println("Please enter your postal code:");
		userCmd = cmdScanner.nextLine();
		int postalCode = Integer.parseInt(userCmd);
		System.out.println("Please enter your city:");
		userCmd = cmdScanner.nextLine();
		String city = userCmd;
		System.out.println("Please enter your country:");
		userCmd = cmdScanner.nextLine();
		String country = userCmd;
		System.out.println("Please enter your occupation:");
		userCmd = cmdScanner.nextLine();
		String occupation = userCmd;
		System.out.println("Please enter your password:");
		userCmd = cmdScanner.nextLine();
		String password = userCmd;

		String sql = " insert into Host (sin, fname, lname, birth_date, postal_code, city, country, occupation, password)"
    		+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, fname);
			preparedStmt.setString(3, lname);
			preparedStmt.setString(4, birthDate);
			preparedStmt.setInt(5, postalCode);
			preparedStmt.setString(6, city);
			preparedStmt.setString(7, country);
			preparedStmt.setString(8, occupation);
			preparedStmt.setString(9, password);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Host created successfully!");
		} catch (SQLException e) {
			System.err.println("Error creating host!");
            		System.out.println(e);
		}
	}

	static void createRenter(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter your SIN:");
		userCmd = cmdScanner.nextLine();
		int sin = Integer.parseInt(userCmd);
		System.out.println("Please enter your first name:");
		userCmd = cmdScanner.nextLine();
		String fname = userCmd;
		System.out.println("Please enter your last name:");
		userCmd = cmdScanner.nextLine();
		String lname = userCmd;
		System.out.println("Please enter your birth date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String birthDate = userCmd;
		LocalDate birthDateDate = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);
		LocalDate currentDate = LocalDate.now();
		Period age = Period.between(birthDateDate, currentDate);
		if (age.getYears() < 18) {
			System.out.println("You must be at least 18 years old to create an account!");
			return;
		}
		System.out.println("Please enter your postal code:");
		userCmd = cmdScanner.nextLine();
		int postalCode = Integer.parseInt(userCmd);
		System.out.println("Please enter your city:");
		userCmd = cmdScanner.nextLine();
		String city = userCmd;
		System.out.println("Please enter your country:");
		userCmd = cmdScanner.nextLine();
		String country = userCmd;
		System.out.println("Please enter your occupation:");
		userCmd = cmdScanner.nextLine();
		String occupation = userCmd;
		System.out.println("Please enter your password:");
		userCmd = cmdScanner.nextLine();
		String password = userCmd;
		System.out.println("Please enter your credit card number:");
		userCmd = cmdScanner.nextLine();
		String creditCard = userCmd;

		String sql = " insert into Renter (sin, fname, lname, birth_date, postal_code, city, country, occupation, password, credit_card)"
    		+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, fname);
			preparedStmt.setString(3, lname);
			preparedStmt.setString(4, birthDate);
			preparedStmt.setInt(5, postalCode);
			preparedStmt.setString(6, city);
			preparedStmt.setString(7, country);
			preparedStmt.setString(8, occupation);
			preparedStmt.setString(9, password);
			preparedStmt.setString(10, creditCard);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Renter created successfully!");
		} catch (SQLException e) {
			System.err.println("Error creating renter!");
            		System.out.println(e);
		}
	}

	static int hostSignIn(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter your SIN:");
		userCmd = cmdScanner.nextLine();
		int sin = Integer.parseInt(userCmd);
		System.out.println("Please enter your password:");
		userCmd = cmdScanner.nextLine();
		String password = userCmd;

		String sql = "SELECT * FROM Host WHERE sin = ? AND password = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, password);

			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				System.out.println("Host signed in successfully!");
				preparedStmt.close();
				return sin;
			}
			else {
				System.out.println("Invalid SIN or password. Please try again.");
				preparedStmt.close();
				return -1;
			}
		} catch (SQLException e) {
			System.err.println("Error signing in!");
			System.out.println(e);
			return -1;
		}
	}

	static int renterSignIn(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter your SIN:");
		userCmd = cmdScanner.nextLine();
		int sin = Integer.parseInt(userCmd);
		System.out.println("Please enter your password:");
		userCmd = cmdScanner.nextLine();
		String password = userCmd;

		String sql = "SELECT * FROM Renter WHERE sin = ? AND password = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, password);

			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				System.out.println("Renter signed in successfully!");
				preparedStmt.close();
				return sin;
			}
			else {
				System.out.println("Invalid SIN or password. Please try again.");
				preparedStmt.close();
				return -1;
			}
		} catch (SQLException e) {
			System.err.println("Error signing in!");
			System.out.println(e);
			return -1;
		}
	}

	static void deleteAccount(Connection conn, Scanner cmdScanner, int sin, boolean signedInAsHost) {
		String userCmd = "";
		System.out.println("Are you sure you want to delete your account? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			String userType = signedInAsHost ? "Host" : "Renter";
			String sql = "DELETE FROM " + userType + " WHERE sin = ?";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, sin);

				preparedStmt.execute();
				preparedStmt.close();
				System.out.println("User deleted successfully!");
			} catch (SQLException e) {
				System.err.println("Error deleting user!");
				System.out.println(e);
			}
		}
		else {
			System.out.println("User not deleted.");
		}
	}

	static void createListing(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the name of the listing:");
		userCmd = cmdScanner.nextLine();
		String name = userCmd;
		System.out.println("Please enter the type of listing (house, apartment, room):");
		userCmd = cmdScanner.nextLine();
		String ltype = userCmd;
		if (!ltype.equals("house") && !ltype.equals("apartment") && !ltype.equals("room")) {
			System.out.println("Invalid listing type!");
			return;
		}
		System.out.println("Please enter the latitude:");
		userCmd = cmdScanner.nextLine();
		float latitude = Float.parseFloat(userCmd);
		System.out.println("Please enter the longitude:");
		userCmd = cmdScanner.nextLine();
		float longitude = Float.parseFloat(userCmd);
		System.out.println("Please enter the postal code:");
		userCmd = cmdScanner.nextLine();
		int postalCode = Integer.parseInt(userCmd);
		System.out.println("Please enter the city:");
		userCmd = cmdScanner.nextLine();
		String city = userCmd;
		System.out.println("Please enter the country:");
		userCmd = cmdScanner.nextLine();
		String country = userCmd;
		//Suggest amenities based on type of listing. Check which amenities are common for at least 50% of listings of the same type.
		String sql = "SELECT aname FROM Amenity WHERE aname IN (SELECT aname FROM ListingAmenity WHERE lid IN (SELECT lid FROM Listing WHERE ltype = ?) GROUP BY aname HAVING COUNT(*) >= (SELECT COUNT(*) FROM Listing WHERE ltype = ?) / 2)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, ltype);
			preparedStmt.setString(2, ltype);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("Suggested amenities:");
			while (rs.next()) {
				System.out.println(rs.getString("aname"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error creating listing!");
			System.out.println(e);
		}
		System.out.println("Please enter the amenities (separated by commas):");
		userCmd = cmdScanner.nextLine();
		String[] amenities = userCmd.split(",");
		String[] allowedAmenities = {"Wifi", "Kitchen", "Washer", "Dryer", "Air conditioning", "Heating", "Dedicated workspace", "TV", "Hair dryer", "Iron", "Pool", "Hot tub", "Free parking", "EV charger", "Crib", "Gym", "BBQ grill", "Breakfast", "Indoor fireplace", "Smoking allowed", "Beachfront", "Waterfront", "Smoke alarm", "Carbon monoxide alarm"};
		for (String amenity : amenities) {
			if (!Arrays.asList(allowedAmenities).contains(amenity)) {
				System.out.println("Invalid amenity: " + amenity);
				return;
			}
		} 

		sql = " insert into Listing (sin, ltype, latitude, longitude, postal_code, city, country, name)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, ltype);
			preparedStmt.setFloat(3, latitude);
			preparedStmt.setFloat(4, longitude);
			preparedStmt.setInt(5, postalCode);
			preparedStmt.setString(6, city);
			preparedStmt.setString(7, country);
			preparedStmt.setString(8, name);

			preparedStmt.execute();
			preparedStmt.close();

			sql = "SELECT lid FROM Listing WHERE sin = ? AND ltype = ? AND latitude = ? AND longitude = ? AND postal_code = ? AND city = ? AND country = ? AND name = ?";
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setString(2, ltype);
			preparedStmt.setFloat(3, latitude);
			preparedStmt.setFloat(4, longitude);
			preparedStmt.setInt(5, postalCode);
			preparedStmt.setString(6, city);
			preparedStmt.setString(7, country);
			preparedStmt.setString(8, name);

			ResultSet rs = preparedStmt.executeQuery();
			int lid = -1;
			if (rs.next()) {
				lid = rs.getInt("lid");
			}
			preparedStmt.close();

			sql = " insert into ListingAmenity (lid, aname)"
				+ " values (?, ?)";
			for (String amenity : amenities) {
				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, lid);
				preparedStmt.setString(2, amenity);

				preparedStmt.execute();
				preparedStmt.close();
			}

			System.out.println("Listing created successfully!");
		} catch (SQLException e) {
			System.err.println("Error creating listing!");
			System.out.println(e);
		}
	}

	static void addAvailability(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		String listingType = "";
		//Check if signed in user is the host of the listing
		String sql = "SELECT * FROM Listing WHERE lid = ? AND sin = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setInt(2, sin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You are not the host of this listing!");
				preparedStmt.close();
				return;
			}
			listingType = rs.getString("ltype");
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error adding availability!");
			System.out.println(e);
		}
		System.out.println("Please enter the date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String date = userCmd;
		//Suggest price based on type of listing. Check the average price of listings of the same type.
		sql = "SELECT AVG(price) AS avg_price FROM Availability WHERE lid IN (SELECT lid FROM Listing WHERE ltype = ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, listingType);

			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				//Round to 2 decimal places
				System.out.println("Suggested price: $" + String.format("%.2f", rs.getFloat("avg_price")));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error adding availability!");
			System.out.println(e);
		}
		System.out.println("Please enter the price:");
		userCmd = cmdScanner.nextLine();
		Float price = Float.parseFloat(userCmd);

		sql = " insert into Availability (lid, date, price, available)"
			+ " values (?, ?, ?, true)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setString(2, date);
			preparedStmt.setFloat(3, price);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Availability added successfully!");
		} catch (SQLException e) {
			System.err.println("Error adding availability!");
			System.out.println(e);
		}
	}

	static void updatePrice(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		//Check if signed in user is the host of the listing
		String sql = "SELECT * FROM Listing WHERE lid = ? AND sin = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setInt(2, sin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You are not the host of this listing!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error updating price!");
			System.out.println(e);
		}
		System.out.println("Please enter the date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		//Check if the listing is available on the given date
		sql = "SELECT * FROM Availability WHERE lid = ? AND date = ? AND available = true";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setString(2, userCmd);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("This listing is not available on the given date!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error updating price!");
			System.out.println(e);
		}
		String date = userCmd;
		System.out.println("Please enter the new price:");
		userCmd = cmdScanner.nextLine();
		Float price = Float.parseFloat(userCmd);

		sql = "UPDATE Availability SET price = ? WHERE lid = ? AND date = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setFloat(1, price);
			preparedStmt.setInt(2, lid);
			preparedStmt.setString(3, date);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Price updated successfully!");
		} catch (SQLException e) {
			System.err.println("Error updating price!");
			System.out.println(e);
		}
	}

	static void removeAvailability(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		//Check if signed in user is the host of the listing
		String sql = "SELECT * FROM Listing WHERE lid = ? AND sin = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setInt(2, sin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You are not the host of this listing!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error removing availability!");
			System.out.println(e);
		}
		System.out.println("Please enter the date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String date = userCmd;
		//Check if the listing is available on the given date
		sql = "SELECT * FROM Availability WHERE lid = ? AND date = ? AND available = true";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setString(2, date);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("This listing is not available on the given date!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error removing availability!");
			System.out.println(e);
		}

		sql = "DELETE FROM Availability WHERE lid = ? AND date = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setString(2, date);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Availability removed successfully!");
		} catch (SQLException e) {
			System.err.println("Error removing availability!");
			System.out.println(e);
		}
	}

	static void book(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		System.out.println("Please enter the start date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String startDate = userCmd;
		System.out.println("Please enter the end date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String endDate = userCmd;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDateObj = LocalDate.parse(startDate, dateFormatter);
        LocalDate endDateObj = LocalDate.parse(endDate, dateFormatter);

        // Loop through the dates and check if the listing is available
        LocalDate currentDate = startDateObj;
        while (!currentDate.isAfter(endDateObj)) {
            String sql = "SELECT * FROM Availability WHERE lid = ? AND date = ? AND available = true";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, lid);
				preparedStmt.setString(2, currentDate.format(dateFormatter));

				ResultSet rs = preparedStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("This listing is not available for the given dates!");
					preparedStmt.close();
					return;
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error booking listing!");
				System.out.println(e);
				return;
			}
            currentDate = currentDate.plusDays(1); // Move to the next date
        }

		String sql = " insert into Booking (sin, lid, status, start_date, end_date)"
			+ " values (?, ?, 'booked', ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, lid);
			preparedStmt.setString(3, startDate);
			preparedStmt.setString(4, endDate);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Booking created successfully!");
		} catch (SQLException e) {
			System.err.println("Error booking listing!");
			System.out.println(e);
		}

		sql = "UPDATE Availability SET available = false WHERE lid = ? AND date >= ? AND date <= ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, lid);
			preparedStmt.setString(2, startDate);
			preparedStmt.setString(3, endDate);

			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error booking listing!");
			System.out.println(e);
		}
	}

	static void cancel(Connection conn, Scanner cmdScanner, int sin, boolean signedInAsHost) {
		String userCmd = "";
		System.out.println("Please enter the booking ID:");
		userCmd = cmdScanner.nextLine();
		int bid = Integer.parseInt(userCmd);
		//Check if signed in user is the host of the booking
		if (signedInAsHost) {
			String sql = "SELECT * FROM Booking WHERE bid = ? AND lid IN (SELECT lid FROM Listing WHERE sin = ?)";
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, bid);
				preparedStmt.setInt(2, sin);

				ResultSet rs = preparedStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("You are not the host of this booking!");
					preparedStmt.close();
					return;
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error cancelling booking!");
				System.out.println(e);
			}
		}

		else {
			String sql = "SELECT * FROM Booking WHERE bid = ? AND sin = ?";
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, bid);
				preparedStmt.setInt(2, sin);

				ResultSet rs = preparedStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("You are not the renter of this booking!");
					preparedStmt.close();
					return;
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error cancelling booking!");
				System.out.println(e);
			}
		}

		String sql = "UPDATE Booking SET status = 'cancelled' WHERE bid = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, bid);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Booking cancelled successfully!");
		} catch (SQLException e) {
			System.err.println("Error cancelling booking!");
			System.out.println(e);
		}

		sql = "UPDATE Availability SET available = true WHERE lid IN (SELECT lid FROM Booking WHERE bid = ?) AND date >= (SELECT start_date FROM Booking WHERE bid = ?) AND date <= (SELECT end_date FROM Booking WHERE bid = ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, bid);
			preparedStmt.setInt(2, bid);
			preparedStmt.setInt(3, bid);

			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error cancelling booking!");
			System.out.println(e);
		}
	}

	static void rateHost(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the host's SIN:");
		userCmd = cmdScanner.nextLine();
		int hostSin = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid IN (SELECT lid FROM Listing WHERE sin = ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, hostSin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You have not booked any of this host's listings!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error rating host!");
			System.out.println(e);
		}
		System.out.println("Please enter the rating (1-5):");
		userCmd = cmdScanner.nextLine();
		int rating = Integer.parseInt(userCmd);

		sql = " insert into HostRating (renter_sin, host_sin, rating)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, hostSin);
			preparedStmt.setInt(3, rating);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Host rated successfully!");
		} catch (SQLException e) {
			System.err.println("Error rating host!");
			System.out.println(e);
		}
	}

	static void commentOnHost(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the host's SIN:");
		userCmd = cmdScanner.nextLine();
		int hostSin = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid IN (SELECT lid FROM Listing WHERE sin = ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, hostSin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You have not booked any of this host's listings!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
		System.out.println("Please enter your comment:");
		userCmd = cmdScanner.nextLine();
		String comment = userCmd;

		sql = " insert into HostComment (renter_sin, host_sin, comment)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, hostSin);
			preparedStmt.setString(3, comment);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Comment posted successfully!");
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
	}

	static void rateRenter(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the renter's SIN:");
		userCmd = cmdScanner.nextLine();
		int renterSin = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid IN (SELECT lid FROM Listing WHERE sin = ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, renterSin);
			preparedStmt.setInt(2, sin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("The renter not booked any of your listings!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error rating renter!");
			System.out.println(e);
		}
		System.out.println("Please enter the rating (1-5):");
		userCmd = cmdScanner.nextLine();
		int rating = Integer.parseInt(userCmd);

		sql = " insert into RenterRating (host_sin, renter_sin, rating)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, renterSin);
			preparedStmt.setInt(3, rating);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Renter rated successfully!");
		} catch (SQLException e) {
			System.err.println("Error rating renter!");
			System.out.println(e);
		}
	}

	static void commentOnRenter(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the renter's SIN:");
		userCmd = cmdScanner.nextLine();
		int renterSin = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid IN (SELECT lid FROM Listing WHERE sin = ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, renterSin);
			preparedStmt.setInt(2, sin);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("The renter not booked any of your listings!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
		System.out.println("Please enter your comment:");
		userCmd = cmdScanner.nextLine();
		String comment = userCmd;

		sql = " insert into RenterComment (host_sin, renter_sin, comment)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, renterSin);
			preparedStmt.setString(3, comment);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Comment posted successfully!");
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
	}

	static void rateListing(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, lid);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You have not booked this listing!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error rating listing!");
			System.out.println(e);
		}
		System.out.println("Please enter the rating (1-5):");
		userCmd = cmdScanner.nextLine();
		int rating = Integer.parseInt(userCmd);

		sql = " insert into ListingRating (renter_sin, lid, rating)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, lid);
			preparedStmt.setInt(3, rating);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Listing rated successfully!");
		} catch (SQLException e) {
			System.err.println("Error rating listing!");
			System.out.println(e);
		}
	}

	static void commentOnListing(Connection conn, Scanner cmdScanner, int sin) {
		String userCmd = "";
		System.out.println("Please enter the listing ID:");
		userCmd = cmdScanner.nextLine();
		int lid = Integer.parseInt(userCmd);
		String sql = "SELECT * FROM Booking WHERE sin = ? AND lid = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, lid);

			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("You have not booked this listing!");
				preparedStmt.close();
				return;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
		System.out.println("Please enter your comment:");
		userCmd = cmdScanner.nextLine();
		String comment = userCmd;

		sql = " insert into ListingComment (renter_sin, lid, comment)"
			+ " values (?, ?, ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, sin);
			preparedStmt.setInt(2, lid);
			preparedStmt.setString(3, comment);

			preparedStmt.execute();
			preparedStmt.close();
			System.out.println("Comment posted successfully!");
		} catch (SQLException e) {
			System.err.println("Error posting comment!");
			System.out.println(e);
		}
	}

	static void searchByLocation(Connection conn, Scanner cmdScanner) {
		//Use latitude and longitude to search for listings within distance specified by user, and display the results in order of distance
		String userCmd = "";
		System.out.println("Please enter the latitude:");
		userCmd = cmdScanner.nextLine();
		float latitude = Float.parseFloat(userCmd);
		System.out.println("Please enter the longitude:");
		userCmd = cmdScanner.nextLine();
		float longitude = Float.parseFloat(userCmd);
		System.out.println("Please enter the distance (km):");
		userCmd = cmdScanner.nextLine();
		float distance = Float.parseFloat(userCmd);
		//Ask user if they want to rank results by price (ascending or descending)
		System.out.println("Do you want to rank results by price? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			System.out.println("Do you want to rank by ascending or descending? (a/d)");
			userCmd = cmdScanner.nextLine();
			boolean ascending = false;
			if (userCmd.equals("a")) {
				ascending = true;
			}
			else if (!userCmd.equals("d")) {
				ascending = false;
			}
			else {
				System.out.println("Invalid input!");
				return;
			}

			String sql = "SELECT * FROM Listing WHERE (latitude - ?) * (latitude - ?) + (longitude - ?) * (longitude - ?) <= ? * ? ORDER BY price";

			if (ascending) {
				sql += " ASC";
			}
			else {
				sql += " DESC";
			}

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setFloat(1, latitude);
				preparedStmt.setFloat(2, latitude);
				preparedStmt.setFloat(3, longitude);
				preparedStmt.setFloat(4, longitude);
				preparedStmt.setFloat(5, distance);
				preparedStmt.setFloat(6, distance);

				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error searching for listing!");
				System.out.println(e);
			}
			return;
		}
		else if (!userCmd.equals("n")) {
			System.out.println("Invalid input!");
			return;
		}

		String sql = "SELECT * FROM Listing WHERE (latitude - ?) * (latitude - ?) + (longitude - ?) * (longitude - ?) <= ? * ? ORDER BY (latitude - ?) * (latitude - ?) + (longitude - ?) * (longitude - ?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setFloat(1, latitude);
			preparedStmt.setFloat(2, latitude);
			preparedStmt.setFloat(3, longitude);
			preparedStmt.setFloat(4, longitude);
			preparedStmt.setFloat(5, distance);
			preparedStmt.setFloat(6, distance);
			preparedStmt.setFloat(7, latitude);
			preparedStmt.setFloat(8, latitude);
			preparedStmt.setFloat(9, longitude);
			preparedStmt.setFloat(10, longitude);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Type: " + rs.getString("ltype"));
				System.out.println("Latitude: " + rs.getFloat("latitude"));
				System.out.println("Longitude: " + rs.getFloat("longitude"));
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Country: " + rs.getString("country"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error searching for listing!");
			System.out.println(e);
		}
	}

	static void searchByPostalCode(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter the postal code:");
		userCmd = cmdScanner.nextLine();
		int postalCode = Integer.parseInt(userCmd);
		//Check if user wants to filter by range of available dates or by price
		System.out.println("Do you want to filter by range of available dates? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			System.out.println("Please enter the start date (YYYY-MM-DD):");
			userCmd = cmdScanner.nextLine();
			String startDate = userCmd;
			System.out.println("Please enter the end date (YYYY-MM-DD):");
			userCmd = cmdScanner.nextLine();
			String endDate = userCmd;
			System.out.println("Do you want to filter by price? (y/n)");
			userCmd = cmdScanner.nextLine();
			if (userCmd.equals("y")) {
				System.out.println("Please enter the minimum price:");
				userCmd = cmdScanner.nextLine();
				float minPrice = Float.parseFloat(userCmd);
				System.out.println("Please enter the maximum price:");
				userCmd = cmdScanner.nextLine();
				float maxPrice = Float.parseFloat(userCmd);

				String sql = "SELECT * FROM Listing WHERE postal_code = ? AND lid IN (SELECT lid FROM Availability WHERE date >= ? AND date <= ? AND price >= ? AND price <= ? AND available = true)";

				try {
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, postalCode);
					preparedStmt.setString(2, startDate);
					preparedStmt.setString(3, endDate);
					preparedStmt.setFloat(4, minPrice);
					preparedStmt.setFloat(5, maxPrice);

					ResultSet rs = preparedStmt.executeQuery();
					while (rs.next()) {
						System.out.println("Name: " + rs.getString("name"));
						System.out.println("Type: " + rs.getString("ltype"));
						System.out.println("Latitude: " + rs.getFloat("latitude"));
						System.out.println("Longitude: " + rs.getFloat("longitude"));
						System.out.println("Postal Code: " + rs.getInt("postal_code"));
						System.out.println("City: " + rs.getString("city"));
						System.out.println("Country: " + rs.getString("country"));
					}
					preparedStmt.close();

					preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, postalCode + 1);
					preparedStmt.setString(2, startDate);
					preparedStmt.setString(3, endDate);
					preparedStmt.setFloat(4, minPrice);
					preparedStmt.setFloat(5, maxPrice);

					rs = preparedStmt.executeQuery();
					while (rs.next()) {
						System.out.println("Name: " + rs.getString("name"));
						System.out.println("Type: " + rs.getString("ltype"));
						System.out.println("Latitude: " + rs.getFloat("latitude"));
						System.out.println("Longitude: " + rs.getFloat("longitude"));
						System.out.println("Postal Code: " + rs.getInt("postal_code"));
						System.out.println("City: " + rs.getString("city"));
						System.out.println("Country: " + rs.getString("country"));
					}
					preparedStmt.close();

					preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, postalCode - 1);
					preparedStmt.setString(2, startDate);
					preparedStmt.setString(3, endDate);
					preparedStmt.setFloat(4, minPrice);
					preparedStmt.setFloat(5, maxPrice);

					rs = preparedStmt.executeQuery();
					while (rs.next()) {
						System.out.println("Name: " + rs.getString("name"));
						System.out.println("Type: " + rs.getString("ltype"));
						System.out.println("Latitude: " + rs.getFloat("latitude"));
						System.out.println("Longitude: " + rs.getFloat("longitude"));
						System.out.println("Postal Code: " + rs.getInt("postal_code"));
						System.out.println("City: " + rs.getString("city"));
						System.out.println("Country: " + rs.getString("country"));
					}
					preparedStmt.close();
				} catch (SQLException e) {
					System.err.println("Error searching for listing!");
					System.out.println(e);
				}
				return;
			}
			else if (!userCmd.equals("n")) {
				System.out.println("Invalid input!");
				return;
			}

			String sql = "SELECT * FROM Listing WHERE postal_code = ? AND lid IN (SELECT lid FROM Availability WHERE date >= ? AND date <= ? AND available = true)";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode);
				preparedStmt.setString(2, startDate);
				preparedStmt.setString(3, endDate);

				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();

				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode + 1);
				preparedStmt.setString(2, startDate);
				preparedStmt.setString(3, endDate);

				rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();

				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode - 1);
				preparedStmt.setString(2, startDate);
				preparedStmt.setString(3, endDate);

				rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error searching for listing!");
				System.out.println(e);
			}
			return;
		}
		else if (!userCmd.equals("n")) {
			System.out.println("Invalid input!");
			return;
		}

		System.out.println("Do you want to filter by price? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			System.out.println("Please enter the minimum price:");
			userCmd = cmdScanner.nextLine();
			float minPrice = Float.parseFloat(userCmd);
			System.out.println("Please enter the maximum price:");
			userCmd = cmdScanner.nextLine();
			float maxPrice = Float.parseFloat(userCmd);

			String sql = "SELECT * FROM Listing WHERE postal_code = ? AND lid IN (SELECT lid FROM Availability WHERE price >= ? AND price <= ? AND available = true)";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode);
				preparedStmt.setFloat(2, minPrice);
				preparedStmt.setFloat(3, maxPrice);

				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();

				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode + 1);
				preparedStmt.setFloat(2, minPrice);
				preparedStmt.setFloat(3, maxPrice);

				rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();

				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode - 1);
				preparedStmt.setFloat(2, minPrice);
				preparedStmt.setFloat(3, maxPrice);
				
				rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error searching for listing!");
				System.out.println(e);
			}
			return;
		}
		else if (!userCmd.equals("n")) {
			System.out.println("Invalid input!");
			return;
		}

		String sql = "SELECT * FROM Listing WHERE postal_code = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, postalCode);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Type: " + rs.getString("ltype"));
				System.out.println("Latitude: " + rs.getFloat("latitude"));
				System.out.println("Longitude: " + rs.getFloat("longitude"));
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Country: " + rs.getString("country"));
			}
			preparedStmt.close();

			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, postalCode + 1);

			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Type: " + rs.getString("ltype"));
				System.out.println("Latitude: " + rs.getFloat("latitude"));
				System.out.println("Longitude: " + rs.getFloat("longitude"));
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Country: " + rs.getString("country"));
			}
			preparedStmt.close();

			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, postalCode - 1);

			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Type: " + rs.getString("ltype"));
				System.out.println("Latitude: " + rs.getFloat("latitude"));
				System.out.println("Longitude: " + rs.getFloat("longitude"));
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Country: " + rs.getString("country"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error searching for listing!");
			System.out.println(e);
		}
	}

	static void searchByAddress(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter the postal code:");
		userCmd = cmdScanner.nextLine();
		int postalCode = Integer.parseInt(userCmd);
		System.out.println("Please enter the city:");
		userCmd = cmdScanner.nextLine();
		String city = userCmd;
		System.out.println("Please enter the country:");
		userCmd = cmdScanner.nextLine();
		String country = userCmd;
		//Check if user wants to filter by range of available dates or by price
		System.out.println("Do you want to filter by range of available dates? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			System.out.println("Please enter the start date (YYYY-MM-DD):");
			userCmd = cmdScanner.nextLine();
			String startDate = userCmd;
			System.out.println("Please enter the end date (YYYY-MM-DD):");
			userCmd = cmdScanner.nextLine();
			String endDate = userCmd;
			System.out.println("Do you want to filter by price? (y/n)");
			userCmd = cmdScanner.nextLine();
			if (userCmd.equals("y")) {
				System.out.println("Please enter the minimum price:");
				userCmd = cmdScanner.nextLine();
				float minPrice = Float.parseFloat(userCmd);
				System.out.println("Please enter the maximum price:");
				userCmd = cmdScanner.nextLine();
				float maxPrice = Float.parseFloat(userCmd);

				String sql = "SELECT * FROM Listing WHERE postal_code = ? AND city = ? AND country = ? AND lid IN (SELECT lid FROM Availability WHERE date >= ? AND date <= ? AND price >= ? AND price <= ? AND available = true)";

				try {
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, postalCode);
					preparedStmt.setString(2, city);
					preparedStmt.setString(3, country);
					preparedStmt.setString(4, startDate);
					preparedStmt.setString(5, endDate);
					preparedStmt.setFloat(6, minPrice);
					preparedStmt.setFloat(7, maxPrice);

					ResultSet rs = preparedStmt.executeQuery();
					while (rs.next()) {
						System.out.println("Name: " + rs.getString("name"));
						System.out.println("Type: " + rs.getString("ltype"));
						System.out.println("Latitude: " + rs.getFloat("latitude"));
						System.out.println("Longitude: " + rs.getFloat("longitude"));
						System.out.println("Postal Code: " + rs.getInt("postal_code"));
						System.out.println("City: " + rs.getString("city"));
						System.out.println("Country: " + rs.getString("country"));
					}
					preparedStmt.close();
				} catch (SQLException e) {
					System.err.println("Error searching for listing!");
					System.out.println(e);
				}
			}
			else if (!userCmd.equals("n")) {
				System.out.println("Invalid input!");
				return;
			}

			String sql = "SELECT * FROM Listing WHERE postal_code = ? AND city = ? AND country = ? AND lid IN (SELECT lid FROM Availability WHERE date >= ? AND date <= ? AND available = true)";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode);
				preparedStmt.setString(2, city);
				preparedStmt.setString(3, country);
				preparedStmt.setString(4, startDate);
				preparedStmt.setString(5, endDate);

				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error searching for listing!");
				System.out.println(e);
			}
		}
		else if (!userCmd.equals("n")) {
			System.out.println("Invalid input!");
			return;
		}

		System.out.println("Do you want to filter by price? (y/n)");
		userCmd = cmdScanner.nextLine();
		if (userCmd.equals("y")) {
			System.out.println("Please enter the minimum price:");
			userCmd = cmdScanner.nextLine();
			float minPrice = Float.parseFloat(userCmd);
			System.out.println("Please enter the maximum price:");
			userCmd = cmdScanner.nextLine();
			float maxPrice = Float.parseFloat(userCmd);

			String sql = "SELECT * FROM Listing WHERE postal_code = ? AND city = ? AND country = ? AND lid IN (SELECT lid FROM Availability WHERE price >= ? AND price <= ? AND available = true)";

			try {
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, postalCode);
				preparedStmt.setString(2, city);
				preparedStmt.setString(3, country);
				preparedStmt.setFloat(4, minPrice);
				preparedStmt.setFloat(5, maxPrice);

				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Name: " + rs.getString("name"));
					System.out.println("Type: " + rs.getString("ltype"));
					System.out.println("Latitude: " + rs.getFloat("latitude"));
					System.out.println("Longitude: " + rs.getFloat("longitude"));
					System.out.println("Postal Code: " + rs.getInt("postal_code"));
					System.out.println("City: " + rs.getString("city"));
					System.out.println("Country: " + rs.getString("country"));
				}
				preparedStmt.close();
			} catch (SQLException e) {
				System.err.println("Error searching for listing!");
				System.out.println(e);
			}
		}
		else if (!userCmd.equals("n")) {
			System.out.println("Invalid input!");
			return;
		}

		String sql = "SELECT * FROM Listing WHERE postal_code = ? AND city = ? AND country = ?";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, postalCode);
			preparedStmt.setString(2, city);
			preparedStmt.setString(3, country);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Type: " + rs.getString("ltype"));
				System.out.println("Latitude: " + rs.getFloat("latitude"));
				System.out.println("Longitude: " + rs.getFloat("longitude"));
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Country: " + rs.getString("country"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error searching for listing!");
			System.out.println(e);
		}
	}

	static void bookingsDateRangeReport(Connection conn, Scanner cmdScanner) {
		String userCmd = "";
		System.out.println("Please enter the start date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String startDate = userCmd;
		System.out.println("Please enter the end date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String endDate = userCmd;

		String sql = "SELECT city, COUNT(*) as total FROM Booking JOIN Listing ON Booking.lid = Listing.lid WHERE Booking.start_date >= ? AND Booking.end_date <= ? GROUP BY city";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, startDate);
			preparedStmt.setString(2, endDate);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("Total number of bookings per city:");
			while (rs.next()) {
				System.out.println("City: " + rs.getString("city"));
				System.out.println("Total Bookings: " + rs.getInt("total"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error generating report!");
			System.out.println(e);
		}

		sql = "SELECT postal_code, COUNT(*) as total FROM Booking JOIN Listing ON Booking.lid = Listing.lid WHERE Booking.start_date >= ? AND Booking.end_date <= ? GROUP BY postal_code";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, startDate);
			preparedStmt.setString(2, endDate);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("Total number of bookings per postal code:");
			while (rs.next()) {
				System.out.println("Postal Code: " + rs.getInt("postal_code"));
				System.out.println("Total Bookings: " + rs.getInt("total"));
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.err.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void listingsPerAddressReport(Connection conn, Scanner cmdScanner) {
		String sql = "SELECT country, COUNT(*) as total FROM Listing GROUP BY country";

        try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

            Map<String, Integer> countryCountMap = new HashMap<>();
            while (resultSet.next()) {
                String country = resultSet.getString("country");
                int count = resultSet.getInt("total");
                countryCountMap.put(country, count);
            }

			System.out.println("Total number of listings per country:");
            for (Map.Entry<String, Integer> entry : countryCountMap.entrySet()) {
                String country = entry.getKey();
                int count = entry.getValue();
                System.out.println(country + ": " + count);
            }
        } catch (SQLException e) {
			System.out.println("Error generating report!");
            System.out.println(e);
        }

		sql = "SELECT city, COUNT(*) as total FROM Listing GROUP BY city";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

            Map<String, Integer> cityCountMap = new HashMap<>();
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                int count = resultSet.getInt("total");
                cityCountMap.put(city, count);
            }

			System.out.println("Total number of listings per city:");
            for (Map.Entry<String, Integer> entry : cityCountMap.entrySet()) {
                String city = entry.getKey();
                int count = entry.getValue();
                System.out.println(city + ": " + count);
            }
        } catch (SQLException e) {
			System.out.println("Error generating report!");
            System.out.println(e);
        }

		sql = "SELECT postal_code, COUNT(*) as total FROM Listing GROUP BY postal_code";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

            Map<String, Integer> postalCodeCountMap = new HashMap<>();
            while (resultSet.next()) {
                String postalCode = resultSet.getString("postal_code");
                int count = resultSet.getInt("total");
                postalCodeCountMap.put(postalCode, count);
            }

			System.out.println("Total number of listings per postal code:");
            for (Map.Entry<String, Integer> entry : postalCodeCountMap.entrySet()) {
                String postalCode = entry.getKey();
                int count = entry.getValue();
                System.out.println(postalCode + ": " + count);
            }
        } catch (SQLException e) {
			System.out.println("Error generating report!");
            System.out.println(e);
        }
	}

	static void rankHostsByListingsPerCountryReport(Connection conn, Scanner cmdScanner) {
		String sql = "SELECT h.sin, h.fname, h.lname, l.country, COUNT(l.lid) AS num_listings "
					+ "FROM Host h "
					+ "JOIN Listing l ON h.sin = l.sin "
					+ "GROUP BY h.sin, h.fname, h.lname, l.country "
					+ "ORDER BY l.country, num_listings DESC";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			String currentCountry = "";
			int rank = 1;
			while (resultSet.next()) {
				String country = resultSet.getString("country");
				if (!country.equals(currentCountry)) {
					currentCountry = country;
					rank = 1;
					System.out.println("Country: " + country);
				}
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				int numListings = resultSet.getInt("num_listings");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numListings);
				rank++;
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void rankHostsByListingsPerCityReport(Connection conn, Scanner cmdScanner) {
		String sql = "SELECT h.sin, h.fname, h.lname, l.city, COUNT(l.lid) AS num_listings "
					+ "FROM Host h "
					+ "JOIN Listing l ON h.sin = l.sin "
					+ "GROUP BY h.sin, h.fname, h.lname, l.city "
					+ "ORDER BY l.city, num_listings DESC";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			String currentCity = "";
			int rank = 1;
			while (resultSet.next()) {
				String city = resultSet.getString("city");
				if (!city.equals(currentCity)) {
					currentCity = city;
					rank = 1;
					System.out.println("City: " + city);
				}
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				int numListings = resultSet.getInt("num_listings");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numListings);
				rank++;
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void possibleCommercialHostsReport(Connection conn, Scanner cmdScanner) {
		String sql = "SELECT h.sin, h.fname, h.lname, l.country, (COUNT(l.lid) / (SELECT COUNT(*) FROM Listing WHERE country = l.country) * 100) AS percentage "
					+ "FROM Host h "
					+ "JOIN Listing l ON h.sin = l.sin "
					+ "GROUP BY h.sin, h.fname, h.lname, l.country "
					+ "HAVING percentage > 10 "
					+ "ORDER BY l.country, percentage DESC";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			String currentCountry = "";
			while (resultSet.next()) {
				String country = resultSet.getString("country");
				if (!country.equals(currentCountry)) {
					currentCountry = country;
					System.out.println("Country: " + country);
				}
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				float percentage = resultSet.getFloat("percentage");
				System.out.println(fname + " " + lname + ": " + percentage + "%");
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}

		sql = "SELECT h.sin, h.fname, h.lname, l.city, (COUNT(l.lid) / (SELECT COUNT(*) FROM Listing WHERE city = l.city) * 100) AS percentage "
					+ "FROM Host h "
					+ "JOIN Listing l ON h.sin = l.sin "
					+ "GROUP BY h.sin, h.fname, h.lname, l.city "
					+ "HAVING percentage > 10 "
					+ "ORDER BY l.city, percentage DESC";

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			String currentCity = "";
			while (resultSet.next()) {
				String city = resultSet.getString("city");
				if (!city.equals(currentCity)) {
					currentCity = city;
					System.out.println("City: " + city);
				}
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				float percentage = resultSet.getFloat("percentage");
				System.out.println(fname + " " + lname + ": " + percentage + "%");
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void rankRentersByBookingsReport(Connection conn, Scanner cmdScanner) {
		//Rank renters by number of bookings within a given date range
		String userCmd = "";
		System.out.println("Please enter the start date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String startDate = userCmd;
		System.out.println("Please enter the end date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String endDate = userCmd;

		String sql = "SELECT r.sin, r.fname, r.lname, COUNT(b.bid) AS num_bookings "
					+ "FROM Renter r "
					+ "JOIN Booking b ON r.sin = b.sin "
					+ "WHERE b.start_date >= ? AND b.end_date <= ? "
					+ "GROUP BY r.sin, r.fname, r.lname "
					+ "ORDER BY num_bookings DESC";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, startDate);
			preparedStmt.setString(2, endDate);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("Renters ranked by number of bookings:");
			int rank = 1;
			while (rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				int numBookings = rs.getInt("num_bookings");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numBookings);
				rank++;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void rankRentersByBookingsPerCityReport(Connection conn, Scanner cmdScanner) {
		//Rank renters by number of bookings within a given date range per city
		String userCmd = "";
		System.out.println("Please enter the start date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String startDate = userCmd;
		System.out.println("Please enter the end date (YYYY-MM-DD):");
		userCmd = cmdScanner.nextLine();
		String endDate = userCmd;

		String sql = "SELECT r.sin, r.fname, r.lname, l.city, COUNT(b.bid) AS num_bookings "
					+ "FROM Renter r "
					+ "JOIN Booking b ON r.sin = b.sin "
					+ "JOIN Listing l ON b.lid = l.lid "
					+ "WHERE b.start_date >= ? AND b.end_date <= ? "
					+ "GROUP BY r.sin, r.fname, r.lname, l.city "
					+ "ORDER BY l.city, num_bookings DESC";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, startDate);
			preparedStmt.setString(2, endDate);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("Renters ranked by number of bookings per city:");
			String currentCity = "";
			int rank = 1;
			while (rs.next()) {
				String city = rs.getString("city");
				if (!city.equals(currentCity)) {
					currentCity = city;
					rank = 1;
					System.out.println("City: " + city);
				}
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				int numBookings = rs.getInt("num_bookings");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numBookings);
				rank++;
			}
			preparedStmt.close();
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}

	static void mostCancellationsReport(Connection conn, Scanner cmdScanner) {
		String sql = "SELECT h.sin, h.fname, h.lname, COUNT(b.bid) AS num_cancellations "
					+ "FROM Host h "
					+ "JOIN Listing l ON h.sin = l.sin "
					+ "JOIN Booking b ON l.lid = b.lid "
					+ "WHERE b.status = 'cancelled' AND b.start_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
					+ "GROUP BY h.sin, h.fname, h.lname "
					+ "ORDER BY num_cancellations DESC";

		try {
			System.out.println("Hosts with the most cancellations within a year:");
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			int rank = 1;
			while (resultSet.next()) {
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				int numCancellations = resultSet.getInt("num_cancellations");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numCancellations);
				rank++;
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}

		sql = "SELECT r.sin, r.fname, r.lname, COUNT(b.bid) AS num_cancellations "
					+ "FROM Renter r "
					+ "JOIN Booking b ON r.sin = b.sin "
					+ "WHERE b.status = 'cancelled' AND b.start_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
					+ "GROUP BY r.sin, r.fname, r.lname "
					+ "ORDER BY num_cancellations DESC";

		try {
			System.out.println("Renters with the most cancellations within a year:");
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			int rank = 1;
			while (resultSet.next()) {
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				int numCancellations = resultSet.getInt("num_cancellations");
				System.out.println(rank + ". " + fname + " " + lname + ": " + numCancellations);
				rank++;
			}
		} catch (SQLException e) {
			System.out.println("Error generating report!");
			System.out.println(e);
		}
	}
}
