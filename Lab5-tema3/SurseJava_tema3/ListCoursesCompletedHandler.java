/**
 * @(#)ListCoursesCompletedHandler.java
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Handler pentru evenimentul "Lista cursurilor absolvite de un student".
 */
public class ListCoursesCompletedHandler extends CommandEventHandler {

    /**
     * Construirea handler-lui pentru evenimentul "Lista cursurilor absolvite de un student".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public ListCoursesCompletedHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Lista cursurilor absolvite de un student".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        // Parsarea parametrilor.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID = objTokenizer.nextToken();

        // Preluarea listei cursurilor absolvite de un student dat.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        if (objStudent == null) {
            return "ID student inexistent";
        }
        ArrayList vCourseID = objStudent.getCompletedCourses();

        // Construirea listei cu informatiile despre cursuri si returnarea ei.
        String sReturn = "";
        for (int i=0; i<vCourseID.size(); i++) {
            String sCID = (String) vCourseID.get(i);
            String sName = this.objDataBase.getCourseName(sCID);
            sReturn += (i == 0 ? "" : "\n") + sCID + " " + (sName == null ? "Unknown" : sName);
        }
        return sReturn;
    }
}