import java.util.ArrayList;
import java.util.StringTokenizer;

public class CheckCourseOverbookedHandler extends CommandEventHandler {
    // Cand un curs are trei studenti sau mai mult este considerat suprasolicitat.
    private final int OVERBOOKED_AT_NUM_STUDENTS = 3;
    /**
     * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
     *
     * @param objDataBase referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
     * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
     */
    public CheckCourseOverbookedHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
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
        String sCID     = objTokenizer.nextToken();

        // Preluarea inregistrarilor despre curs.
        Course objCourse = this.objDataBase.getCourseRecord(sCID);
        if (objCourse == null) {
            return "ID curs inexistent";
        }

        if (objCourse.vRegistered.size() >= this.OVERBOOKED_AT_NUM_STUDENTS){
            return String.format("Cursul %s este suprasolicitat!", objCourse.sName);
        }

        return String.format("Cursul %s nu este suprasolicitat!", objCourse.sName);        
    }
}
