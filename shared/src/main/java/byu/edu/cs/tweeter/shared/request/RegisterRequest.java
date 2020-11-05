package byu.edu.cs.tweeter.shared.request;

public class RegisterRequest {
    private String name;
    private String alias;
    private String password;
    private String imageData;

    public RegisterRequest(String name, String alias, String password, String imageData) {
        this.name = name;
        this.alias = alias;
        this.password = password;
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public String getImageData() {
        return imageData;
    }
}

