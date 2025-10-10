package inventario.burgerhouse.domain;

/**
 *
 * @author yulieth segura
 */

import java.util.HashMap;
import java.util.Map;

public abstract class Producto implements Cloneable {
    //- Declaracion de variables
    private String id; //- id del producto
    private String name; //- nombre del producto
    private double price; //- precio del producto 
    private Categoria category; //- categoria del producto
    private String unit; //- numero de unidades disponibles
    private Map<String, String> attributes; //- Mapa de atributos
    //-Constructor
    protected Producto(String id, String name, double price, Categoria category, String unit) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.category = category;
    this.unit = unit;
    this.attributes = new HashMap<>();
    }
    //- Getters y Setters estandares para los archivos.
    //- Getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public Categoria getCategory() {
        return category;
    }
    public String getUnit() {
        return unit;
    }
    public Map<String, String> getAttributes() {
        return attributes;
    }
    //- Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setCategory(Categoria category) {
        this.category = category;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setAttributes(Map<String, String> attributes) { 
        this.attributes = attributes; 
    }
    //- Se crea una copia del producto, con un nuevo mapa de atributos
    @Override
    public Producto clone() {
        try{
            Producto cloned = (Producto) super.clone();
            cloned.attributes = new HashMap<>(this.attributes);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error al clonar", e);
        }
    }
    //- Devolvemos todo en un string de producto
    @Override
    public String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", unit='" + unit + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
