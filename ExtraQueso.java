package inventario.burgerhouse.decorator;

/**
 *
* @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.domain.Producto;

public class ExtraQueso extends ProductoDecorator {
    private final double recargo;

    public ExtraQueso(Producto base, double recargo) {
        super(base);
        this.recargo = recargo;
        setExtra("extra_queso", "true");
    }

    @Override
    public double getPrice() {
        return super.getPrice() + recargo;
    }
}
