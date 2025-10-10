package inventario.burgerhouse.observer;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
public class AlertaConsolaObserver implements AlertaInventarioObserver {
    @Override
    public void onLowStock(String product_id, int stock) {
        System.out.println("[ALERTA!!] Stock bajo para " + product_id + ": " + stock + " unidades.");
    }

    @Override
    public void onExpiringLot(String product_id, String lote_id, int daysToExpire) {
        System.out.println("[ALERTA!!] El lote " + lote_id + " del producto " 
                           + product_id + " vence en " + daysToExpire + " días.");
    }
}
