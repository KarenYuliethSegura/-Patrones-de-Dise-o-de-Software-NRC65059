
package inventario.burgerhouse.observer;

import inventario.burgerhouse.domain.Lote;
import inventario.burgerhouse.singleton.GestorInventario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
public class AuditorInventario {
    private final GestorInventario inv = GestorInventario.getInstance();
    private final AlertaInventarioSubject subject;
    private final int lowStockThreshold;
    private final int daysToExpireThreshold;

    public AuditorInventario(AlertaInventarioSubject subject,
                             int lowStockThreshold,
                             int daysToExpireThreshold) {
        this.subject = subject;
        this.lowStockThreshold = lowStockThreshold;
        this.daysToExpireThreshold = daysToExpireThreshold;
    }

    public void evaluar(String product_id) {
        int stock = inv.consultar_stock(product_id);
        if (stock > 0 && stock <= lowStockThreshold) {
            subject.notifyLowStock(product_id, stock);
        }

        List<Lote> activos = inv.consultar_lotes_activos(product_id);
        for (Lote l : activos) {
            if (l.getDate_exp() != null) {
                long dias = ChronoUnit.DAYS.between(LocalDate.now(), l.getDate_exp());
                if (dias >= 0 && dias <= daysToExpireThreshold) {
                    subject.notifyExpiringLot(product_id, l.getId(), (int) dias);
                }
            }
        }
    }
}
