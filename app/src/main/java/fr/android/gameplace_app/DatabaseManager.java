package fr.android.gameplace_app;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Sportapp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String match = "CREATE TABLE IF NOT EXISTS matches ( id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, CreationDate DATE, location TEXT, latitude REAL, longitude REAL, street_name TEXT, image BLOB)";
        String stats = "CREATE TABLE IF NOT EXISTS match_statistics (id INTEGER PRIMARY KEY AUTOINCREMENT, match_id INTEGER, goals_scored INTEGER, shots_on_target INTEGER, possession_percentage REAL, fouls_committed INTEGER, FOREIGN KEY (match_id) REFERENCES matches (id))";
        String coach = "CREATE TABLE IF NOT EXISTS coaches (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, email TEXT, password TEXT)";
        String team = "CREATE TABLE IF NOT EXISTS teams (id INTEGER PRIMARY KEY AUTOINCREMENT, team_name TEXT, coach_id INTEGER, league_name TEXT, city TEXT, country TEXT, points INTEGER, match_image BLOB, FOREIGN KEY (coach_id) REFERENCES coaches (id))";
        String player = "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, age INTEGER, team_id INTEGER, FOREIGN KEY (team_id) REFERENCES team (id))";
//       String match =        "create table T_Scores ("
//                        + "       idScore integer primary key autoincrement," +
//                        "        name text not null," +
//                        "        score integer not null," +
//                        "        when_ integer not null)";




        db.execSQL(match);
        db.execSQL(stats);
        db.execSQL(coach);
        db.execSQL(team);
        db.execSQL(player);
        Log.i( "DATABASE", "onCreate invoked" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion) {
        String strSql = "drop table teams";
        db.execSQL(strSql);
        this.onCreate(db);
        Log.i("DATABASE", "onUpgrade invoked");
    }

    public void insertCoach(String first_name, String last_name, String email, String password){
        first_name = first_name.replace("'", "''");
//        String strlSql = "insert into T_Scores (name, score, when_) values ('"
//                + name + "', " + score + ", " + new Date().getTime() + ")";
        String strlSql = "INSERT INTO coaches (first_name, last_name, email, password) values ('"
                + first_name + "', '" + last_name + "', '" + email + "', '" + email + "')";
        this.getWritableDatabase().execSQL(strlSql);
        Log.i("DATABASE","insertCoach invoked");
    }

    public void insertTeam(String team_name, int coach_id, String league_name, String city, String country, int points, byte[] matchImage) {
        team_name = team_name.replace("'", "''");
        city = city.replace("'", "''");
        country = country.replace("'", "''");

        String strSql = "INSERT INTO teams (team_name, coach_id, league_name, city, country, points, match_image) values ('" +
                team_name + "', " + coach_id + ", '" + league_name + "', '" + city + "', '" + country + "', " + points + ", " + matchImage + ")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertTeam invoked");
    }

    public void insertMatch(String date, String location, double latitude, double longitude, String streetName, byte[] matchImage) {
        date = date.replace("'", "''");
        location = location.replace("'", "''");
        streetName = streetName.replace("'", "''");

        String strSql = "INSERT INTO matches (date, creationDate, location, latitude, longitude, street_name, image) VALUES ('"
                + date + "', '" + new Date().getTime() + "', '" + location + "', " + latitude + ", " + longitude + ", '" + streetName + "', " + matchImage + ")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertMatch invoked");
    }

    public void insertMatchStatistics(int matchId, int goalsScored, int shotsOnTarget, double possessionPercentage, int foulsCommitted) {
        String strSql = "INSERT INTO match_statistics (match_id, goals_scored, shots_on_target, possession_percentage, fouls_committed) VALUES ("
                + matchId + ", " + goalsScored + ", " + shotsOnTarget + ", " + possessionPercentage + ", " + foulsCommitted + ")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertMatchStatistics invoked");
    }

    public void insertPlayer(String firstName, String lastName, int age, int teamId) {
        firstName = firstName.replace("'", "''");
        lastName = lastName.replace("'", "''");

        String strSql = "INSERT INTO players (first_name, last_name, age, team_id) VALUES ('"
                + firstName + "', '" + lastName + "', " + age + ", " + teamId + ")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertPlayer invoked");
    }

    public List<MatchData> readTop5Matches(){
        List<MatchData> matches = new ArrayList<>();
        String strSql = "SELECT * FROM matches ORDER BY CreationDate DESC LIMIT 5";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int matchId = cursor.getInt(0);
            String date = cursor.getString(1);
            Date creationDate = new Date(cursor.getLong(2));
            String location = cursor.getString(3);
            double latitude = cursor.getDouble(4);
            double longitude = cursor.getDouble(5);
            String streetName = cursor.getString(6);
            byte[] image = cursor.getBlob(7);
            MatchData match = new MatchData(matchId, date, creationDate, location, latitude, longitude, streetName, image);
            matches.add(match);
            cursor.moveToNext();
        }
        cursor.close();
        return matches;
    }

    public List<TeamData> readTeamsSortedByPoints(){
        List<TeamData> teams = new ArrayList<>();
        String strSql = "SELECT * FROM teams ORDER BY points DESC";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int teamId = cursor.getInt(0);
            String teamName = cursor.getString(1);
            int coachId = cursor.getInt(2);
            String leagueName = cursor.getString(3);
            String city = cursor.getString(4);
            String country = cursor.getString(5);
            int points = cursor.getInt(6);
            byte[] matchImage = cursor.getBlob(7);
            TeamData team = new TeamData(teamId, teamName, coachId, leagueName, city, country, points, matchImage);
            teams.add(team);
            cursor.moveToNext();
        }
        cursor.close();
        return teams;
    }


//    public List<ScoreData> readTop10(){
//        List<ScoreData> scores = new ArrayList<>();
//
//        // TODO
//        //1ere technique
//        String strSql = "select * from T_Scores order by score desc limit 10";
//                Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
//                cursor.moveToFirst();
//                while(!cursor.isAfterLast()){
//                    ScoreData score = new ScoreData(cursor.getInt( 0 ),cursor.getString(1),cursor.getInt(2), new Date(cursor.getInt(3)));
//                    scores.add(score);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//
//
//        return scores;
//    }
}
