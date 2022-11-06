package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
@Setter
public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;

    private CustomDataSource(String driver, String url, String password, String name) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public static CustomDataSource getInstance() {
        if (instance != null) return instance;

        try {
            String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            String appConfigPath = rootPath + "app.properties";

            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));

            instance = new CustomDataSource(
                    appProps.getProperty("postgres.driver"),
                    appProps.getProperty("postgres.url"),
                    appProps.getProperty("postgres.password"),
                    appProps.getProperty("postgres.name")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }

    private Connection getConnectionCommon(String url, String name, String password) {
        String _url = url == null ? this.url : url;
        String _user = name == null ? this.name : name;
        String _password = password == null ? this.password : password;

        Connection c = null;
        try {
            Class.forName(driver);
            c = DriverManager
                    .getConnection(_url, _user, _password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
    }


    @Override
    public Connection getConnection() {
        return getConnectionCommon(null, null, null);
    }

    @Override
    public Connection getConnection(String username, String password) {
        return getConnectionCommon(null, username, password);
    }

    public Connection getConnection(String url) {
        return getConnectionCommon(url, null, null);
    }

    public Connection getConnection(String url, String username, String password) {
        return getConnectionCommon(url, username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
    }
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}