public class ValidateStudentBalanceHandler extends CommandEventHandler {
    public static final Integer COURSE_PRICE = 1;

    /**
     * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
     *
     * @param objDataBase    referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de
     *                       procesat
     * @param iOutputEvCode  codul evenimentului de iesire, pentru transmiterea
     *                       rezultatului procesarii comenzii
     */
    public ValidateStudentBalanceHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Inscriere student la un curs".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        StudentRegistrationFormat info = StudentRegistrationFormat.From(param);

        if (info.hasError()) {
            return info.toString();
        }

        String studentId = info.getStudentId();
        String courseId = info.getCourseId();

        // Preluarea inregistrarilor despre student si curs.
        Student objStudent = this.objDataBase.getStudentRecord(studentId);
        Course objCourse = this.objDataBase.getCourseRecord(courseId);
        if (objStudent == null) {
            return StudentRegistrationFormat.CreateInvalid(studentId, courseId, "ID student inexistent").toString();
        }
        if (objCourse == null) {
            return StudentRegistrationFormat.CreateInvalid(studentId, courseId, "ID curs inexistent").toString();
        }

        if (objStudent.iBalance < COURSE_PRICE) {
            return StudentRegistrationFormat.CreateInvalid(studentId, courseId, String.format(
                    "Nu se poate realiza inscrierea la cursul %s a studentului %s datorita fondurilor insuficiente",
                    objCourse.sName, objStudent.sName)).toString();
        }

        return StudentRegistrationFormat.CreateValid(studentId, courseId).toString();
    }
}
