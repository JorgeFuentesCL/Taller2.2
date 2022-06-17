package model;

import conexionBD.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Braulio Fuentes Beltrán
 *         Jorge Fuentes Zúñiga 
 *         Benjamín Igor Ruiz
 *         Julián Iturra Valdes
 *         Fernanda Jara Vargas
 *         Christian Valdebenito Villagra
 */
public class productoDAO {

    //Atributos que establecen conexión y ejecución de query sql
    ConexionBD conexion = new ConexionBD();
    private static Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //Insertar - Create
    //BUSCAR ERROR CON INSERT DEL VIDEO
    public void insertarDatos(Producto producto) {
        String sql = "insert into producto(nombre, precio, descripcion) values(?, ?,?)";
        // boolean resp = false; //Resultado de la transaccion
        try {
            con = ConexionBD.iniciarConexion(); //inicio la conexion con la BD
            ps = con.prepareStatement(sql); //Abro la query
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.executeUpdate(); //Ejecutando la query
            // resp = true; //entrego el valor verdadero cuando se ejecuta correctamente

        } catch (SQLException e) {
            System.out.println("Error SQL Agregar: " + e);
        }
    }

    //Listar - Select (Read)
    public List listar() {
        String sql = "SELECT * FROM producto";
        List producto = new ArrayList();
        try {
            con = ConexionBD.iniciarConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Producto p = new Producto();
                p.setCodigo(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setPrecio(rs.getInt(3));
                p.setDescripcion(rs.getString(4));
                producto.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error SQL" + e);
        }
        return producto;
    }

    //Editar - Actualizar (UPDATE)
    public void editar(Producto producto) {
        String sql = "update producto set Nombre=?, Precio=?, Descripcion=? where Codigo=?";
        try {
            con = ConexionBD.iniciarConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al EditarDAO: " + e);
        }
    } // fin del metodo editar

//metodo eliminar
    public void eliminar(int id) {

        String sql = "delete from producto where codigo=" + id;
        try {
            con = ConexionBD.iniciarConexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminarDAO: " + e);
        }
    } // fin metodo eliminar

    public boolean validarAdmin(LoginUsuario user) {
        int resultado = 0;
        Connection con = ConexionBD.iniciarConexion();
        try {
            String usuario = user.getUsuario();
            String password = user.getPassword();
            String sql = "SELECT * FROM trabajador WHERE Usuario='" + usuario + "'and Password='" + password + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                resultado = 1;
                if (resultado == 1) {

                    return true;
                } else {

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error de usuario y/o contraseña");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la Base de Datos");
        }
        return false;
    }

    public boolean crearUsuario(LoginUsuario user) {
        String sql = "insert into trabajador(Usuario, password) values(?, ?)";
        // boolean resp = false; //Resultado de la transaccion
        try {
            con = ConexionBD.iniciarConexion(); //inicio la conexion con la BD
            ps = con.prepareStatement(sql); //Abro la query
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getPassword());
            ps.executeUpdate(); //Ejecutando la query
            return true;
            // resp = true; //entrego el valor verdadero cuando se ejecuta correctamente

        } catch (SQLException e) {
            System.out.println("Error SQL Agregar: " + e);
            return false;
        }

    }
}
