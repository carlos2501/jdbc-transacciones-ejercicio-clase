package util;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL="jdbc:postgresql://localhost:5432/jardineria";
    private static final String USUARIO= "jardinero";
    private static final String CLAVE="jardinero";
    // esta variable contiene el objeto conexión -es el SINGLETON-
    private static Connection conex;

    // este método crea una instancia de una conexión a la BBDD
    public static Connection creaConexion() throws SQLException {
        if(conex == null){
            conex = DriverManager.getConnection(URL, USUARIO, CLAVE);
        }
        return conex;
    }

    public static Connection getDataSource() throws SQLException {
        PGSimpleDataSource ps = new PGSimpleDataSource();
        ps.setServerName("localhost");
        ps.setDatabaseName("jardineria");
        ps.setUser("jardinero");
        ps.setPassword("jardinero");
        return ((DataSource) ps).getConnection();
    }
}
