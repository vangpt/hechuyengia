package hechuyengiaid3;

import java.util.ArrayList;

public class Attribute {

    String name;
    String[] value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public Attribute(String name, String[] value) {
        this.name = name;
        this.value = value;
    }

    public Attribute(String name) {
        this.name = name;
        this.value = new String[7];
    }

}
