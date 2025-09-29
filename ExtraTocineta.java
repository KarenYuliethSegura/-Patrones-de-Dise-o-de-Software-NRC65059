
package inventario.burgerhouse.decorator;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
import inventario.burgerhouse.domain.Producto;

public class ExtraTocineta extends ProductoDecorator {
    
    private final double recargo;

    public ExtraTocineta(Producto base, double recargo) {
        super(base);
        this.recargo = recargo;
        setExtra("extra_tocineta", "true");
    }

    @Override
    public double getPrice() {
        return super.getPrice() + recargo;
    }

}
