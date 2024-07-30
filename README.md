# E-Voting-Web-Application


## Overview
The E-Voting System is a web application developed using Java,Java Servlets, HTML, CSS, and JavaScript. The project is designed to facilitate secure and efficient online voting. The application is built using the NetBeans IDE and connects to a MySQL database for data storage and management. Ps This system is based on purely Keeping into consideration the Voting system of Pakistan.

## Prerequisites
Before setting up the project, ensure you have the following installed:
- [NetBeans IDE](https://netbeans.apache.org/download/index.html)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [JSON Processing (JSON-P)](https://javaee.github.io/jsonp/)
- [Jakarta EE](https://jakarta.ee/)

## Setup Instructions

### Database Setup
1. **Install MySQL Server**: Download and install MySQL Server from the official [MySQL website](https://dev.mysql.com/downloads/mysql/).
2. **Create Database and Tables**: 
   - Open your MySQL command line or use a GUI like MySQL Workbench.
   - Create a database and the necessary tables. The SQL script for creating the tables is provided in the `/sql` directory of the project.

### Project Setup
1. **Download the Project**:
   - Download the zipped project file and extract it to a suitable location.
2. **Open in NetBeans**:
   - Launch NetBeans IDE.
   - Open the extracted project folder in NetBeans by navigating to `File > Open Project` and selecting the project directory.
3. **Configure Database Connection**:
   - Open the project's `web.xml` or `context.xml` (if using a server like Tomcat) and configure the database connection parameters.
   - Set your MySQL username and password in the configuration file.

### JSON and Jakarta EE Setup
1. **Download and Include JSON and Jakarta EE**:
   - Download the JSON Processing library and Jakarta EE dependencies.
   - Include these dependencies in your project. You can do this by adding the JAR files to the project's library or configuring them in your build tool (e.g., Maven or Gradle).

### Running the Application
1. **Start MySQL Server**: Ensure your MySQL server is running.
2. **Deploy the Application**:
   - In NetBeans, right-click the project and select `Run` or `Deploy`.
   - The application will run on `localhost:8080` or the port configured in your server settings.

## Usage
1. **Accessing the Application**:
   - Open a web browser and navigate to `http://localhost:8080/EVotingSystem` (or the appropriate URL if you have changed the server settings).
2. **Features**:
   - Users can register, log in, and cast their votes.
   - Administrators can manage elections, view results, and perform other administrative tasks.

## Troubleshooting
- Ensure that the MySQL server is running and the database connection details are correctly configured.
- Check for any missing dependencies in the project.
- Verify that the correct port is being used (default is 8080).

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Contact
For any inquiries or support, please contact [miraal.fatima.297@gmail.com].

