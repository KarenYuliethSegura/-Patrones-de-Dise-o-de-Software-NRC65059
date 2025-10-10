
package inventario.burgerhouse.adapter;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VerdurasFrescasAdapter implements IProveedorStandard {
    
    private final ProveedorVerdurasFrescas proveedor;
    private static final String PROVEEDOR_NAME = "Verduras Frescas Ltda.";
    
    public VerdurasFrescasAdapter(ProveedorVerdurasFrescas proveedor) {
        this.proveedor = proveedor;
    }
    
    @Override
    public String getProductId() {
        //- Adapta el SKU del proveedor al formato del sistema
        return "F-" + proveedor.getSku().replace("VF-", "");
    }
    
    @Override
    public String getProductName() {
        return proveedor.getItemName();
    }
    
    @Override
    public double getProductPrice() {
        return proveedor.getPricePerUnit();
    }
    
    @Override
    public String getProductUnit() {
        //- Normaliza los tipos de empaque del proveedor
        String packageType = proveedor.getPackageType().toLowerCase();
        switch (packageType) {
            case "bunch":
                return "atado";
            case "piece":
            case "unit":
                return "unidad";
            case "bag":
                return "bolsa";
            default:
                return packageType;
        }
    }
    
    @Override
    public int getProductQuantity() {
        return proveedor.getAvailableQty();
    }
    
    @Override
    public String getExpirationDate() {
        //- Convierte formato yyyy/MM/dd a yyyy-MM-dd (ISO)
        try {
            String fechaOriginal = proveedor.getBestBeforeDate();
            return fechaOriginal.replace("/", "-");
        } catch (Exception e) {
            //- Si falla, devuelve fecha por defecto (1 semana para frescos)
            return LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
    
    @Override
    public String getProviderName() {
        return PROVEEDOR_NAME;
    }
}
