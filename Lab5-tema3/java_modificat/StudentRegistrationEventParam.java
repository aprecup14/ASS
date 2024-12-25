import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clasa creata pentru a usura transformarea din String in formatul asteptat de componentele care realizeaza flow-ul pentru inregistrarea unui student.
public class StudentRegistrationEventParam {
    private final String courseId;

    private final String studentId;

    private final Optional<String> errorMessage;

    // The Regex representing the valid format for the Student Registration flow.
    // "%s %s %s", where the third string is optional.
    private static final String REGEX = "^[^\\s]+ [^\\s]+( .*)?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private StudentRegistrationEventParam(String studentId, String courseId, Optional<String> errorMessage) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.errorMessage = errorMessage;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", this.studentId, this.courseId,
                this.errorMessage.isPresent() ? this.errorMessage.get() : "");
    }

    public static StudentRegistrationEventParam From(String eventParam) {
        if (!IsValidFormat(eventParam)) {
            throw new IllegalArgumentException("Input does not match the required format: '%s %s %s'.");
        }

        String[] parts = eventParam.split(" ", 3);

        return new StudentRegistrationEventParam(parts[0], parts[1],
                Optional.ofNullable((parts.length > 2) && !parts[2].trim().equals("") ? parts[2] : null));
    }

    public static StudentRegistrationEventParam CreateValid(String studentId, String courseId) {
        return new StudentRegistrationEventParam(studentId, courseId, Optional.empty());
    }

    public static StudentRegistrationEventParam CreateInvalid(String studentId, String courseId, String errorMessage) {
        return new StudentRegistrationEventParam(studentId, courseId, Optional.of(errorMessage));
    }

    private static boolean IsValidFormat(String eventParam) {
        Matcher matcher = PATTERN.matcher(eventParam);
        return matcher.matches();
    }

    public boolean hasError() {
        return this.errorMessage.isPresent();
    }

}
