package inventario.burgerhouse.domain;

/**
 *
 * @author dct-yulieth
 */
public class Bebida extends Producto {
    //- Constructor
    public Bebida(String id, String name, double price, Categoria category, String unit) {
        super(id, name, price, Categoria.BEBIDAS, unit);
        }
    //
    @Override
    public Bebida clone(){
        return (Bebida) super.clone();
    }
}
