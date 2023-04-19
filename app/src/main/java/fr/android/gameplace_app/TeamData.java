package fr.android.gameplace_app;

public class TeamData {
    private int id;
    private String teamName;
    private int coachId;
    private String leagueName;
    private String city;
    private String country;
    private int points;
    private byte[] matchImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public byte[] getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(byte[] matchImage) {
        this.matchImage = matchImage;
    }

    public TeamData(int id, String teamName, int coachId, String leagueName, String city, String country, int points, byte[] matchImage) {
        this.id = id;
        this.teamName = teamName;
        this.coachId = coachId;
        this.leagueName = leagueName;
        this.city = city;
        this.country = country;
        this.points = points;
        this.matchImage = matchImage;
    }

    @Override
    public String toString() {
        return "TeamData{" +
                "teamName='" + teamName + '\'' +
                ", country='" + country + '\'' +
                ", points=" + points +
                '}';
    }
}
