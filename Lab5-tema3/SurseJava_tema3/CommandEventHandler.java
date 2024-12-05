/**
 * @(#)CommandEventHandler.java
 */

import java.util.Observable;
import java.util.Observer;


/**
 * Aceasta clasa este superclasa claselor handler de evenimente.  
 * Subclasele trebuie doar sa defineasca implementarea metodei 
 * <code>execute</code> pentru procesarea comenzii daca gestioneaza 
 * un eveniment si genereaza un eveniment de iesire.  
 */
abstract public class CommandEventHandler implements Observer {

    /**
     * Referinta la obiectul baza de date.
     */
    protected DataBase objDataBase;

    /**
     * Codul evenimentului de iesire ce va fi trimis ca rezultat al procesarii comenzii.
     */
    protected int iOutputEvCode;

    /**
     * Construire handler de eveniment asociat unei comenzi utilizator. 
     * La momentul crearii, acesta se aboneaza implicit la evenimentul dat.
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului de primire a comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire ce va fi trimis ca rezultat al procesarii comenzii
     */
    public CommandEventHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        // Abonarea la un eveniment.
        EventBus.subscribeTo(iCommandEvCode, this);

        // Memorarea referintei la baza de date si a numelui evenimentului de iesire.
        this.objDataBase = objDataBase;
        this.iOutputEvCode = iOutputEvCode;
    }

    /**
     * Procesarea evenimentelor receptionate. Rezultatul procesarii este anuntat 
     * sub forma de eveniment si este insotit de un mesaj sub forma de sir de caractere.
     *
     * @param event un obiect eveniment. (atentie: nu va fi referit in mod direct)
     * @param param un obiect parametru al evenimentului. (va fi "cast" la tipul de date corespunzator)
     */
    public void update(Observable event, Object param) {
        // Lansarea procesarii comenzii si anuntarea unui nou eveniment de iesire cu rezultatul executiei.
        EventBus.announce(this.iOutputEvCode, this.execute((String) param));
    }

    /**
     * Rutina de procesare a comenzii. Va fi personalizata de catre subclase.
     *
     * @param param un parametru sir de caractere pentru comanda
     * @return un sir vde caractere rezultat al procesarii comenzii
     */
    abstract protected String execute(String param);
}