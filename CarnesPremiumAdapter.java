
package inventario.burgerhouse.adapter;
/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CarnesPremiumAdapter implements IProveedorStandard {
    
    private final ProveedorCarnesPremium proveedor;
    private static final String PROVEEDOR_NAME = "Carnes Premium S.A.";
    
    public CarnesPremiumAdapter(ProveedorCarnesPremium proveedor) {
        this.proveedor = proveedor;
    }
    
    @Override
    public String getProductId() {
        //- Adaptar el código del proveedor al formato del sistema
        return "F-" + proveedor.getCodigo_articulo().replace("CP-", "");
    }
    
    @Override
    public String getProductName() {
        return proveedor.getDescripcion();
    }
    
    @Override
    public double getProductPrice() {
        return proveedor.getValor_unitario();
    }
    
    @Override
    public String getProductUnit() {
        //- Normaliza las unidades del proveedor
        String medida = proveedor.getMedida().toLowerCase();
        switch (medida) {
            case "kg":
            case "kilogramo":
                return "kg";
            case "gr":
            case "gramo":
                return "gramos";
            default:
                return medida;
        }
    }
    
    @Override
    public int getProductQuantity() {
        return proveedor.getStock_disponible();
    }
    
    @Override
    public String getExpirationDate() {
        //-Convierte formato del dd/MM/yyyy al yyyy-MM-dd
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate fecha = LocalDate.parse(proveedor.getFecha_caducidad(), inputFormatter);
            return fecha.format(outputFormatter);
        } catch (Exception e) {
            //- Si falla, devuelve fecha por defecto (3 meses)
            return LocalDate.now().plusMonths(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
    
    @Override
    public String getProviderName() {
        return PROVEEDOR_NAME;
    }
}