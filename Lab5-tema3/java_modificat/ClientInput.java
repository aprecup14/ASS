/**
 * @(#)ClientInput.java
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Clasa reprezinta o componenta client de intrare, care este responsabila 
 * cu preluarea intrarilor de la utilizator si transmiterea evenimentelor 
 * corespunzatoare comenzilor date de acesta. Aceste evenimente asociate comenzilor 
 * pot avea parametri de tip sir de caractere care sunt transferati impreuna cu evenimentul. 
 * Mai multi parametri sunt concatenati intr-un singur sir, separati cu spatii.
 */
public class ClientInput extends Thread {

    /**
     * Continuu sunt preluate intrari de la utilizator si 
     * sunt anuntate evenimente asociate comenzilor. 
     * Anunta, de asemenea, evenimente de afisare (show) 
     * pentru a solicita afisarea prompt-urilor de utilizare.
     */
    public void run() { 
        try {
            // Creare buffered reader utilizand fluxul de intrare al sistemului.
            BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Afisarea comenzilor disponibile si preluarea unei optiuni.
                EventBus.announce(EventBus.EV_SHOW, "\nSistem inscriere studenti\n");
                EventBus.announce(EventBus.EV_SHOW, "1) Lista studenti");
                EventBus.announce(EventBus.EV_SHOW, "2) Lista cursuri");
                EventBus.announce(EventBus.EV_SHOW, "3) Lista studentilor inscrisi la un curs");
                EventBus.announce(EventBus.EV_SHOW, "4) Lista cursurilor la care e inscris un student");
                EventBus.announce(EventBus.EV_SHOW, "5) Lista cursurilor absolvite de un student");
                EventBus.announce(EventBus.EV_SHOW, "6) Inscriere student la un curs");
                EventBus.announce(EventBus.EV_SHOW, "7) Este suprasolicitat cursul?");
                EventBus.announce(EventBus.EV_SHOW, "x) Exit");
                EventBus.announce(EventBus.EV_SHOW, "\nSelectati o optiune si apasati return >> ");
                String sChoice = objReader.readLine().trim();

                // Executia comenzii 1: Lista studenti.
                if (sChoice.equals("1")) {
                    // Anuntarea evenimentului asociat comenzii #1.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_LIST_ALL_STUDENTS, null);
                    continue;
                }

               // Executia comenzii 2: Lista cursuri.
                 if (sChoice.equals("2")) {
                    // Anuntarea evenimentului asociat comenzii #2.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_LIST_ALL_COURSES, null);
                    continue;
                }

                // Executia comenzii 3: Lista studenti inscrisi la un curs.
                if (sChoice.equals("3")) {
                    // Preluare ID curs de la utilizator.
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID curs si apasati return >> ");
                    String sCID = objReader.readLine().trim();
                    
                    // Anuntarea evenimentului asociat comenzii #3, cu transmitere ID curs.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_LIST_STUDENTS_REGISTERED, sCID);
                    continue;
                }

                // Executia comenzii 4: Lista cursurilor la care e inscris un student.
                if (sChoice.equals("4")) {
                    // Preluare ID student de la utilizator.
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID student si apasati return >> ");
                    String sSID = objReader.readLine().trim();

                    // Anuntarea evenimentului asociat comenzii #4, cu transmitere ID student.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_LIST_COURSES_REGISTERED, sSID);
                    continue;
                }

                // Executia comenzii 5: Lista cursurilor absolvite de un student.
                if (sChoice.equals("5")) {
                    // Preluare ID student de la utilizator.
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID student si apasati return >> ");
                    String sSID = objReader.readLine().trim();

                    // Anuntarea evenimentului asociat comenzii #5, cu transmitere ID student.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_LIST_COURSES_COMPLETED, sSID);
                    continue;
                }

                // Executia comenzii 6: Inregistrare student la un curs.
                if (sChoice.equals("6")) {
                    // Preluare ID student si ID curs de la utilizator.
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID student si apasati return >> ");
                    String sSID = objReader.readLine().trim();
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID curs si apasati return >> ");
                    String sCID = objReader.readLine().trim();
                    
                    // Anuntarea evenimentului asociat comenzii #5,
                    // cu transmitere ID student si ID curs.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_REGISTER_STUDENT, sSID + " " + sCID);
                    continue;
                }

                // Executia comenzii 6: Inregistrare student la un curs.
                if (sChoice.equals("7")) {
                    // Preluare ID student si ID curs de la utilizator.
                    EventBus.announce(EventBus.EV_SHOW, "\nIndicati ID curs si apasati return >> ");
                    String sCID = objReader.readLine().trim();
                    
                    // Anuntarea evenimentului asociat comenzii #5,
                    // cu transmitere ID student si ID curs.
                    EventBus.announce(EventBus.EV_SHOW, "\n");
                    EventBus.announce(EventBus.EV_CHECK_COURSE_OVERBOOKED, sCID);
                    continue;
                }

                // Terminare sesiune client.
                if (sChoice.equalsIgnoreCase("X")) {
                    break;
                }
            }

            // Eliberarea resurselor.
            objReader.close();
        }
        catch (Exception e) {
            // Afisarea informatiilor pentru depanare.
            e.printStackTrace();
            System.exit(1);
        }
    }
}
