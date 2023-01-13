package se.iths.persistency;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.fail;

public class AlbumDAOTest {
    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/iths";
    private static final String JDBC_USER = "iths";
    private static final String JDBC_PASSWORD = "iths";

    public static Connection con = null;
    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection(JDBC_CONNECTION,JDBC_USER,JDBC_PASSWORD);
    }

    @AfterAll
    public static void tearDown() throws Exception{
        con.close();
    }

    @Test
    public void shouldFindAllAlbums(){
        fail("Not yet implemented!");
    }

    @Test
    public void shouldFindAlbumById(){
        fail("Not yet implemented!");
    }

    @Test
    public void shouldCreateAlbum(){
        fail("Not yet implemented!");
    }

    @Test
    public void shouldUpdateAlbum(){
        fail("Not yet implemented!");
    }

    @Test
    public void shouldDeleteAlbum(){
        fail("Not yet implemented!");
    }
}
