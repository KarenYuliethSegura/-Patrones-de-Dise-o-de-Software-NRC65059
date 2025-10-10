package inventario.burgerhouse.singleton;

/**
 *
 * @author dct-yulieth
 */
public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}