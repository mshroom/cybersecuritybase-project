package sec.project.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database() throws ClassNotFoundException {
        this.databaseAddress = "jdbc:sqlite:database.db";
        this.init();
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> statements = sqlStatements();

        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            for (String s : statements) {
                System.out.println("Running command >> " + s);
                st.executeUpdate(s);
            }
        } catch (Throwable t) {
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqlStatements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("CREATE TABLE Password (id integer PRIMARY KEY, user varchar(50), username varchar(50), password varchar(50), service varchar(50));");
        return list;
    }
    
}
