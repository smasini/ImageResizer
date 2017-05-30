package it.smasini.imageresizer.files;

/**
 * Created by Simone on 30/05/17.
 */
public class Renamer {

    private String fileName;

    private boolean replace = false;
    private String replacePattern = "";
    private String replaceWith = "";

    private boolean addAtStart = false;
    private String startString = "";

    private boolean addAtEnd = false;
    private String endString = "";

    public String rename(){
        String name = fileName;
        if(replace && !replacePattern.isEmpty()){
            name = name.replaceAll(replacePattern, replaceWith);
        }
        if(addAtStart){
            name = startString + name;
        }
        if(addAtEnd){
            name = name + endString;
        }
        return name.replaceAll(" ", "_").replaceAll("-", "_").toLowerCase();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public void setReplacePattern(String replacePattern) {
        this.replacePattern = replacePattern;
    }

    public void setReplaceWith(String replaceWith) {
        this.replaceWith = replaceWith;
    }

    public void setAddAtStart(boolean addAtStart) {
        this.addAtStart = addAtStart;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public void setAddAtEnd(boolean addAtEnd) {
        this.addAtEnd = addAtEnd;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }
}
