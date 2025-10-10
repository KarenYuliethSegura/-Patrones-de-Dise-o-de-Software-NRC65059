package inventario.burgerhouse.singleton;

/**
 *
 * @author yulieth segura
 */
import inventario.burgerhouse.domain.Lote;
import java.util.*;

public class GestorInventario {

    private static GestorInventario INSTANCE;

    // Mapa: product_id -> lista de lotes (ordenados por date_entry asc)
    private final Map<String, List<Lote>> inventario = new HashMap<>();

    private GestorInventario() { }

    public static synchronized GestorInventario getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestorInventario();
        }
        return INSTANCE;
    }

    // -- Registrar entrada aplicando PEPS
    public synchronized void registrar_entrada(Lote lote) {
        if (lote == null) {
            throw new IllegalArgumentException("No se puede registrar un lote vacío.");
        }
        String product_id = lote.getProduct_id();
        if (product_id == null || product_id.isEmpty()) {
            throw new IllegalArgumentException("product_id no puede ser vacío.");
        }
        inventario.computeIfAbsent(product_id, k -> new ArrayList<>()).add(lote);
        // Asegurar orden natural por fecha de ingreso (PEPS)
        Collections.sort(inventario.get(product_id));
    }

    // -- Registrar salida (consumo) aplicando PEPS
    public synchronized void registrar_salida(String product_id, int amount)
            throws ProductoNoEncontradoException, StockInsuficienteException {

        if (product_id == null || product_id.isEmpty()) {
            throw new IllegalArgumentException("product_id no puede ser vacío.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser > 0.");
        }

        List<Lote> lotes = inventario.get(product_id);
        if (lotes == null || lotes.isEmpty()) {
            throw new ProductoNoEncontradoException("No hay lotes para el producto: " + product_id);
        }

        int restante = amount;

        Iterator<Lote> it = lotes.iterator();
        while (it.hasNext() && restante > 0) {
            Lote l = it.next();

            if (l.is_expired()) {
                it.remove();
                continue;
            }

            int disponible = l.getAmount();
            if (disponible <= 0) {
                it.remove();
                continue;
            }

            if (disponible >= restante) {
                l.setAmount(disponible - restante);
                if (l.getAmount() == 0) it.remove();
                restante = 0;
            } else {
                restante -= disponible;
                it.remove();
            }
        }

        if (restante > 0) {
            throw new StockInsuficienteException(
                "Stock insuficiente (no vencido) para " + product_id + ". Faltante: " + restante
            );
        }

        if (lotes.isEmpty()) {
            inventario.remove(product_id);
        }
    }

    // -- Consulta de stock (NO vencido) por producto
    public synchronized int consultar_stock(String product_id) {
        List<Lote> lotes = inventario.get(product_id);
        if (lotes == null) return 0;
        int total = 0;
        for (Lote l : lotes) {
            if (!l.is_expired()) {
                total += l.getAmount();
            }
        }
        return total;
    }

    // -- Consulta de lotes activos (NO vencidos) ordenados por date_entry (PEPS)
    public synchronized List<Lote> consultar_lotes_activos(String product_id) {
        List<Lote> lotes = inventario.getOrDefault(product_id, Collections.emptyList());
        List<Lote> activos = new ArrayList<>();
        for (Lote l : lotes) {
            if (!l.is_expired()) {
                activos.add(l);
            }
        }
        Collections.sort(activos);
        return Collections.unmodifiableList(activos);
    }

    // -- Resumen de stock (NO vencido) por producto
    public synchronized Map<String, Integer> consultar_stock_total() {
        Map<String, Integer> resumen = new HashMap<>();
        for (Map.Entry<String, List<Lote>> e : inventario.entrySet()) {
            int total = 0;
            for (Lote l : e.getValue()) {
                if (!l.is_expired()) total += l.getAmount();
            }
            if (total > 0) {
                resumen.put(e.getKey(), total);
            }
        }
        return resumen;
    }

    // -- Utilidad para pruebas: limpia todo el inventario
    public synchronized void limpiar() {
        inventario.clear();
    }
}
