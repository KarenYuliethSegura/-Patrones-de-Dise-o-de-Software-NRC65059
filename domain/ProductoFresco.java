
package inventario.burgerhouse.domain;

/**
 *
 * @author yulieth segura
 */
public class ProductoFresco extends Producto {
    //- Constructor
    public ProductoFresco(String id, String name, double price, Categoria category, String unit) {
        super(id, name, price, Categoria.FRESCOS, unit);
        }
    //
    @Override
    public ProductoFresco clone(){
        return (ProductoFresco) super.clone();
    }
}
