package fr.android.gameplace_app;

public class MatchStatisticsData {
    private int id;
    private int matchId;
    private int goalsScored;
    private int shotsOnTarget;
    private double possessionPercentage;
    private int foulsCommitted;

    public MatchStatisticsData(int id, int matchId, int goalsScored, int shotsOnTarget, double possessionPercentage, int foulsCommitted) {
        this.id = id;
        this.matchId = matchId;
        this.goalsScored = goalsScored;
        this.shotsOnTarget = shotsOnTarget;
        this.possessionPercentage = possessionPercentage;
        this.foulsCommitted = foulsCommitted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getShotsOnTarget() {
        return shotsOnTarget;
    }

    public void setShotsOnTarget(int shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    public double getPossessionPercentage() {
        return possessionPercentage;
    }

    public void setPossessionPercentage(double possessionPercentage) {
        this.possessionPercentage = possessionPercentage;
    }

    public int getFoulsCommitted() {
        return foulsCommitted;
    }

    public void setFoulsCommitted(int foulsCommitted) {
        this.foulsCommitted = foulsCommitted;
    }

}