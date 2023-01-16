package se.iths.persistency.dao;

import java.sql.*;

public class DAOUtil {

    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/Chinook";
    private static final String JDBC_USER = "iths";
    private static final String JDBC_PASSWORD = "iths";

    /**
     * Util method that closes a connection and ignores errors that occur.
     * @param con Connection to be closed.
     */
    public static void close(Connection con){
        if (con == null) return;
        try {
            con.close();
        } catch (SQLException ignore) {}
    }

    /**
     * Util method that closes a ResultSet and ignores errors that occur.
     * @param rs ResultSet to be closed.
     */
    public static void close(ResultSet rs){
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException ignore) {}
    }

    /**
     * Util method that closes a Statement and ignores errors that occur.
     * @param stmt Statement to be closed.
     */
    public static void close(Statement stmt){
        if (stmt == null) return;
        try {
            stmt.close();
        } catch (SQLException ignore) {}
    }

    /**
     * Util method that creates a connection using global settings.
     * @return connection to database.
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_CONNECTION,JDBC_USER,JDBC_PASSWORD);
    }

    /**
     * Method that executes a sql statement when testing ignoring exceptions.
     * @param sql the statment to be executed.
     */
    public static void execute(String sql){
        Connection con = null;
        try {
            con = getConnection();
            getConnection().createStatement().execute(sql);
        } catch (SQLException ignore) {}
        finally {
            close(con);
        }
    }
}
