/**
 * @(#)ClientOutput.java
 */


import java.util.Observable;
import java.util.Observer;


/**
 * Clasa reprezinta o componenta client de iesire care este responsabila cu afisarea  * mesajelor text la consola atunci cand receptioneaza evenimente de tip SHOW. 
 * Evenimentele de tip SHOW sunt insotite de un parametru de tip String ce 
 * reprezinta sirul de caractere ce trebuie afisat. Aceasta componenta trebuie sa se  * inregistreze pentru receptionarea evenimentelor de tip SHOW, aceasta realizandu-se 
 * la crearea ei.
 */

public class ClientOutput implements Observer {
    private IOutputStrategy outputStrategy;

    /**
     * Construieste o componenta client de iesire. 
     * Noua componenta se inregistreaza pentru receptionarea evenimentelor de tip SHOW.
     */

    public ClientOutput(IOutputStrategy outputStrategy) {
        // Inregistrare cu evenimente SHOW.
        EventBus.subscribeTo(EventBus.EV_SHOW, this);
        this.outputStrategy = outputStrategy;
    }

    /**
     * Handler-ul de evenimente ale componetei client de iesire. 
     * La receptionarea evenimentului SHOW, obiectul de tip String primit 
     * ca parametru este afisat la consola.
     *
     * @param event un obiect eveniment. (atentie: nu va fi referit direct)
     * @param param un obiect parametru al evenimentului. (va fi "cast" la tipul de date      * corespunzator
     */
    public void update(Observable event, Object param) {
        this.outputStrategy.execute(param);
    }
}
