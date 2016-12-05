package com.biel.dominatorarena.logic.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biel on 1/12/2016.
 */
public class SourceAnnotation {
    String type;
    List<String> args = new ArrayList<>();

    public SourceAnnotation(String type, List<String> args) {
        this.type = type;
        this.args = args;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
    public boolean isOfType(String s){
        return getType().equalsIgnoreCase(s);
    }
}
