package model;

/**
 *
 * @author Braulio Fuentes Beltrán
 *         Jorge Fuentes Zúñiga 
 *         Benjamín Igor Ruiz
 *         Julián Iturra Valdes
 *         Fernanda Jara Vargas
 *         Christian Valdebenito Villagra
 */
public class Producto {
     
    //Atributos Producto (Variables Globales)
    
    private int codigo;
    private String nombre;
    private int precio;
    private String descripcion;

    //Constructor Vacio
    public Producto() {
    }

    //Constructor Ingresar
    public Producto(String nombre, int precio, String descripcion) {

        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;

    }

    //Constructor Actualizar
    public Producto(int codigo, String nombre, int precio, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }
    
    //getter y setter
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
   
}
