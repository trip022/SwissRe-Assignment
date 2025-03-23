wissRe-Assignment
###Employee Analysis Application This is a Spring Boot application that provides functionality to manage and analyze employee data. The main features of the application are: Upload employee data from a CSV file: Read employee information from a CSV file and store it in the database. Analyze salary discrepancies for managers: Identify discrepancies in the salaries of managers. Check the reporting line length: Verify if the reporting line of any employee exceeds a predefined threshold.

###Features RESTful APIs for managing employees. CSV file upload functionality for importing employee data. Analysis of employee salaries and reporting lines.

###Technologies Used Java 21 Spring Boot JUnit 5 (for testing) Mockito (for mocking dependencies)

###Project Structure The project is structured into the following modules: com.company.swissRe.controller: Contains the REST controller for handling HTTP requests. com.company.swissRe.service: Contains the business logic of the application (service layer). com.company.swissRe.model: Contains the entity classes (like Employee). com.company.swissRe.repository: Contains repository classes for database interaction.

###Setup Prerequisites Before running the application, make sure you have the following installed: Java 11 or later Maven IDE (e.g., Eclipse, IntelliJ IDEA)

###Clone the repository git clone https://github.com/trip022/employee-analysis.git cd employee-analysis

###Build the application mvn clean install

###Run the application mvn spring-boot:run

###API Endpoints

GET /employees Retrieve a list of all employees.

POST /employees/upload Upload a CSV file containing employee data. Request Body: multipart/form-data with file parameter file containing the CSV file. Example Request: curl -X POST -F "file=@/path/to/your/file.csv" http://localhost:8080/employees/upload

GET /employees/salary-analysis Analyze the salary discrepancies of managers.

GET /employees/reporting-line Check if any employee's reporting line exceeds the threshold.

###Running Tests

mvn test view detailed test results in the target/surefire-reports directory
