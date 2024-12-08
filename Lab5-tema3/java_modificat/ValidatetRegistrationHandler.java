        // Construirea listei cu informatiile despre cursuri si returnarea ei.


/**
 * @(#)RegisterStudentHandler.java
 */

 import java.util.ArrayList;
 import java.util.StringTokenizer;
 
 
 /**
  * Handler pentru evenimentul "Inscriere student la un curs".
  */
 public class ValidatetRegistrationHandler extends CommandEventHandler {
 
     /**
      * Construirea handler-lui pentru evenimentul "Inscriere student la un curs".
      *
      * @param objDataBase referinta la obiectul baza de date
      * @param iCommandEvCode codul evenimentului pentru receptionarea comenzilor de procesat
      * @param iOutputEvCode codul evenimentului de iesire, pentru transmiterea rezultatului procesarii comenzii
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
         // Parsarea parametrilor.
         StringTokenizer objTokenizer = new StringTokenizer(param);
         String sSID     = objTokenizer.nextToken();
         String sCID     = objTokenizer.nextToken();
 
         // Preluarea inregistrarilor despre student si curs.
         Student objStudent = this.objDataBase.getStudentRecord(sSID);
         Course objCourse = this.objDataBase.getCourseRecord(sCID);
         if (objStudent == null) {
            this.objDataBase.removeARegistration(sSID, sCID); 
             return "ID student inexisent";
         }
         if (objCourse == null) {
            this.objDataBase.removeARegistration(sSID, sCID); 
             return "ID curs inexistent";
         }
 
         // Verificare conflicte intre cursul dat si orice alt curs la care studentul este inscris.
         ArrayList vCourse = objStudent.getRegisteredCourses();
         // Ultimul curs va fi cel mai recent adaugat (si neverifcat pentru conflicte inca).
         for (int i=0; i<vCourse.size() - 1; i++) {
             if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                // Exista conflict. Inregistrarea va fi stearsa.
                this.objDataBase.removeARegistration(sSID, sCID); 
                return String.format("Conflicte la inscrierea la cursul %s pentru studentul %s.", objCourse.sName, objStudent.sName);
             }
         }

         if (objStudent.iBalance < 0){
            this.objDataBase.removeARegistration(sSID, sCID); 
            return String.format("Nu se poate realiza inscrierea la cursul %s a studentului %s datorita fondurilor insuficiente.", objCourse.sName, objStudent.sName);
         }
 
         return "Inregistrare la curs validata!";
     }
 }