# emails_db
Microservice that make operations in the emails database

Execute these steps to run this Microservice
1. mvn clean package -DskipTests
2. Make sure you have a MySql Server running on port 3306
3. Flyway will create the tables in a schema called 'gbtec'
3. Create a new run configuration -> maven + spring-boot:run
4. Run the application
