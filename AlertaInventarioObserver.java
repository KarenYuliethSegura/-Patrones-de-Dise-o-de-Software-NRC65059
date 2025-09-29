package inventario.burgerhouse.observer;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
public interface AlertaInventarioObserver {
    void onLowStock(String product_id, int stock);
    void onExpiringLot(String product_id, String lote_id, int daysToExpire);
    
}
