package api.requests;

public class ClientCredentialsRequest {
    public String grant_type;
    public String scope;

    public ClientCredentialsRequest(String grant_type, String scope) {
        this.grant_type = grant_type;
        this.scope = scope;
    }

    public ClientCredentialsRequest() {
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getScope() {
        return scope;
    }
}
