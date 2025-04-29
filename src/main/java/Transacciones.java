import util.ConexionBD;

import java.sql.*;

public class Transacciones {
    public static void main(String[] args){
        String qry = "INSERT INTO cliente (codigo_cliente, nombre_cliente, telefono, ciudad) VALUES(?, ?, ?, ?)";

        try(Connection con = ConexionBD.creaConexion();
            PreparedStatement stmt = con.prepareStatement(qry)) {
            con.setAutoCommit(false);
            System.out.println(" -------------- Conectado a la BBDD ----------");

            Savepoint sp = null;
            try {
                stmt.setInt(1,100);
                stmt.setString(2, "Carlos");
                stmt.setString(3, "+349875642");
                stmt.setString(4, "Madrid");
                stmt.executeUpdate();

                stmt.setInt(1,101);
                stmt.setString(2, "Mario");
                stmt.setString(3, "+349879999");
                stmt.setString(4, "Málaga");
                stmt.executeUpdate();

                sp = con.setSavepoint("sp");

                stmt.setInt(1,102);
                stmt.setString(2, null);
                stmt.setString(3, "+349870009");
                stmt.setString(4, "Málaga");
                stmt.executeUpdate();

                con.commit();
                System.out.println("------ Datos grabados ------");
            } catch (SQLException e) {
                System.out.println("Se deshacen los cambios debido a " + e.getMessage());
                if(sp == null) {
                    con.rollback();
                } else {
                    con.rollback(sp);
                }
            } finally {
                con.commit();
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la BBDD. " + e.getMessage());
        }
    }
}
