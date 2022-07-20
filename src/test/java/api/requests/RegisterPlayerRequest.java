package api.requests;

public class RegisterPlayerRequest {
    private String username;
    private String password_change;
    private String password_repeat;
    private String email;
    private String name;
    private String surname;
    private String currency_code;

    public RegisterPlayerRequest(String username, String password_change, String password_repeat, String email, String name, String surname, String currency_code) {
        this.username = username;
        this.password_change = password_change;
        this.password_repeat = password_repeat;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.currency_code = currency_code;
    }

    public RegisterPlayerRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_change() {
        return password_change;
    }

    public String getPassword_repeat() {
        return password_repeat;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCurrency_code() {
        return currency_code;
    }
}
