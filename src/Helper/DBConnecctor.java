package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecctor {
    private Connection connect = null;

    public Connection ConnectDB(){
        try {
            this.connect = DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.connect;
    }

    public static Connection getInstance(){
        DBConnecctor db = new DBConnecctor();
        return db.ConnectDB();
    }
}
