
package inventario.burgerhouse.adapter;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

//- Esta es la interfaz estandar que el sistema espera para importar datos de los proveedores
public interface IProveedorStandard {
    //- Obtener el ID del producto
    String getProductId();
    //- Obtener el nombre del producto
    String getProductName();
    //- Obtener el precio del producto
    double getProductPrice();
    //- Obtener la unidad del producto
    String getProductUnit();
    //- Obtener la cantidad disponible
    int getProductQuantity();
    //- Obtener la fecha de vencimiento
    String getExpirationDate();
    //- Obtener el proveedor
    String getProviderName();
}
