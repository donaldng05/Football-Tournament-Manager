package java.com.footballtournament.domain.model;

public class Standing {
    private final Club club;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public Standing(Club club) {
        this.club = club;
        this.played = 0;
        this.won = 0;
        this.drawn = 0;
        this.lost = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.points = 0;
    }

    public void recordWin(int goalsFor, int goalsAgainst) {
        this.played++;
        this.won++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        this.points += 3;
    }

    public void recordDraw(int goalsFor, int goalsAgainst) {
        this.played++;
        this.drawn++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        this.points += 1;
    }

    public void recordLoss(int goalsFor, int goalsAgainst) {
        this.played++;
        this.lost++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public Club getClub() {
        return club;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }
}
