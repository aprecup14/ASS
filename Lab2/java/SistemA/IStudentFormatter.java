// Interfata folosita pentru a formata datele despre un student intr-un anumit fel dorit. Spre exemplu, daca se doreste
// scrierea intr-un fisier de output doar a numelui si specializarii, se poate crea o instanta a acestei interfete care formateaza datele despre student.
@FunctionalInterface
public interface IStudentFormatter {
    String format(Student student);
}
