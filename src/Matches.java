public class Matches {
    private int Match;
    private String Date,Results,Player1,Player2,Court;

    public Matches(int Match, String Player2, String Player1, String Court, String Date, String Results) {
        this.Match = Match;
        this.Court = Court;
        this.Player2 = Player2;
        this.Player1 = Player1;
        this.Date = Date;
        this.Results = Results;
    }

    public int getMatch() {
        return Match;
    }

    public void setMatch(int match) {
        Match = match;
    }


    public String getCourt() {
        return Court;
    }

    public void setCourt(String court) {
        Court = court;
    }

    public String getPlayer2() {
        return Player2;
    }

    public void setPlayer2(String player2) {
        Player2 = player2;
    }

    public String getPlayer1() {
        return Player1;
    }

    public void setPlayer1(String player1) {
        Player1 = player1;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}