package controller;

import model.Producto;
import model.productoDAO;
import view.InterfazProducto;
import view.LoginTrabajador;

/**
 *
 * @author Braulio Fuentes Beltrán
 *         Jorge Fuentes Zúñiga 
 *         Benjamín Igor Ruiz
 *         Julián Iturra Valdes
 *         Fernanda Jara Vargas
 *         Christian Valdebenito Villagra
 */
public class Main {
    public static void main(String[] args) {
        
    Producto inventario = new Producto();
    productoDAO inventariodao = new productoDAO();
    InterfazProducto view = new InterfazProducto();
    LoginTrabajador view2 = new LoginTrabajador();
    ControllerRegistroInventario controlador = new ControllerRegistroInventario(view2, view);

    }
    
}
