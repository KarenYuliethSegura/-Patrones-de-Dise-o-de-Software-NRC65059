package inventario.burgerhouse.decorator;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
import inventario.burgerhouse.domain.Producto;

public class ExtraJalapenos extends ProductoDecorator {
    private final double recargo;

    public ExtraJalapenos(Producto base, double recargo) {
        super(base);
        this.recargo = recargo;
        setExtra("extra_jalapenos", "true");
    }

    @Override
    public double getPrice() {
        return super.getPrice() + recargo;
    }
}
