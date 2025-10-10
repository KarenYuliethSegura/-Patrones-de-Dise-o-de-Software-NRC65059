
package inventario.burgerhouse.builder;

/**
 *
 * @author yulieth segura
 */

import inventario.burgerhouse.domain.Producto;
import java.util.HashMap;
import java.util.Map;

public abstract class ProductoBuilder<T extends Producto, SELF extends ProductoBuilder<T, SELF>> {

    protected String id;
    protected String name;
    protected double price;
    protected String unit;
    protected Map<String, String> attributes = new HashMap<>();

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }

    public SELF id(String id) {
        this.id = id;
        return self();
    }

    public SELF name(String name) {
        this.name = name;
        return self();
    }

    public SELF price(double price) {
        this.price = price;
        return self();
    }

    public SELF unit(String unit) {
        this.unit = unit;
        return self();
    }

    //- agrega/actualiza un atributo suelto
    public SELF attribute(String key, String value) {
        this.attributes.put(key, value);
        return self();
    }

    //- reemplaza todos los atributos
    public SELF attributes(Map<String, String> attrs) {
        this.attributes.clear();
        if (attrs != null) this.attributes.putAll(attrs);
        return self();
    }

    //- construye el producto concreto
    public abstract T build();

    //- hook para aplicar atributos comunes después de construir
    protected void applyCommon(Producto p) {
        p.setAttributes(new HashMap<>(this.attributes));
    }
}