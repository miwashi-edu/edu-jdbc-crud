# edu-jdbc-crud

> CRUD är ett begrepp man ofta använder i utveckling, och framförallt med databaser. Den står för Create, Read, Update samt Delete. CRUD är en livscykel för entiteter i databaser.

> I exemplet använder vi oss av testgrenen i ett projekt för att visa CRUD via jdbc för att undvika att komplicera med undantagshantering.

## Instruktioner

```bash
cd ~
cd ws
rm -rf edu-crud-jdbc #Försiktig med denna
mkdir edu-crud-jdbc
cd edu-crud-jdbc
mkdir -p ./app/src/main/{java/se/iths,resources}
mkdir -p ./app/src/test/{java/se/iths,resources}
touch ./app/src/main/java/se/iths/App.java
touch ./app/src/test/java/se/iths/AppTest.java
touch ./app/build.gradle
echo "# edu-crud-jdbc" > README.md
echo "rootProject.name = 'edu-jdbc-crud'\ninclude('app')" > settings.gradle
curl -L https://gist.github.com/miwashiab/987826fc0f2df3cd686a755f38a1c504/raw/build.gradle -o ./app/build.gradle
curl -L https://gist.github.com/miwashiab/0ca40c177e62925e8dbb973229a4299d/raw/AppTest.java -o ./app/src/test/java/se/iths/AppTest.java
curl -L https://gist.github.com/miwashiab/629757ac8e86e4caeab6835396be159b/raw/App.java -o ./app/src/main/java/se/iths/App.java
curl -L https://gist.github.com/miwashiab/b1cf3a265791c60aa87aacbd3d257bff/raw/.gitignore -o .gitignore
git init
git add .
git commit -m "Initial commit"
```

## Låna drivrutin för jdbc mot MySql

```groovy
implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.30'
```

## AppTest.java

```java
package se.iths;


import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest {
    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/iths";
    private static final String JDBC_USER = "iths";
    private static final String JDBC_PASSWORD = "iths";
    private static final String TEST_USER ="Kalle Anka";
    private static final String TEST_ROLE ="Admin";
    private static final String TEST_NEWROLE ="User";
    private static long actualIdAfterInsert;

    public static Connection con = null;
    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection(JDBC_CONNECTION,JDBC_USER,JDBC_PASSWORD);
        con.createStatement().execute("DROP TABLE IF EXISTS User ");
        con.createStatement().execute("CREATE TABLE User (ID INT  NOT NULL AUTO_INCREMENT, NAME VARCHAR(255), ROLE VARCHAR(255), PRIMARY KEY (ID))");
    }

    @AfterAll
    public static void tearDown() throws Exception{
        con.close();
    }

    @Order(1)
    @Test
    void shouldCreateRowInDatabase() throws Exception{
        PreparedStatement stmt = con.prepareStatement("INSERT INTO User (NAME, ROLE) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, TEST_USER);
        stmt.setString(2, TEST_ROLE);
        stmt.execute();
        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next(), "Should have a row with generated id!");
        final long expectedIdAfterInsert = 1L;
        actualIdAfterInsert = rs.getLong(1);
        assertEquals(expectedIdAfterInsert, actualIdAfterInsert, "Should have correct id after insert!");
    }

    @Order(3)
    @Test
    void shouldFindRowInDatabase() throws Exception {
        PreparedStatement stmt = con.prepareStatement("SELECT Id, Name, Role FROM User WHERE Id = ?");
        stmt.setLong(1, actualIdAfterInsert);
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next(), "Should find one row!");
        assertEquals(actualIdAfterInsert, rs.getLong("Id"), "Selected Id shoule match");
        assertTrue(TEST_USER.equalsIgnoreCase(rs.getString("Name")), "Selected user should match");
        assertTrue(TEST_ROLE.equalsIgnoreCase(rs.getString("Role")), "Selected role should match");
        rs.close();
        stmt.close();
    }

    @Order(4)
    @Test
    void shouldUpdateRowInDatabase() throws Exception {
        PreparedStatement stmt = con.prepareStatement("UPDATE User Set ROLE = ? WHERE ID = ?");
        stmt.setString(1, TEST_NEWROLE);
        stmt.setLong(2, actualIdAfterInsert);
        stmt.execute();

        stmt = con.prepareStatement("SELECT Role FROM User WHERE Id = ?");
        stmt.setLong(1, actualIdAfterInsert);
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next(), "Should find one row!");
        assertTrue(TEST_NEWROLE.equalsIgnoreCase(rs.getString("Role")), "Updated role should match");
    }

    @Order(5)
    @Test
    void shouldDeleteRowInDatabase() throws Exception {
        con.createStatement().execute("DELETE FROM User");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM User");
        assertTrue(rs.next(), "Should find one row with count!");
        assertEquals(0, rs.getInt(1), "Table should be emtpty");
    }
}
```
