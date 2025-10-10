package inventario.burgerhouse.builder;

/**
 *
 * @author dct-yulieth
 */

import inventario.burgerhouse.domain.ProductoPanaderia;
import inventario.burgerhouse.domain.Categoria;

public class ProductoPanaderiaBuilder extends ProductoBuilder<ProductoPanaderia, ProductoPanaderiaBuilder> {

    @Override
    protected ProductoPanaderiaBuilder self() {
        return this;
    }

    @Override
    public ProductoPanaderia build() {
        ProductoPanaderia p = new ProductoPanaderia(id, name, price, Categoria.PANADERIA, unit);
        applyCommon(p);
        return p;
    }
}