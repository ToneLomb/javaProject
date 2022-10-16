public class Player {

    private int ID;
    private String fn,ln,sex,nationality;

    Player(int ID, String sex, String ln, String fn, String nationality){
        this.ID = ID;
        this.sex = sex;
        this.ln = ln;
        this.fn = fn;
        this.nationality = nationality;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

}
