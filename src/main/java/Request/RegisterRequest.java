package Request;

/**
 * Represents a request for user registration.
 */
public class RegisterRequest {
    /**
     * The username for the registration request.
     */
    private String username;

    /**
     * The password for the registration request.
     */
    private String password;

    /**
     * The email for the registration request.
     */
    private String email;

    /**
     * The first name for the registration request.
     */
    private String firstName;

    /**
     * The last name for the registration request.
     */
    private String lastName;

    /**
     * The gender for the registration request.
     */
    private String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
