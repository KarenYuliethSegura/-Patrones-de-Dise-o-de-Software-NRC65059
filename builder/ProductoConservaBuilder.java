
package inventario.burgerhouse.builder;

/**
 *
 * @author yulieth segura
 */

import inventario.burgerhouse.domain.ProductoConserva;
import inventario.burgerhouse.domain.Categoria;

public class ProductoConservaBuilder extends ProductoBuilder<ProductoConserva, ProductoConservaBuilder> {

    @Override
    protected ProductoConservaBuilder self() {
        return this;
    }

    @Override
    public ProductoConserva build() {
        ProductoConserva c = new ProductoConserva(id, name, price, Categoria.CONSERVAS, unit);
        applyCommon(c);
        return c;
    }
}