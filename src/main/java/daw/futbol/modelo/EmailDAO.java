/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daw.futbol.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jriosaguilar
 */
public class EmailDAO {

    private static final Connection CONEXION = Conexion.getInstance();

    // Este método devuelve una lista de emails ordenada por Nombre
    // si creterio es FALSE o por votos si criterio es TRUE
    public static ArrayList<Email> consultarEmails(boolean criterio) {
        Statement st;
        ResultSet res;
        ArrayList<Email> lista = new ArrayList();

        // Guardo la consulta SQL realizar en una cadena
        String sql = (criterio) ? "select * from emails" : "select * from emails";

        try {

            // Preparamos Statement
            st = CONEXION.createStatement();
            // Ejecutamos la sentencia y obtenemos la tabla resultado
            res = st.executeQuery(sql);
            // Ahora construimos la lista
            while (res.next()) {
                Email j = new Email();
                // Recogemos los datos del turismo, guardamos en un objeto
                j.setEmail(res.getString("email"));

                //Añadimos el objeto al array
                lista.add(j);
            }
            // Cerramos el recurso PreparedStatement
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas durante la consulta en tabla emails");
            System.out.println(e);
        }

        return lista;
    }

    public static int insertarEmail(String nombre) {

        // Cadena con la consulta parametrizada
        String sql = "insert into emails values (?)";

        PreparedStatement prest;

        try {
            // Preparamos la inserción de datos  mediante un PreparedStatement
            prest = CONEXION.prepareStatement(sql);

            // Procedemos a indicar los valores que queremos insertar
            // Usamos los métodos setXXX(indice, valor)
            // indice indica la posicion del argumento ?, empieza en 1
            // valor es el dato que queremos insertar
            prest.setString(1, nombre);

            // Ejecutamos la sentencia de inserción preparada anteriormente
            int nfilas = prest.executeUpdate();

            // Cerramos el recurso PreparedStatement
            prest.close();

            // La inserción se realizó con éxito, devolvemos filas afectadas
            return nfilas;
        } catch (SQLException e) {
            System.out.println("Problemas durante la inserción de datos en la tabla emails");
            System.out.println(e);
            return -1;
        }
    }
}
