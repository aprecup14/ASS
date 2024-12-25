// Construirea listei cu informatiile despre cursuri si returnarea ei.

/**
 * @(#)RegisterStudentHandler.java
 */

import java.util.ArrayList;

/**
 * Handler pentru evenimentul "Inscriere student la un curs".
 */
public class ValidatetRegistrationHandler extends CommandEventHandler {
    /**
     * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
     *
     * @param objDataBase    referinta la obiectul baza de date
     * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de
     *                       procesat
     * @param iOutputEvCode  codul evenimentului de iesire, pentru transmiterea
     *                       rezultatului procesarii comenzii
     */
    public ValidatetRegistrationHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Procesarea evenimentului asociat comenzii "Inscriere student la un curs".
     *
     * @param param sir de caractere - comanda
     * @return sir de caractere - rezultat procesare comanda
     */
    protected String execute(String param) {
        StudentRegistrationEventParam info = StudentRegistrationEventParam.From(param);
        String studentId = info.getStudentId();
        String courseId = info.getCourseId();
        // Preluarea inregistrarilor despre student si curs.
        Student objStudent = this.objDataBase.getStudentRecord(studentId);
        Course objCourse = this.objDataBase.getCourseRecord(courseId);
        if (objStudent == null) {
            return StudentRegistrationEventParam.CreateInvalid(studentId, courseId, "ID student inexistent").toString();
        }
        if (objCourse == null) {
            return StudentRegistrationEventParam.CreateInvalid(studentId, courseId, "ID curs inexistent").toString();
        }

        // Verificare conflicte intre cursul dat si orice alt curs la care studentul
        // este inscris.
        ArrayList vCourse = objStudent.getRegisteredCourses();
        for (int i = 0; i < vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                // Exista conflict.
                return StudentRegistrationEventParam.CreateInvalid(studentId, courseId, String.format(
                        "Conflicte la inscrierea la cursul %s pentru studentul %s", objCourse.sName, objStudent.sName))
                        .toString();
            }
        }

        return StudentRegistrationEventParam.CreateValid(studentId, courseId).toString();
    }
}