package api.responses;

public class NotFoundError {
    private String name;
    private String message;
    private Integer code;
    private Integer status;

    public NotFoundError() {
    }

    public NotFoundError(String name, String message, Integer code, Integer status) {
        this.name = name;
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getStatus() {
        return status;
    }
}
