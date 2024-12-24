public class CheckCourseOverbookedHandler extends CommandEventHandler {
    // Cand un curs are trei studenti sau mai mult este considerat suprasolicitat.
    private final int OVERBOOK_LIMIT = 3;

    /**
     * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
     *
     * @param objDataBase    referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de
     *                       procesat
     * @param iOutputEvCode  codul evenimentului de iesire, pentru transmiterea
     *                       rezultatului procesarii comenzii
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
        StudentRegistrationFormat info = StudentRegistrationFormat.From(param);

        if (info.hasError()) {
            return info.toString();
        }

        String sCID = info.getCourseId();

        // Preluarea inregistrarilor despre curs.
        Course objCourse = this.objDataBase.getCourseRecord(sCID);
        if (objCourse == null) {
            return "ID curs inexistent";
        }

        if (objCourse.vRegistered.size() >= this.OVERBOOK_LIMIT) {
            return String.format("Studentul a fost inscris! \nCursul %s este suprasolicitat!", objCourse.sName);
        }

        return String.format("Studentul a fost inscris!", objCourse.sName);
    }
}
