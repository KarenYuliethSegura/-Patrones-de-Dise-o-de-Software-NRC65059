package inventario.burgerhouse.factory;

/**
 *
 * @author yulieth segura
 */
import inventario.burgerhouse.builder.BebidaBuilder;
import inventario.burgerhouse.domain.Producto;

public class FabricaBebidas implements AbstractCategoriaFactory {

    @Override
    public Producto crearProducto(String id, String name, double price, String unit) {
        return new BebidaBuilder()
                .id(id)
                .name(name)
                .price(price)
                .unit(unit)
                .build();
    }
}
