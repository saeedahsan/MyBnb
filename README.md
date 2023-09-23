# MyBnb
MyBnb is a CLI app that mimicks the functionalities of Airbnb's website, including creating and renting listings, commenting on listings and users, and search and filter functionalities. The database is implemented using MySQL, while the entire project is created using Java.

## How to Use This Application
With MyBnB, you can become a host or renter and either create listings and put them up for rent or rent the listings themselves. Using the command line, you can sign up, sign in, create listings, update availability, rent properties and comment on other users and listings. In addition, you can also search for properties and see different reports on the application’s users, listings and bookings. Of course, you will need to be signed in as either a host or renter in order to use some of these features. Below is a list of commands you can run, what their functions are, and whether you need to be signed in to use them.

`create_host`: Create a host account.<br>
`create_renter`: Create a renter account.<br>
`host_sign_in`: Sign in as a host. You must be logged out to use this command.<br>
`renter_sign_in`: Sign in as a renter. You must be logged out to use this command.<br>
`logout`: Log out. You must be logged in to use this command.<br>
`delete_account`: Delete your account. You must be signed in to use this command.<br>
`create_listing`: Create a new listing. You must be signed in as a host to use this command.<br>
`add_availability`: Add available dates to your listings. You must be signed in as a host to use this command.<br>
`update_price`: Update the price of your listings. This will only work if the listing is available and not booked for the date you select. You must be signed in as a host to use this command.<br>
`remove_availability`: Remove availability of your listings. This will only work if the listing is available and not booked for the date you select. You must be signed in as a host to use this command.<br>
`book`: Book a listing. This will only work if the listing is available for the dates you select. You must be signed in as a renter to use this command.<br>
`cancel`: Cancel a booking. You must be signed in to use this command.<br>
`rate_host`: Give a host a rating. This will only work if you’ve booked at least one of the selected host’s listings. You must be signed in as a renter to use this command.<br>
`comment_on_host`: Comment on a host. This will only work if you’ve booked at least one of the selected host’s listings. You must be signed in as a renter to use this command.<br>
`rate_renter`: Give a renter a rating. This will only work if the renter has booked at least one of your listings. You must be signed in as a host to use this command.<br>
`comment_on_renter`: Comment on a renter. This will only work if the renter has booked at least one of your listings. You must be signed in as a host to use this command.<br>
`rate_listing`: Give a listing a rating. This will only work if you’ve booked this listing. You must be signed in as a renter to use this command.<br>
`comment_on_listing`: Comment on a listing. This will only work if you’ve booked this listing. You must be signed in as a renter to use this command.<br>
`search_by_location`: Search for listings within a specified distance from the specified location.<br>
`search_by_postal_code`: Search for listings at or adjacent to the specified postal code.<br>
`search_by_address`: Search for listings at an exact address.<br>
`bookings_date_range_report`: Get the total number of bookings in a specified date range per city and postal code.<br>
`listings_per_address_report`: Get the total number of listings per postal code, city and country.<br>
`rank_hosts_listings_country_report`: Rank hosts by the number of listings they have per country.<br>
`rank_hosts_listings_city_report`: Rank hosts by the number of listings they have per city.<br>
`possible_commercial_hosts_report`: Get hosts that own more than 10% of the listings in a city or country.<br>
`rank_renters_bookings_report`: Rank renters by the number of bookings they’ve done in a specified date range.<br>
`rank_renters_bookings_city_report`: Rank renters by the number of bookings they’ve done in a specified date range per city.<br>
`most_cancellations_report`: Get hosts and renters with the most cancellations within a year.<br>
`exit`: Exit the application.

Note: If you want to quickly start playing around with actual data, run the SQL script to insert several hosts, renters, listings and other data in the database. 
