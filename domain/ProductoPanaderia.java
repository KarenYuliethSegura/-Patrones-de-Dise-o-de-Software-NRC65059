package inventario.burgerhouse.domain;

/**
 *
 * @author dct-yulieth
 */
public class ProductoPanaderia extends Producto {
    //- Constructor
    public ProductoPanaderia(String id, String name, double price, Categoria category, String unit) {
        super(id, name, price, Categoria.PANADERIA, unit);
        }
    //
    @Override
    public ProductoPanaderia clone(){
        return (ProductoPanaderia) super.clone();
    }
}
