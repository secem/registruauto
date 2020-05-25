package registruauto.db;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDataSource {

    private static final Logger LOG = Logger.getLogger(MyDataSource.class.getName());

    private Connection connection;

    private String user;
    private String dbname;
    private String parola;
    private String url;
    private String driverName = "com.mysql.jdbc.Driver";

    private MyDataSource() {
        try {
            loadProperties();
            loadDriver();
            testConnection();
        } catch (Exception ex) {
            Logger.getLogger(MyDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static MyDataSource getInstance() {
        return MyDataSourceHolder.INSTANCE;
    }

    private void loadProperties() throws Exception {
        Properties pr = new Properties();
        pr.load(new FileReader("jdbc.properties"));

        user = pr.getProperty("user", "userauto");
        parola = pr.getProperty("parola", "123456");
        dbname = pr.getProperty("dbname", "registruauto");
        url = pr.getProperty("url", "jdbc:mysql://localhost:3306/");
        driverName = pr.getProperty("driverName", "com.mysql.jdbc.Driver");
        LOG.info("Proprietati incarcate cu succes!");

        url += dbname;
    }

    private void loadDriver() throws Exception {
        Class.forName(driverName);
        LOG.info("Driver incarcat cu succes!");
    }

    public Connection getConnection() throws Exception {
        if (connection == null || connection != null && connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, parola);
        }
        return connection;
    }

    private void testConnection() throws SQLException {
        if (connection == null || connection != null && connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, parola);
            LOG.info("Conexiunea testata cu succes!");
            connection.close();
        }

    }

    private static class MyDataSourceHolder {

        private static final MyDataSource INSTANCE = new MyDataSource();
    }

    public static void main(String[] args) {
        MyDataSource ds = MyDataSource.getInstance();
    }
}
