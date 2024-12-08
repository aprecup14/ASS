/**
 * @(#)EventBus.java
 */


import java.util.Observable;
import java.util.Observer;


/**
 * Clasa ofera mecanismul de abonare (subscribe) la si de anuntare (publish) de evenimente. 
 * Inainte de utilizarea lor, evenimentele trebuie definite sub forma de coduri numerice. 
 * Intern, clasele <code>Observer</code>/<code>Observable</code> 
 * sunt utilizate pentru implementare, adica transferul de evenimente 
 * intre doua componente functioneaza (deocamdata) doar in cadrul 
 * unui singur spatiu de adresare. De observat ca nici una dintre 
 * componente nu este de tip <code>Observable</code>. Aceasta asigura ca 
 * nici o componenta nu este constienta de existenta altor componente si 
 * ca este posibila abonarea la un eveniment chiar daca nu exista nici o componenta ce-l poate anunta.
 */
public class EventBus {

    /**
     * Aceasta clasa impacheteaza clasa <code>Observable</code>. 
     * Ea ofera o modalitate de a seta din exterior indicatorul "changed" 
     * al unui obiect <code>Observable</code>.
     */
    public static class Event extends Observable {

        /**
         * Setarea indicatorului "changed" al acestui eveniment.
         */
        public void getDirty() {
            super.setChanged();
        }
    }

    /**
     * Definitia evenimentului "afiseaza".
     */
    public static final int EV_SHOW = 0;

    /**
     * Definitia evenimentului asociat comenzii #1 : lista studenti. 
     */
    public static final int EV_LIST_ALL_STUDENTS = 1;

    /**
     * Definita evenimentului asociat comenzii #2 : lista cursuri. 
     */
    public static final int EV_LIST_ALL_COURSES = 2;

    /**
     * Definita evenimentului asociat comenzii #3 : lista studentilor inscrisi la un curs. 
     */
    public static final int EV_LIST_STUDENTS_REGISTERED = 3;

    /**
     * Definita evenimentului asociat comenzii #4 : lista cursurilor la care este inscris un student. 
     */
    public static final int EV_LIST_COURSES_REGISTERED = 4;

    /**
     * Definita evenimentului asociat comenzii #5 : lista cursurilor absolvite de un student. 
     */
    public static final int EV_LIST_COURSES_COMPLETED = 5;

    /**
     * Definita evenimentului asociat comenzii #6 : inscriere student la un curs. 
     */
    public static final int EV_REGISTER_STUDENT = 6;

    /**
     * Definita evenimentului asociat comenzii #7 : verificarea daca un curs este suprasolicitat.
     */
    public static final int EV_CHECK_COURSE_OVERBOOKED = 7;

    /**
     * Definitia evenimentului pentru inceperea validarii unei inscrieri.
     */
    public static final int EV_VALIDATE_REGISTRATION = 8;

    /**
     * Numarul evenimentelor definite.
     */
    public static final int MAX_NUM_EVENTS  = 100;

    /**
     * Un array ce contine obiecte eveniment pentru toate evenimentele definite.
     */
    protected static Event[] aEvent = new Event[MAX_NUM_EVENTS];

    /**
     * Initializarea magistralei de evenimente. 
     * Abonamentele existente (maparile event/receiver) sunt sterse.
     */
    public static void initialize() {
        for (int i=0; i<MAX_NUM_EVENTS; i++) {
            aEvent[i] = new Event();
        }
    }

    /**
     * Abonarea la un eveniment. Prin apelarea acestei metode se asigura faptul ca 
     * metoda <code>update</code> a obiectului <code>objSubscriber</code>, 
     * care este un obiect <code>Observer</code>, va fi invocata 
     * ori de cate ori este anuntat un eveniment <code>iEventCode</code>.
     *
     * @param iEventCode evenimentul la care se face abonarea
     * @param objSubscriber abonat pe care va fi invocata metoda <code>update</code>.
     */
    public static void subscribeTo(int iEventCode, Observer objSubscriber) {
        aEvent[iEventCode].addObserver(objSubscriber);
    }

    /**
     * Anuntarea unui eveniment. Ca rezultat, este invocata metoda <code>update</code> 
     * pe toate obiectele <code>Observer</code> care s-au abonat la evenimentul 
     * <code>iEventCode</code>. Daca exista mai multi abonati ordinea de invocare este arbitrara. 
     *
     * @param iEventCode evenimentul de anuntat
     * @param sEventParam evenimentul sub forma de sir de caractere
     */
    public static void announce(int iEventCode, String sEventParam) {
        aEvent[iEventCode].getDirty();
        aEvent[iEventCode].notifyObservers(sEventParam);
    }
}
