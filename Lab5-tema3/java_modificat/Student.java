/**
 * @(#)Student.java
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;


/**
 * Clasa reprezinta o inregistrare cu informatii despre un student. 
 * Clasa contine informatii personale ale studentului si 
 * ID-urile cursurilor pe care studentul le-a absolvit.
 * Clasa este construita dintr-un sir orientat pe campuri separate cu spatii.
 */
public class Student {

    /**
     * Sir reprezentand ID student.
     */
    protected String sSID;

    /**
     * Sir reprezentand nume student.
     */
    protected String sName;

    /**
     * Sir reprezentand specializare student.
     */
    protected String sSpecializare;

    /**
     * Lista cursurilor absolvite de student. Elementele listei sunt
     * obiecte de tip <code>String</code>, reprezentand ID-urile cursurilor absolvite.
     */
    protected ArrayList vCompleted;

    /**
     * Lista cursurilor la care este inscris studentul. Elementele listei sunt
     * obiecte de tip <code>Course</code>, reprezentand cursurile la care s-a inscris studentul.
     */
    protected ArrayList<Course> vRegistered;
    
    /**
     * Balanta contului studentului.
     */
    protected int iBalance;

    /**
     * Construieste un obiect de tip Student prin parsarea sirului dat. 
     * Argumentul <code>sInput</code> este un sir orientat pe campuri separate prin spatii. 
     * Primele trei campuri necesare sunt ID student, nume student si specializare. 
     * Urmatorul camp este balanta cntului studentului.
     * Urmeaza ID-urile cursurilor absolvite de student (daca exista).
     * Desi nu este necesar, evitati includerea de caractere "newline" 
     * in sirul <code>sInput</code>.
      *
     * @param sInput sirul ce trebuie parsat si care contine toate 
     * informatiile dintr-o inregistrare student.
     */
    public Student(String sInput)
    {
        // Pregatire pentru tokeniz-area sirului de intrare.
        StringTokenizer objTokenizer = new StringTokenizer(sInput);

        // Preluare ID student, nume, specializare si balanta contului.
        this.sSID     = objTokenizer.nextToken();
        this.sName    = objTokenizer.nextToken();
        this.sName    = this.sName + " " + objTokenizer.nextToken();
		this.sSpecializare = objTokenizer.nextToken();
		this.iBalance = Integer.parseInt(objTokenizer.nextToken());

       // Preluarea cursurilor absolvite de student.
        this.vCompleted = new ArrayList();
        while (objTokenizer.hasMoreTokens()) {
            this.vCompleted.add(objTokenizer.nextToken());
        }

       // Pregatire pentru memorarea cursurilor la care se va inscrie studentul.
        this.vRegistered = new ArrayList();
    }

    /**
      * Test if the given string <code>sSID</code> is equal to the ID of this student record.
      *
      * @param  sSID a string representing a student ID
      * @return <code>true</code> if <code>sSID</code> is equal to the ID of this student record
      * @see    #match(String,String)
      */
    public boolean match(String sSID) {
        return this.sSID.equals(sSID);
    }

    /**
     * Return the name of this student record.
     *
     * @return the first and last name of this student record
     */
    public String getName() {
        return this.sName;
    }

    /**
     * Return a list of courses this student has registered for.
     *
     * @return the courses this student has registered for as an <code>ArrayList</code> of
     *         <code>String</code>s 
     * @see    #getCompletedCourses()
     */
    public ArrayList getRegisteredCourses() {
        return this.vRegistered;
    }

    /**
     * Return a list of courses this student has completed.
     *
     * @return the courses this student has completed as an <code>ArrayList</code> of 
     *         <code>Course</code>s 
     * @see    #getRegisteredCourses()
     */
    public ArrayList getCompletedCourses() {
        return this.vCompleted;
    }

    /**
     * Register for a course.
     *
     * @param objCourse the reference to the course object to register for.
     * @see   Course#registerStudent(Student)
     */
    public void registerCourse(Course objCourse) {
        this.vRegistered.add(objCourse);
        // Consideram costul inscrierii la un curs ca fiind 1.
        this.iBalance--;
    }

    /**
     * Remove registration from a course.
     *
     * @param objCourse the reference to the course object to unregister for.
     */
    public void removeCourse(Course objCourse) {
        Object[] courses = this.vRegistered.toArray();
        int index = IntStream.range(0, this.vRegistered.size()).map(i -> this.vRegistered.size() - i - 1).filter(i -> ((Course) courses[i]).sCID == objCourse.sCID).findFirst().orElse(-1);
        if (index == -1){
            return;
        }

        this.vRegistered.remove(index);
        this.iBalance++;
    }

    /**
     * Returns a string representation of this student record. The resulting string will be in the
     * same format as the argument for the constructor of this class.
     *
     * @return a string representation of this student record
     * @see    #Student(String)
     * @see    Course#toString()
     */
    public String toString() {
        // Create a string to return using ID, name, and program.
        String sReturn = this.sSID + " " + this.sName + " " + this.sSpecializare + " " + this.iBalance;

        // Append the completed course numbers.
        for (int i=0; i<this.vCompleted.size(); i++) {
            sReturn = sReturn + " " + this.vCompleted.get(i).toString();
        }

        return sReturn;
    }
}
