
package inventario.burgerhouse.adapter;

/**
 * @author Mauricio Martinez, Karen Segura
 */

public class ProveedorCarnesPremium {
    
    //- Formato propio del proveedor (incompatible con nuestro sistema)
    private String codigo_articulo;
    private String descripcion;
    private double valor_unitario;
    private String medida;
    private int stock_disponible;
    private String fecha_caducidad;
    
    public ProveedorCarnesPremium(String codigo_articulo, String descripcion, 
                                   double valor_unitario, String medida, 
                                   int stock_disponible, String fecha_caducidad) {
        this.codigo_articulo = codigo_articulo;
        this.descripcion = descripcion;
        this.valor_unitario = valor_unitario;
        this.medida = medida;
        this.stock_disponible = stock_disponible;
        this.fecha_caducidad = fecha_caducidad;
    }
    
    //- Metodos con nomenclatura diferente (formato del proveedor)
    public String getCodigo_articulo() {
        return codigo_articulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getValor_unitario() {
        return valor_unitario;
    }
    
    public String getMedida() {
        return medida;
    }
    
    public int getStock_disponible() {
        return stock_disponible;
    }
    
    public String getFecha_caducidad() {
        return fecha_caducidad;
    }
    
    @Override
    public String toString() {
        return "ProveedorCarnesPremium{" +
                "codigo=" + codigo_articulo +
                ", desc='" + descripcion + '\'' +
                ", valor=" + valor_unitario +
                '}';
    }
}