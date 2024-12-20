        // Construirea listei cu informatiile despre cursuri si returnarea ei.


/**
 * @(#)RegisterStudentHandler.java
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Handler pentru evenimentul "Inscriere student la un curs".
 */
public class RegisterStudentHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public RegisterStudentHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Inscriere student la un curs".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        // Parsarea parametrilor.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();

        // Cerere validata. Inscriere student la curs.
        this.objDataBase.makeARegistration(sSID, sCID);
        return String.format("%s %s", sSID, sCID);
    }
}