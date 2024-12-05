/**
 * @(#)ListAllCoursesHandler.java
 */

import java.util.ArrayList;


/**
 * Handler pentru evenimentul "Lista cursuri".
 */
public class ListAllCoursesHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Lista cursuri".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public ListAllCoursesHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Lista cursuri".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        // Preluarea tuturor inregistrarilor despre cursuri.
        ArrayList vCourse = this.objDataBase.getAllCourseRecords();

        // Construirea listei cu informatiile despre cursuri si returnarea ei.
        String sReturn = "";
        for (int i=0; i<vCourse.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
        }
        return sReturn;
    }
}