package api.responses;

public class Player {
    private Integer id;
    private Object country_id;
    private Object timezone_id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private Object gender;
    private Object phone_number;
    private Object birthdate;
    private boolean bonuses_allowed;
    private boolean is_verified;

    public Player() {
    }

    public Player(Integer id, Object country_id, Object timezone_id, String username, String email, String name,
                  String surname, Object gender, Object phone_number, Object birthdate,
                  boolean bonuses_allowed, boolean is_verified) {
        this.id = id;
        this.country_id = country_id;
        this.timezone_id = timezone_id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone_number = phone_number;
        this.birthdate = birthdate;
        this.bonuses_allowed = bonuses_allowed;
        this.is_verified = is_verified;
    }

    public Integer getId() {
        return id;
    }

    public Object getCountry_id() {
        return country_id;
    }

    public Object getTimezone_id() {
        return timezone_id;
    }

    public String getUsername() {
        return username;
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

    public Object getGender() {
        return gender;
    }

    public Object getPhone_number() {
        return phone_number;
    }

    public Object getBirthdate() {
        return birthdate;
    }

    public boolean isBonuses_allowed() {
        return bonuses_allowed;
    }

    public boolean isIs_verified() {
        return is_verified;
    }
}
