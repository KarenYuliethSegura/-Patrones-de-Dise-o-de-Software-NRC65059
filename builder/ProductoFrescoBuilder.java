package inventario.burgerhouse.builder;

/**
 *
 * @author yulieth segura
 */

import inventario.burgerhouse.domain.ProductoFresco;
import inventario.burgerhouse.domain.Categoria;

public class ProductoFrescoBuilder extends ProductoBuilder<ProductoFresco, ProductoFrescoBuilder> {

    @Override
    protected ProductoFrescoBuilder self() {
        return this;
    }

    @Override
    public ProductoFresco build() {
        ProductoFresco f = new ProductoFresco(id, name, price, Categoria.FRESCOS, unit);
        applyCommon(f);
        return f;
    }
}
