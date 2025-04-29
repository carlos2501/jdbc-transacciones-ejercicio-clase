import com.github.javafaker.Faker;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class AddBatch {
    public static void main(String[] args) throws SQLException {
        String qry = "INSERT INTO cliente (codigo_cliente, nombre_cliente, telefono, ciudad) VALUES(?, ?, ?, ?)";
        try(Connection con = ConexionBD.creaConexion();
            PreparedStatement stmt = con.prepareStatement(qry)) {
            System.out.println("------ Conectado a la BBDD ------");

            // Asignamos valores de prueba a los datos a insertar mediante la librería Faker
            try{
                Faker faker = new Faker(new Locale("es"));
                for (int i = 100; i < 200; i++) {
                    // Cada bucle genera un INSERT que se ejecuta inmediatamente
                    stmt.setInt(1, i);
                    stmt.setString(2, faker.name().firstName());
                    stmt.setString(3, faker.phoneNumber().cellPhone());
                    stmt.setString(4, faker.address().city());
                    stmt.addBatch();
                }
                int[] results = stmt.executeBatch();  // devuelve la cantidad de registros afectados por cada sentencia
                for (int result : results) {
                    System.out.println("filas afectadas: " + result);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
