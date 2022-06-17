package conexionBD;

//import conexionBD.Conexion;
import java.sql.Connection;
import javax.swing.JOptionPane;


public class Prueba {
    public static void main(String[] args) {
        Connection con;
        con = ConexionBD.iniciarConexion();
        if (con != null){
            JOptionPane.showMessageDialog(null, "CONEXION EXITOSA");
            }else{
                 JOptionPane.showMessageDialog(null, "ERROR EN LA CONEXION");
        }
    }
}
