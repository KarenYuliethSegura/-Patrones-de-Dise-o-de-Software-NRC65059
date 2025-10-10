package inventario.burgerhouse.observer;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlertaCapturadoraObserver implements AlertaInventarioObserver {

    private int lowStockCount = 0;
    private int expiringCount = 0;
    private final List<String> eventos = new ArrayList<>();

    @Override
    public void onLowStock(String product_id, int stock) {
        lowStockCount++;
        eventos.add("LOW_STOCK " + product_id + " -> " + stock);
    }

    @Override
    public void onExpiringLot(String product_id, String lote_id, int daysToExpire) {
        expiringCount++;
        eventos.add("EXPIRING " + product_id + " lote=" + lote_id + " en " + daysToExpire + " días");
    }

    public int getLowStockCount() { return lowStockCount; }
    public int getExpiringCount() { return expiringCount; }
    public int getTotalEventos() { return lowStockCount + expiringCount; }
    public List<String> getEventos() { return Collections.unmodifiableList(eventos); }

    public void reset() {
        lowStockCount = 0;
        expiringCount = 0;
        eventos.clear();
    }
}
