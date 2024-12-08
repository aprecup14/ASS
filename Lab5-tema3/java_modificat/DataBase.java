/**
 * @(#)DataBase.java
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * O <code>DataBase</code> ofera acces la datele despre studenti si cursuri incluzand citirea informatiilor inregistrate si scrierea informatiilor de inscriere. DE observat ca versiunea curenta a bazei de date nu suporta screirea in fisierele cu inregsirarile despre studenti si despre cursuri. Oprirea sistemului duce la pierderea tuturor informatiilor de inscriere.
 */
public class DataBase {

    /**
     * O lista de obiecte <code>Student</code> continand informatii despre studenti.
     */
    protected ArrayList vStudent;

    /**
     * O lista de obiecte <code>Course</code> continand informatii despre cursuri.
     */
    protected ArrayList vCourse;

    /**
     * Construirea unei baze de date ce ofera acces la datele despre studenti si cursuri. Datele initiale sunt completate cu informatiile din inregistrarile aflate in cele doua fisiere date cu informatii despre studenti si despre cursuri. La momentul crearii, baza de date nu contine nici o informatie despre inscrieri.
     *
     * @param sStudentFileName numele fisierului cu inregistrari despre studenti
     * @param sCourseFileName numele fisierului cu inregistrari despre cursuri
     */
    public DataBase(String sStudentFileName, String sCourseFileName) 
           throws FileNotFoundException, IOException {
        // Deschiderea fisisrelor.
        BufferedReader objStudentFile = new BufferedReader(new FileReader(sStudentFileName));
        BufferedReader objCourseFile  = new BufferedReader(new FileReader(sCourseFileName));

        // Initializarea listelor de studenti si de cursuri.
        this.vStudent = new ArrayList();
        this.vCourse  = new ArrayList();

        // Popularea listelor de studenti si de cursuri.
        while (objStudentFile.ready()) {
            this.vStudent.add(new Student(objStudentFile.readLine()));
        }
        while (objCourseFile.ready()) {
            this.vCourse.add(new Course(objCourseFile.readLine()));
        }

        // Inchiderea fisierelor.
        objStudentFile.close();
        objCourseFile.close();
    }

    /**
     * Returnarea tuturor inregistrarilor depre studenti sub forma de lista.
     *
     * @return un <code>ArrayList</code> de obiecte <code>Student</code> 
     * continand inregistrarile despre studenti
     */
    public ArrayList getAllStudentRecords() {
        // Returnarea listei studentilor.
        return this.vStudent;
    }

    /**
     * Returnarea tuturor inregistrarilor despre cursuri sub forma de lista.
     *
     * @return un <code>ArrayList</code> de obiecte <code>Course</code> 
     * continand inregistrarile despre cursuri
     */
    public ArrayList getAllCourseRecords() {
        // Returnarea listei de cursuri.
        return this.vCourse;
    }

    /**
     * Returnarea unei inregistrari student a carui ID este egal cu un ID dat.
     *
     * @param  sSID ID student cautat
     * @return un obiect <code>Student</code> cu ID egal cu <code>sSID</code>.
     *         <code>null</code> daca nu exista
     */
    public Student getStudentRecord(String sSID) {
        // Cautare si returnare inregistrare student daca e gasita.
        for (int i=0; i<this.vStudent.size(); i++) {
            Student objStudent = (Student) this.vStudent.get(i);
            if (objStudent.match(sSID)) {
                return objStudent;
            }
        }

        // Return null daca nu exista.
        return null;
    }

    /**
     * Returnarea numelui studentului cu ID egal cu un ID dat.
     *
     * @param  sSID ID student cautat
     * @return un <code>String</code> reprezentand numele studentului sau <code>null</code> daca nu e gasit.
     */
    public String getStudentName(String sSID) {
        // Cautare si returnarea numelui studentului daca e gasit.
        for (int i=0; i<this.vStudent.size(); i++) {
            Student objStudent = (Student) this.vStudent.get(i);
            if (objStudent.match(sSID)) {
                return objStudent.getName();
            }
        }

        // Return null daca nu exista.
        return null;
    }

    /**
     * Returnarea inregistrarii unui curs cu ID egal cu un ID dat.
     *
     * @param  sCID ID curs cautat
     * @return un obiect <code>Course</code> cu ID egal cu <code>sCID</code> 
     * sau <code>null</code> daca nu exista.
     */
    public Course getCourseRecord(String sCID) {
        // Cautarea si returnarea inregistrarii cursului, daca e gasit.
        for (int i=0; i<this.vCourse.size(); i++) {
            Course objCourse = (Course) this.vCourse.get(i);
            if (objCourse.match(sCID)) {
                return objCourse;
            }
        }

        // Return null daca nu exista.
        return null;
    }

    /**
     * Returnarea numelui cursului cu ID egal cu un ID dat.
     *
     * @param  sCID ID curs cautat
     * @return un <code>String</code> reprezentand numele cursului 
     * sau <code>null</code> daca nu exista.
     */
    public String getCourseName(String sCID) {
        // Cautarea si returnarea numelui cursului, daca e gasit.
        for (int i=0; i<this.vCourse.size(); i++) {
            Course objCourse = (Course) this.vCourse.get(i);
            if (objCourse.match(sCID)) {
                return objCourse.getName();
            }
        }

        // Return null daca nu exista.
        return null;
    }

    /**
     * Realizarea unei inscrieri. Nu sunt verificate conflictele inainte de actualizarea bazei de date. 
     * Nu se intampla nimic daca nu este gasita o inregistrare student 
     * sau o inregistare curs corespunzatoare ID-urilor date.
     *
     * @param  sSID ID student ce se inscrie
     * @param  sCID ID curs la care se face inscrierea
     */
    public void makeARegistration(String sSID, String sCID) {
        // Gasirea inregistrarilor student si curs.
        Student objStudent = this.getStudentRecord(sSID);
        Course  objCourse  = this.getCourseRecord(sCID);

        // Realizarea inscrierii.
        if (objStudent != null && objCourse != null) {
            objStudent.registerCourse(objCourse);
            objCourse.registerStudent(objStudent);
        }
    }

    /**
     * Realizarea unei stergeri de la inscrierile la curs.
     * @param  sSID ID student ce trebuie sters.
     * @param  sCID ID curs la care se face stergerea.
     */
    public void removeARegistration(String sSID, String sCID) {
        // Gasirea inregistrarilor student si curs.
        Student objStudent = this.getStudentRecord(sSID);
        Course  objCourse  = this.getCourseRecord(sCID);

        // Realizarea inscrierii.
        if (objStudent != null && objCourse != null) {
            objStudent.removeCourse(objCourse);
            objCourse.removeStudent(objStudent);
        }
    }
}
