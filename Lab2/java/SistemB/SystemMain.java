import java.io.*;

public class SystemMain {

    /**
     * Creaza componentele si porneste sistemul.
     * Sunt asteptati doi parametrii de intrare:
     * primul: numele fisierului de intrare ce contine inregistrarile
     * corespunzatoare studentilor candidati,
     * al doilea: numele fisierului de iesire ca va contine inregistrarile
     * studentilor acceptati.
     *
     * @param args array cu parametrii de intrare
     */
    public static void main(String[] args) {
        // Verificarea numarului parametrilor de intrare.
        if (args.length != 2) {
            System.out.println("Numar incorect de parametri");
            System.out.println("Utilizare corecta: java SystemMain <fisier_de_intrare> <fisier_de_iesire>");
            System.exit(1);
        }

        // Verificarea existentei fisierului de intrare.
        if (!new File(args[0]).exists()) {
            System.out.println("Could not find " + args[0]);
            System.exit(1);
        }

        // Vertificarea existentei directorului parinte al fisierului de iesire.
        // Crearea acestuia daca e necesar.
        File parentFile = new File(args[1]).getAbsoluteFile().getParentFile();
        if (!parentFile.exists() && !parentFile.mkdir()) {
            System.out.println("Nu s-a putut crea directorul parinte " + args[1]);
            System.exit(1);
        }

        try {
            // _____________________________________________________________________
            // Crearea si legarea componentelor si conectorilor
            // _____________________________________________________________________

            // Crearea conductelor (de fapt, a rolurilor).
            System.out.println("Controller: Creare conectori (roluri)...");
            PipedWriter objTemp;

            BufferedWriter role13456PassedSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader role13456PassedSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter role13456RejectedSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader role13456RejectedSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter role12333PassedSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader role12333PassedSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter roleFilterSplitIACDSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader roleFilterSplitIACDSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter roleFilterMergeAcceptedSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader roleFilterMergeAcceptedSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter roleFilterIACDStudentsSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader roleFilterIACDStudentsSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter roleFilterNonIACDStudentsSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader roleFilterNonIACDStudentsSync = new BufferedReader(new PipedReader(objTemp));

            BufferedWriter roleFilterCourse13222PassedSource = new BufferedWriter(objTemp = new PipedWriter());
            BufferedReader roleFilterCourse13222PassedSync = new BufferedReader(new PipedReader(objTemp));

            BufferedReader roleInputFileSync = new BufferedReader(new FileReader(args[0]));
            BufferedWriter roleOutputFileSource = new BufferedWriter(new FileWriter(args[1]));

            // Crearea filtrelor (transferul rolurilor ca parametrii, pentru a fi legati
            // la porturile fiecarui filtru).
            System.out.println("Controller: Creare componente ...");
            CourseFilter filterCourse13456 = new CourseFilter("13456", roleInputFileSync, role13456PassedSource,
                    role13456RejectedSource, 13456);
            CourseFilter filterCourse12333 = new CourseFilter("12333", role13456RejectedSync, role12333PassedSource,
                    null, 12333);
            MergeFilter filterMergeAccepted = new MergeFilter("Passed13456or12333", role13456PassedSync,
                    role12333PassedSync, roleFilterMergeAcceptedSource);
            SplitFilter filterSplitIACD = new SplitFilter("IACD", roleFilterMergeAcceptedSync,
                    roleFilterIACDStudentsSource, roleFilterNonIACDStudentsSource, "IACD");
            CourseFilter filterCourse13222 = new CourseFilter("13222", roleFilterNonIACDStudentsSync,
                    roleFilterCourse13222PassedSource, null, 13222);
            MergeFilter filterMergeFinal = new MergeFilter("Final merge", roleFilterIACDStudentsSync,
                    roleFilterCourse13222PassedSync, roleOutputFileSource);

            // _____________________________________________________________________
            // Executarea sistemului
            // _____________________________________________________________________

            // Start all filters.
            System.out.println("Controller: Pornire filtre ...");

            filterCourse13456.start();
            filterCourse12333.start();
            filterMergeAccepted.start();
            filterSplitIACD.start();
            filterCourse13222.start();
            filterMergeFinal.start();

            // Asteapta pana la terminarea datelor de pe lanturile conductelor si filtrelor.
            // Ordinea de verificare, de la intrare la iesire, este importanta pentru a
            // evita problemele de concurenta.
            // Analizati ce s-ar intampla daca lantul pipe-and-filter ar fi circular.
            while (filterCourse13456.busy() ||
                    role13456PassedSync.ready() ||
                    role13456RejectedSync.ready() ||
                    role12333PassedSync.ready() ||
                    roleFilterSplitIACDSync.ready() ||
                    roleFilterMergeAcceptedSync.ready() ||
                    roleFilterIACDStudentsSync.ready() ||
                    roleFilterNonIACDStudentsSync.ready() ||
                    roleFilterCourse13222PassedSync.ready() ||
                    filterCourse12333.busy() ||
                    filterMergeAccepted.busy() ||
                    filterSplitIACD.busy() ||
                    filterCourse13222.busy() ||
                    filterMergeFinal.busy()) {
                // Afiseaza un semnal de feedback signal si transfera controlul pentru
                // planifcarea altui fir de executie.
                System.out.print('.');
                Thread.yield();
            }

            // _____________________________________________________________________
            // Curatarea sistemului
            // _____________________________________________________________________

            // Distrugerea tuturor filtrelor.
            System.out.println("Controller: Distrugerea tuturor componentelor ...");

            filterCourse13456.interrupt();
            filterCourse12333.interrupt();
            filterMergeAccepted.interrupt();
            filterSplitIACD.interrupt();
            filterCourse13222.interrupt();
            filterMergeFinal.interrupt();

            // Verificarea faptului ca filtrele sunt distruse.
            while (filterCourse13456.isAlive() == false ||
                    filterCourse12333.isAlive() == false ||
                    filterMergeAccepted.isAlive() == false ||
                    filterSplitIACD.isAlive() == false ||
                    filterCourse13222.isAlive() == false ||
                    filterMergeFinal.isAlive() == false) {
                // Afiseaza un semnal de feedback si transfera controlul planificatorului de
                // fire de execuitie.
                System.out.print('.');
                Thread.yield();
            }

            // Distrugerea tuturor conductelor.
            System.out.println("Controller: Distrugerea tuturor conectorilor ...");
            roleInputFileSync.close();
            roleOutputFileSource.close();

            role13456PassedSync.close();
            role13456RejectedSync.close();
            role12333PassedSync.close();
            roleFilterSplitIACDSync.close();
            roleFilterMergeAcceptedSync.close();
            roleFilterIACDStudentsSync.close();
            roleFilterNonIACDStudentsSync.close();
            roleFilterCourse13222PassedSync.close();
            role13456PassedSource.close();
            role13456RejectedSource.close();
            role12333PassedSource.close();
            roleFilterSplitIACDSource.close();
            roleFilterMergeAcceptedSource.close();
            roleFilterIACDStudentsSource.close();
            roleFilterNonIACDStudentsSource.close();
            roleFilterCourse13222PassedSource.close();
        } catch (Exception e) {
            // Afisarea de informatii pentru debugging.
            System.out.println("Exceptie aparuta in SystemMain.");
            e.printStackTrace();
            System.exit(1);
        }

        // Final!
        System.out.println("Controller: Final!");
    }
}
