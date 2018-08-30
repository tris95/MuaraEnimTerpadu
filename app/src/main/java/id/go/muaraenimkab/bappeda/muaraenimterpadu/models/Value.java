package id.go.muaraenimkab.bappeda.muaraenimterpadu.models;

import java.util.List;

public class Value<T> {
    private int success;
    private String message;
    private List<T> data;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
        return data;
    }
}
