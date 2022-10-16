package exceptions;

public class InvalidPlayerException extends Throwable {
    
    private String fn, ln;

    public InvalidPlayerException(){

    }

    public InvalidPlayerException(String ln, String fn){
        this.fn = fn;
        this.ln = ln;
    }
    
    public String toString(){
        return "The player " + ln + " " + fn + " doesn't exist, try again !";
    }
}
