package inventario.burgerhouse.domain;

/**
 *
 * @author dct-yulieth
 */
public class ProductoConserva extends Producto {
    //- Constructor
    public ProductoConserva(String id, String name, double price, Categoria category, String unit) {
        super(id, name, price, Categoria.CONSERVAS, unit);
        }
    //
    @Override
    public ProductoConserva clone(){
        return (ProductoConserva) super.clone();
    }
}
