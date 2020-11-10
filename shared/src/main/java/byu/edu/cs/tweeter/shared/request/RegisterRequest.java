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

    public RegisterRequest(String name, String alias, String password) {
        this.name = name;
        this.alias = alias;
        this.password = password;
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

    public RegisterRequest() {
        name = null;
        alias = null;
        password = null;
        imageData = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}

