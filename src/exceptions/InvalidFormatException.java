package exceptions;

public class InvalidFormatException extends Throwable {

    private String format;

    public InvalidFormatException(){

    }

    public InvalidFormatException(String format){
        this.format = format;
    }

    public String toString(){
        return format + " doesn't match the correct format, try again";
    }
    
}
