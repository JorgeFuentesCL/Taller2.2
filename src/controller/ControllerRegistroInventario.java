package controller;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Producto;
import model.productoDAO;
import model.LoginUsuario;
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
public class ControllerRegistroInventario implements ActionListener {

    //Llamamos a las clases(INSTANCIAS)
    Producto inventario = new Producto();
    productoDAO inventariodao = new productoDAO();
    InterfazProducto view = new InterfazProducto();
    LoginTrabajador view2 = new LoginTrabajador();
    DefaultTableModel productoTabla = new DefaultTableModel();

    //Variables Globales
    private int codigo;
    private String nombre;
    private int precio;
    private String descripcion;
    private String usuario;
    private String password;

    //Constructor Interfaz
    public ControllerRegistroInventario(LoginTrabajador view2, InterfazProducto view) {
        this.view = view;
        this.view2 = view2;
        view2.setVisible(true);
        //view.setVisible(true);//hacemos view visible 
        agregarEventos();
        listarTabla();

    }

    //Agtegamos Eventos para lectura de botones: Declaramos ActionListener para cada botón (Los llamamos mediante la view)
    private void agregarEventos() {
        this.view.BtnEditar.addActionListener(this);
        this.view.BtnIngresar.addActionListener(this);
        this.view.BtnEliminar.addActionListener(this);
        this.view.BtnSalir.addActionListener(this);
        this.view2.IngresarBTN2.addActionListener(this);
        this.view2.RegistrarBTN.addActionListener(this);
        this.view.TableInventario.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);

            }

        });
    }

    //TABLA
    private void listarTabla() {
        String[] titulos = new String[]{"Código", "Nombre", "Precio", "Descripcion"}; //Arreglo
        productoTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProducto = inventariodao.listar();//ArrayList
        for (Producto inventario : listaProducto) {
            productoTabla.addRow(new Object[]{inventario.getCodigo(), inventario.getNombre(), inventario.getPrecio(), inventario.getDescripcion()});//Array unidimensional
        }
        this.view.TableInventario.setModel(productoTabla);
        this.view.TableInventario.setPreferredSize(new Dimension(800, productoTabla.getRowCount() * 16));
    }

    //Llenar campos
    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        codigo = (int) view.getTableInventario().getModel().getValueAt(target.getSelectedRow(), 0);
        view.TxtNombre.setText(view.TableInventario.getModel().getValueAt(target.getSelectedRow(), 1).toString());
        view.TxtPrecio.setText(view.TableInventario.getModel().getValueAt(target.getSelectedRow(), 2).toString());
        view.TxtDescripcion.setText(view.TableInventario.getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }

    //----------------------------------------------------------------------------------------------
    private boolean validarDatos() {
        if ("".equals(view.getTxtNombre().getText()) || "".equals(view.getTxtPrecio().getText()) || "".equals(view.getTxtDescripcion().getText())) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean cargarDatos() {
        try {
            nombre = view.getTxtNombre().getText();
            precio = Integer.parseInt(view.getTxtPrecio().getText());
            descripcion = view.getTxtDescripcion().getText();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Campo precio debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);

            System.out.println("Error al cargar datos" + e);
            return false;
        }
    }

    private void limpiarCampos() {
        view.getTxtNombre().setText("");//getTxtNombre??
        view.getTxtPrecio().setText("");
        view.getTxtDescripcion().setText("");
        codigo = 0;
        nombre = "";
        precio = 0;
        descripcion = "";
    }
    //----------------------------------------------------

    //Método Insertar
    private void insertarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Producto producto = new Producto(nombre, precio, descripcion);
                    inventariodao.insertarDatos(producto);
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error Insertar: " + e);
        } finally {
            listarTabla();//vuelve a listar tabla
        }

    }

    private void editarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Producto producto = new Producto(codigo, nombre, precio, descripcion);
                    inventariodao.editar(producto);
                    JOptionPane.showMessageDialog(null, "Registro editado");
                    limpiarCampos();

                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error al editarC : " + e);
        } finally {
            listarTabla();
        }
    }

    private void eliminarProducto() {
        try {
            if (codigo != 0) {
                inventariodao.eliminar(codigo);
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                limpiarCampos();
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al eliminarC : " + e);
        } finally {
            listarTabla();
        }
    }

    private void consultarUsuario() {
        try {
            usuario = view2.getUsuarioTF().getText();
            String pass = new String(view2.getPasswordTF().getPassword());

            LoginUsuario lt = new LoginUsuario(usuario, pass);
            boolean login = inventariodao.validarAdmin(lt);
            if (login) {
                view2.setVisible(false);
                view.setVisible(true);
            }
        } catch (HeadlessException e) {
            System.out.println("Error al Ingresar : " + e);
        } finally {
            listarTabla();
        }
    }

    private void registrarUsuario() {
        try {
            usuario = view2.getUsuarioTF().getText();
            String pass = new String(view2.getPasswordTF().getPassword());
            if ("".equals(view2.getUsuarioTF().getText()) || ("".equals(pass))) {
                JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacíos", "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                LoginUsuario lt = new LoginUsuario(usuario, pass);
                boolean login = inventariodao.crearUsuario(lt);
                if (login) {

                    JOptionPane.showMessageDialog(null, "Usuario Registrado");
                }

            }
        } catch (Exception e) {
            System.out.println("Error Insertar: " + e);
        } finally {
            listarTabla();//vuelve a listar tabla
        }

    }

    public ControllerRegistroInventario() {
    }

    //BOTONES
    @Override
    public void actionPerformed(ActionEvent ae) {

        //BOTÓN INGRESAR
        if (ae.getSource() == view.getBtnIngresar()) {
            insertarProducto();
        }
        //Botón Editar
        if (ae.getSource() == view.getBtnEditar()) {
            editarProducto();
        }
        //BOTÓN ELIMINAR
        if (ae.getSource() == view.getBtnEliminar()) {
            eliminarProducto();
        }

        //Botón Salir
        if (ae.getSource() == view.BtnSalir) {
            System.exit(0);
        }
        //Boton Ingresar
        if (ae.getSource() == view2.getIngresarBTN2()) {
            consultarUsuario();
        }

        //Boton Registrar
        if (ae.getSource() == view2.getRegistrarBTN()) {
            registrarUsuario();
        }
    }
}
