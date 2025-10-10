
package inventario.burgerhouse.adapter;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
public class ProveedorVerdurasFrescas {
    
    //- Formato estilo JSON del proveedor (incompatible)
    private String sku;
    private String itemName;
    private double pricePerUnit;
    private String packageType;
    private int availableQty;
    private String bestBeforeDate;
    
    public ProveedorVerdurasFrescas(String sku, String itemName, 
                                     double pricePerUnit, String packageType,
                                     int availableQty, String bestBeforeDate) {
        this.sku = sku;
        this.itemName = itemName;
        this.pricePerUnit = pricePerUnit;
        this.packageType = packageType;
        this.availableQty = availableQty;
        this.bestBeforeDate = bestBeforeDate;
    }
    
    //- Metodos con nomenclatura estilo proveedor (formato del proveedor)
    public String getSku() {
        return sku;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public double getPricePerUnit() {
        return pricePerUnit;
    }
    
    public String getPackageType() {
        return packageType;
    }
    
    public int getAvailableQty() {
        return availableQty;
    }
    
    public String getBestBeforeDate() {
        return bestBeforeDate;
    }
    
    @Override
    public String toString() {
        return "ProveedorVerdurasFrescas{" +
                "sku='" + sku + '\'' +
                ", item='" + itemName + '\'' +
                ", price=" + pricePerUnit +
                '}';
    }
}