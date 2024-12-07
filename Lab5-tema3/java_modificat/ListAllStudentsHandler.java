/**
 * @(#)ListAllStudentsHandler.java
*/

import java.util.ArrayList;

/**
 * Handler pentru evenimentul "Lista studenti".
 */
public class ListAllStudentsHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Lista studenti".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public ListAllStudentsHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Lista studenti".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
ng
     */
    protected String execute(String param) {
        // Preluarea tuturor inregistrarilor despre studenti.
        ArrayList vStudent = this.objDataBase.getAllStudentRecords();

        // Construirea listei cu informatiile despre studenti si returnarea ei.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        return sReturn;
    }
}