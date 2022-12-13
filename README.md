# edu-jdbc-crud

> CRUD är ett begrepp man ofta använder i utveckling, och framförallt med databaser. Den står för Create, Read, Update samt Delete. CRUD är en livscykel för entiteter i databaser.

> I exemplet använder vi oss av testgrenen i ett projekt för att visa CRUD via jdbc.

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
echo "rootProject.name = 'edu-intro-jdbc'\ninclude('app')" > settings.gradle
curl -L https://gist.github.com/miwashiab/987826fc0f2df3cd686a755f38a1c504/raw/build.gradle -o ./app/build.gradle
curl -L https://gist.github.com/miwashiab/0ca40c177e62925e8dbb973229a4299d/raw/AppTest.java -o ./app/src/test/java/se/iths/AppTest.java
curl -L https://gist.github.com/miwashiab/629757ac8e86e4caeab6835396be159b/raw/App.java -o ./app/src/main/java/se/iths/App.java
curl -L https://gist.github.com/miwashiab/b1cf3a265791c60aa87aacbd3d257bff/raw/.gitignore -> .gitignore
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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/iths";
    private static final String JDBC_USER = "iths";
    private static final String JDBC_PASSWORD = "iths";

    public static Connection con = null;
    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection(JDBC_CONNECTION,JDBC_USER,JDBC_PASSWORD);
        con.createStatement().execute("DROP TABLE IF EXISTS ANYTABLE ");
        con.createStatement().execute("CREATE TABLE ANYTABLE (ID INT  NOT NULL AUTO_INCREMENT, NAME VARCHAR(255), PRIMARY KEY (ID))");
    }

    @AfterAll
    public static void tearDown() throws Exception{
        con.close();
    }

    @Test
    void shouldCreateRowInDatabase() throws Exception{

        fail("Not yet implemented!");
    }

    @Test
    void shouldFindRowInDatabase() throws Exception {

        fail("Not yet implemented!");
    }

    @Test
    void shouldUpdateRowInDatabase() throws Exception {

        fail("Not yet implemented!");
    }

    @Test
    void shouldDeleteRowInDatabase() throws Exception {

        fail("Not yet implemented!");
    }
}
```
