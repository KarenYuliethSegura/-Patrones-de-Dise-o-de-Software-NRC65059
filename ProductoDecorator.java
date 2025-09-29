
package inventario.burgerhouse.decorator;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
import inventario.burgerhouse.domain.Categoria;
import inventario.burgerhouse.domain.Producto;

import java.math.BigDecimal;
import java.util.Map;

public abstract class ProductoDecorator extends Producto {
        protected final Producto base;

    protected ProductoDecorator(Producto base) {

        super(base.getId(), base.getName(), base.getPrice(), base.getCategory(), base.getUnit());
        this.base = base;

        this.getAttributes().putAll(base.getAttributes());
    }

    @Override
    public double getPrice() {
        return base.getPrice();
    }

    // delega la “identidad” al producto base
    @Override public String getId() { return base.getId(); }
    @Override public String getName() { return base.getName(); }
    @Override public Categoria getCategory() { return base.getCategory(); }
    @Override public String getUnit() { return base.getUnit(); }

    // si un decorador necesitara alterar atributos individuales 
    protected void setExtra(String key, String value) {
        Map<String,String> attrs = this.getAttributes();
        attrs.put(key, value);
    }
}
