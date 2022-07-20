package api.requests;

public class AuthorizationRequest {
    private String grant_type;
    private String username;
    private String password;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String grant_type, String username, String password) {
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
