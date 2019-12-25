package sec.project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sec.project.domain.Account;
import sec.project.domain.Password;

public class PasswordDao {

    private Database database;

    public PasswordDao() throws ClassNotFoundException {
        this.database = new Database();
    }

    public void add(Password password) throws SQLException, ClassNotFoundException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Password (user, username, password, service) VALUES (?, ?, ?, ?)");
        stmt.setString(1, password.getAccount().getUsername());
        stmt.setString(2, password.getUsername());
        stmt.setString(3, password.getPassword());
        stmt.setString(4, password.getService());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    // I decided not to use this method because the one below is much much safer
    /*
    public List<Password> findAllByUser(Account account) throws SQLException, ClassNotFoundException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Password WHERE user = ?");
        stmt.setString(1, account.getUsername());

        List<Password> passwords = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String service = rs.getString("service");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Password newPassword = new Password(service, username, password);
            passwords.add(newPassword);
        }
        rs.close();
        stmt.close();
        connection.close();
        return passwords;
    }
*/
    
    public List<Password> findAllByUsername(String user) throws SQLException, ClassNotFoundException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Password WHERE user = '" + user + "'");

        List<Password> passwords = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String service = rs.getString("service");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Password newPassword = new Password(service, username, password);
            passwords.add(newPassword);
        }
        rs.close();
        stmt.close();
        connection.close();
        return passwords;
    }

}
