package exceptions;

public class InvalidFieldException extends Throwable{

    private String field = "";

    public InvalidFieldException(){

    }

    public InvalidFieldException(String field){
        this.field = field;
    }
    
    public String toString(){
        return "The field " + field + " is incorrect, try again !";
    }
}
