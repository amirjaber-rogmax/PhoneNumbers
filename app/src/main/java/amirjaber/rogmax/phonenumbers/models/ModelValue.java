package amirjaber.rogmax.phonenumbers.models;

import java.util.List;

public class ModelValue {

    private String value;
    private String message;
    private List<Model> result;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Model> getResult() {
        return result;
    }

    public void setResult(List<Model> result) {
        this.result = result;
    }


}
