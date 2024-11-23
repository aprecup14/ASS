
import java.io.*;

/**
 * Un filtru de formatare are un port de intrare si un port de iesire.
 * El transfera la portul de iesire datele disponibile pe portul de intrare aplicand o formatare acestora. 
 */
public class OutputFormatterFilter extends Filter {

    /**
     * Portul de intrare.
     **/
    protected BufferedReader pInput;

    /**
     * Portul de iesire.
     **/
    protected BufferedWriter pOutput;

    private final IStudentFormatter formatter;

    /**
     * Construieste un filtru de formatare cu numele dat. 
     * Porturile sunt impachetate intr-un flux de caratere buffer-at.
     *
     * @param sName   sirul ce reprezinta numele filtrului
     * @param pInput1 portul de intrare al acestui filtru
     * @param pOutput portul de iesire al acestui filtru
     * @param IStudentFormatter Formatarea care va fi scrisa in portul de iesire al acestui filtru.
     */
    public OutputFormatterFilter(String sName,
                       BufferedReader pInput, BufferedWriter pOutput, IStudentFormatter formatter) {
        // Executarea constructorului clasei parinte.
        super(sName);

        // Initializarea porturilor de intrare si de iesire.
        this.pInput = pInput;
        this.pOutput = pOutput;
        this.formatter = formatter;
    }

    /**
     * Precizeaza daca datele sunt disponibile pe portul de intrare.
     *
     * @return <code>true</code> daca si numai daca acest filtru 
     * poate citi date de la portul de intrare.
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
        Student objStudent = new Student(pInput.readLine());

        // Scrierea inregistrarii formatate la portul de iesire.
        this.pOutput.write(formatter.format(objStudent));
        this.pOutput.newLine();
        this.pOutput.flush();
    }
}
