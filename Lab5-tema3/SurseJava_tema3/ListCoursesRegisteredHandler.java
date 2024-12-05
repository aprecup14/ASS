/**
 * @(#)ListCoursesRegisteredHandler.java
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Handler pentru evenimentul "Lista cursurilor la care este inscris un student".
 */
public class ListCoursesRegisteredHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Lista cursurilor la care este inscris un student".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii     
    */
    public ListCoursesRegisteredHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Lista cursurilor la care este inscris un student".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        // Parsarea parametrilor.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID = objTokenizer.nextToken();

        // Preluarea listei cursurilor la care este inscris un student dat.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        if (objStudent == null) {
            return "ID student inexistent";
        }
        ArrayList vCourse = objStudent.getRegisteredCourses();

        // Construirea listei cu informatiile despre cursuri si returnarea ei.
        String sReturn = "";
        for (int i=0; i<vCourse.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
        }
        return sReturn;
    }
}