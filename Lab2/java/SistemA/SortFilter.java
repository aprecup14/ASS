
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Un filtru de sortare are un port de intrare si un port de iesire.
 * El transfera la portul de iesire datele disponibile pe portul de intrare. Are
 * nevoie de toate datele de la portul de intrare pentru a putea aplica
 * sortarea.
 */
public class SortFilter extends Filter {

    /**
     * Portul de intrare.
     **/
    protected BufferedReader pInput;

    /**
     * Portul de iesire.
     **/
    protected BufferedWriter pOutput;

    private ArrayList<Student> students;

    private Comparator<Student> comparer;

    /**
     * Construieste un filtru de formatare cu numele dat.
     * Porturile sunt impachetate intr-un flux de caratere buffer-at.
     *
     * @param sName    sirul ce reprezinta numele filtrului
     * @param pInput1  portul de intrare al acestui filtru
     * @param pOutput  portul de iesire al acestui filtru
     * @param comparer Comparatorul folosit pentru ordonarea studentilor.
     */
    public SortFilter(String sName,
            BufferedReader pInput, BufferedWriter pOutput, Comparator<Student> comparer) {
        // Executarea constructorului clasei parinte.
        super(sName);

        // Initializarea porturilor de intrare si de iesire.
        this.pInput = pInput;
        this.pOutput = pOutput;

        this.students = new ArrayList<Student>();
        this.comparer = comparer;
    }

    /**
     * Precizeaza daca datele sunt disponibile pe portul de intrare.
     *
     * @return <code>true</code> daca si numai daca acest filtru
     *         poate citi date de la portul de intrare.
     * @throws IOException
     */
    protected boolean ready() throws IOException {
        return this.pInput.ready();
    }

    /**
     * Citeste date disponibile la portul de intrare si
     * scrie date noi formatate la portul de iesire.
     *
     * @throws IOException
     */
    protected void work() throws IOException {
        // Trebuie agregat toti studentii de la portul de intrare.
        String line;
        while ((line = pInput.readLine()) != null) {
            Student objStudent = new Student(line);
            this.students.add(objStudent);
            if (!pInput.ready()) {
                try {
                    // Sleep pentru a permite ca alti studenti sa ajunga la portul de intrare.
                    Thread.sleep(100);
                    if (!pInput.ready()) {
                        // Daca nu au mai aparut studenti noi pe durata opririi rularii acestui thread
                        // consideram ca toti studentii au fost parsati.
                        // Trecem la partea de sortare si scrierea inregistrarilor la portul de output.
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }

        Collections.sort(this.students, comparer);

        this.students.forEach(s -> {
            try {
                this.pOutput.write(s.toString());
                this.pOutput.newLine();
                this.pOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
