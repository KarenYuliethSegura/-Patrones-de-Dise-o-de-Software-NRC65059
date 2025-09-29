package inventario.burgerhouse.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 *       
 */
public class AlertaInventarioSubject {
 
    private final List<AlertaInventarioObserver> observers = new ArrayList<>();

    public void subscribe(AlertaInventarioObserver o) {
        observers.add(o);
    }

    public void unsubscribe(AlertaInventarioObserver o) {
        observers.remove(o);
    }

    public void notifyLowStock(String product_id, int stock) {
        for (var o : observers) {
            o.onLowStock(product_id, stock);
        }
    }

    public void notifyExpiringLot(String product_id, String lote_id, int days) {
        for (var o : observers) {
            o.onExpiringLot(product_id, lote_id, days);
        }
    }
}
