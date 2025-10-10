package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.util.Iterator;
import java.util.Map;

public class FormatoJSON implements FormatoReporte {

    //- Buffer del JSON
    private final StringBuilder json = new StringBuilder();
    //- Control de indentacion
    private int nivel = 0;
    //- Estado de la seccion actual (encabezado abierto dentro de "datos")
    private boolean seccionAbierta = false;
    //- Controla la coma entre propiedades dentro de la seccion actual
    private boolean necesitaComaSeccion = false;
    //- Controla la coma entre secciones dentro de "datos"
    private boolean necesitaComaDatos = false;

    @Override
    public void iniciar(String titulo) {
        //- Raiz del documento
        json.append("{\n");
        nivel++;
        //- "titulo"
        propLinea("titulo", titulo, true);
        //- "formato"
        propLinea("formato", "JSON", false);
        //- "datos": {
        json.append(",\n");
        indent().append("\"datos\": {\n");
        nivel++;
        seccionAbierta = false;
        necesitaComaDatos = false;
    }

    @Override
    public void agregarEncabezado(String encabezado) {
        //- Si había una seccion abierta, cierrala antes de abrir la nueva
        cerrarSeccionSiAplica();

        //- Si no es la primera sección en "datos", agrega coma
        if (necesitaComaDatos) json.append(",\n");

        //- Abre la nueva sección nombrada
        indent().append("\"").append(esc(encabezado)).append("\": {\n");
        nivel++;
        seccionAbierta = true;
        necesitaComaSeccion = false;
        necesitaComaDatos = true;
    }

    @Override
    public void agregarFila(String clave, String valor) {
        //- Dentro de una seccion, imprime "clave": "valor"
        if (!seccionAbierta) {
            //- Si no hay seccion abierta, creamos una por defecto
            agregarEncabezado("Sección");
        }
        if (necesitaComaSeccion) json.append(",\n");
        indent().append("\"").append(esc(clave)).append("\": ");
        json.append("\"").append(esc(valor)).append("\"");
        necesitaComaSeccion = true;
    }

    @Override
    public void agregarTabla(String titulo, Map<String, Object> datos) {
        //- Tabla como objeto anidado dentro de la seccion actual
        if (!seccionAbierta) {
            agregarEncabezado("Seccion");
        }
        if (necesitaComaSeccion) json.append(",\n");
        indent().append("\"").append(esc(titulo)).append("\": {\n");
        nivel++;

        Iterator<Map.Entry<String, Object>> it = datos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            indent().append("\"").append(esc(e.getKey())).append("\": ");
            Object v = e.getValue();
            if (v instanceof Number || v instanceof Boolean) {
                json.append(String.valueOf(v));
            } else {
                json.append("\"").append(esc(String.valueOf(v))).append("\"");
            }
            if (it.hasNext()) json.append(",\n"); else json.append("\n");
        }

        nivel--;
        indent().append("}");
        necesitaComaSeccion = true;
    }

    @Override
    public void agregarSeparador() {
        //- No aplica en JSON
    }

    @Override
    public void finalizar() {
        //- Cierra seccion abierta si existe
        cerrarSeccionSiAplica();
        //- Cierra "datos"
        json.append("\n");
        nivel--;
        indent().append("}\n");
        //- Cierra raiz
        nivel--;
        json.append("}\n");
        System.out.println(json.toString());
    }

    @Override
    public String getNombreFormato() {
        return "JSON";
    }

    //- Utilidades internas

    //- Cierra la seccion actual si esta abierta
    private void cerrarSeccionSiAplica() {
        if (seccionAbierta) {
            json.append("\n");
            nivel--;
            indent().append("}");
            seccionAbierta = false;
        }
    }

    //- Escribe una propiedad en la raiz con control de "primera linea"
    private void propLinea(String clave, String valor, boolean primera) {
        if (!primera) json.append(",\n");
        indent().append("\"").append(esc(clave)).append("\": ");
        json.append("\"").append(esc(valor)).append("\"");
    }

    //- Indentacion por nivel
    private StringBuilder indent() {
        for (int i = 0; i < nivel; i++) json.append("  ");
        return json;
    }

    //- Escape minimo para JSON
    private String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}