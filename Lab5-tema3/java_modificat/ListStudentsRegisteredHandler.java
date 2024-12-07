/**
 * @(#)ListStudentsRegisteredHandler.java
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Handler pentru evenimentul "Lista studentilor inscrisi la un curs".
 */
public class ListStudentsRegisteredHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Lista studentilor inscrisi la un curs".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public ListStudentsRegisteredHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Lista studentilor inscrisi la un curs".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        // Parsarea parametrilor.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sCID     = objTokenizer.nextToken();

        // Preluarea listei studentilor inscrsi la un curs dat.
        Course objCourse = this.objDataBase.getCourseRecord(sCID);
        if (objCourse == null) {
            return "Id curs inexistent";
        }
        ArrayList vStudent = objCourse.getRegisteredStudents();

        // Construirea listei cu informatiile despre studenti si returnarea ei.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        return sReturn;
    }
}