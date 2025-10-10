package inventario.burgerhouse.factory;

/**
 *
 * @author dct-yulieth
 */
import inventario.burgerhouse.domain.Categoria;


public class CategoriaFactoryProvider {

    public static AbstractCategoriaFactory getFactory(Categoria categoria) {
        switch (categoria) {
            case BEBIDAS:
                return new FabricaBebidas();
            case FRESCOS:
                return new FabricaFrescos();
            case CONSERVAS:
                return new FabricaConservas();
            case PANADERIA:
                return new FabricaPanaderia();
            default:
                throw new IllegalArgumentException("Categoría no soportada: " + categoria);
        }
    }
}