package tg.univlome.epl.cookplusserver.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection tester. Tests the PostgreSQL database connection.
 *
 * @author DAKEY Ahoefa Light
 */
public class DatabaseConnectionTest {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Testing PostgreSQL Connection");
        System.out.println("========================================");

        // Test 1: Test connection
        System.out.println("\n1. Testing database connection...");
        if (DatabaseConnection.testConnection()) {
            System.out.println("✓ Connection successful!");
        } else {
            System.out.println("✗ Connection failed!");
            System.exit(1);
        }

        // Test 2: Get a connection
        System.out.println("\n2. Getting database connection...");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Got active connection");
                System.out.println("   - Database: " + conn.getCatalog());
                System.out.println("   - Driver: " + conn.getMetaData().getDriverName());
                System.out.println("   - Version: " + conn.getMetaData().getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            System.out.println("✗ Error getting connection: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("\n========================================");
        System.out.println("All tests passed!");
        System.out.println("========================================");
    }
}
