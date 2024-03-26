import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DepartmentDatabase {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/departments";
    private static final String USER = "department";
    private static final String PASSWORD = "1234";

    // JDBC variables for opening, closing and managing connection
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            // Connect to the MySQL database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a statement
            statement = connection.createStatement();

            // Create department table if not exists
            String createTableQuery = "CREATE TABLE IF NOT EXISTS department (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))";
            statement.execute(createTableQuery);

            // Insert Department objects into the table
            insertDepartment(new Department(1, "IT"));
            insertDepartment(new Department(2, "HR"));
            insertDepartment(new Department(3, "Finance"));

            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to insert Department object into the database
    private static void insertDepartment(Department department) throws SQLException {
        String insertQuery = String.format("INSERT INTO department (id, name) VALUES (%d, '%s')",
                department.getId(), department.getName());
        statement.executeUpdate(insertQuery);
    }

    // Department class representing a department object
    static class Department {
        private int id;
        private String name;

        public Department(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
