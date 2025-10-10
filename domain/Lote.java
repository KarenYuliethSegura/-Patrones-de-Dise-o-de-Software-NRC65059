
package inventario.burgerhouse.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author yulieth segura
 */
public class Lote implements Comparable<Lote>{
    //- Declaracion de variables.
    private String id;  //- id unico de lote 
    private String product_id; //- id del producto
    private LocalDate date_entry; //- Fecha de ingreso al inventario
    private LocalDate date_exp; //- fecha de vencimiento
    private int amount; //- cantidad disponible en el lote
    //- Constructor
    public Lote(String id, String product_id, LocalDate date_entry, LocalDate date_exp, int amount){
        this.id = id;
        this.product_id = product_id;
        this.date_entry = date_entry;
        this.date_exp = date_exp;
        this.amount = amount;
    }
    //- Funciones getters
    public String getId() {return id;}
    public String getProduct_id() {return product_id;}
    public LocalDate getDate_entry() {return date_entry;}
    public LocalDate getDate_exp() {return date_exp;}
    public int getAmount() {return amount;}
    //-Seteo de amount (cantidad)
    public void setAmount(int amount) {this.amount = amount;}
    
    public boolean is_expired() {
        return LocalDate.now().isAfter(date_exp);
    }
    @Override
    public int compareTo(Lote other){
        return this.date_entry.compareTo(other.date_entry);
        }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Lote)) return false;
        Lote other = (Lote) obj;
        return id.equals(other.id);
    }
    @Override 
    public int hashCode(){
        return Objects.hash(id);
    }
}
