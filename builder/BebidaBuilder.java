package inventario.burgerhouse.builder;

/**
 *
 * @author yulieth segura
 */

import inventario.burgerhouse.domain.Bebida;
import inventario.burgerhouse.domain.Categoria;

public class BebidaBuilder extends ProductoBuilder<Bebida, BebidaBuilder> {

    @Override
    protected BebidaBuilder self() {
        return this;
    }

    @Override
    public Bebida build() {
        Bebida b = new Bebida(id, name, price, Categoria.BEBIDAS, unit);
        applyCommon(b);
        return b;
    }
}
