
/**
 * @(#)SystemMain.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clasa ce contine metoda main a sistemului.
 */
class SystemMain {

	private final static String LOG_FILE_PATH = "./logs.txt";

	/**
	 * Creaza componentele si porneste sistemul.
	 * Se lanseaza cu doi parametri:
	 * numele fisierului ce contine datele despre studenti si
	 * numele fisierului ce contine datele despre cursuri.
	 */
	public static void main(String args[]) {
		// Verificarea numarului de parametri.
		if (args.length != 2) {
			System.err.println("Numar incorect de parametri");
			System.err.println(
					"Usage: java SystemMain <StudentFile> <CourseFile>");
			System.exit(1);
		}
		String studentFileName = args[0];
		String courseFileName = args[1];

		// Verificarea existentei fisierelor.
		if (new File(studentFileName).exists() == false) {
			System.err.println("Could not find " + studentFileName);
			System.exit(1);
		}
		if (new File(courseFileName).exists() == false) {
			System.err.println("Could not find " + courseFileName);
			System.exit(1);
		}

		try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
			writer.write(String.format("%s*******************New execution started*******************",
					System.lineSeparator()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Initializarea magistralei de evenimente.
		EventBus.initialize();

		// Crearea componentelor.
		try {
			DataBase db;
			db = new DataBase(studentFileName, courseFileName);

			ListAllStudentsHandler objCommandEventHandler1 = new ListAllStudentsHandler(
					db,
					EventBus.EV_LIST_ALL_STUDENTS,
					EventBus.EV_SHOW);
			ListAllCoursesHandler objCommandEventHandler2 = new ListAllCoursesHandler(
					db,
					EventBus.EV_LIST_ALL_COURSES,
					EventBus.EV_SHOW);
			ListStudentsRegisteredHandler objCommandEventHandler3 = new ListStudentsRegisteredHandler(
					db,
					EventBus.EV_LIST_STUDENTS_REGISTERED,
					EventBus.EV_SHOW);
			ListCoursesRegisteredHandler objCommandEventHandler4 = new ListCoursesRegisteredHandler(
					db,
					EventBus.EV_LIST_COURSES_REGISTERED,
					EventBus.EV_SHOW);
			ListCoursesCompletedHandler objCommandEventHandler5 = new ListCoursesCompletedHandler(
					db,
					EventBus.EV_LIST_COURSES_COMPLETED,
					EventBus.EV_SHOW);
			RegisterStudentHandler objCommandEventHandler6 = new RegisterStudentHandler(
					db,
					EventBus.EV_REGISTER_STUDENT,
					EventBus.EV_CHECK_COURSE_OVERBOOKED);
			CheckCourseOverbookedHandler overbookedCourseEventHandler = new CheckCourseOverbookedHandler(
					db,
					EventBus.EV_CHECK_COURSE_OVERBOOKED,
					EventBus.EV_SHOW);
			ValidatetRegistrationHandler validateRegistrationEventHandler = new ValidatetRegistrationHandler(
					db,
					EventBus.EV_VALIDATE_REGISTRATION,
					EventBus.EV_VALIDATE_STUDENT_BALANCE);
			ValidateStudentBalanceHandler validateStudentBalanceHandler = new ValidateStudentBalanceHandler(db,
					EventBus.EV_VALIDATE_STUDENT_BALANCE, EventBus.EV_REGISTER_STUDENT);
			ClientInput objClientInput = new ClientInput();
			// Afisarea parametrului event (un sir de caractere) pe stdout.
			ClientOutput objClientOutputInTerminal = new ClientOutput((param) -> System.out.println((String) param));
			// Afisarea parametrului event (un sir de caractere) in fisierul "logs.txt".
			ClientOutput objClientOutputInLogFile = new ClientOutput((param) -> {
				try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
					writer.write((String) param);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			// Lansarea sistemului.
			objClientInput.start();
		} catch (FileNotFoundException e) {
			// Afisare informatii pentru depanare.
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// Afisare informatii pentru depanare.
			e.printStackTrace();
			System.exit(1);
		}
	}
}
