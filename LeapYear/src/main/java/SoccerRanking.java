
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;

// https://leapyear.github.io/soccer-ranking-project/

public class SoccerRanking {
    private  HashMap<String, Integer> scoresMap = new HashMap<>();
    private ArrayList<Map.Entry<String,Integer>> scoresArray = new ArrayList<>();


    private Pair<String, Integer> getTeamNameScore(String teamData) {
        // Get the first team name and score
        String[] team = teamData.split(" ");
        StringBuilder teamName = new StringBuilder();
        for (int i = 0; i < team.length-1; i++) {
            teamName.append((i > 0) ? " ": "").append(team[i]);
        }
        // check for -ve values ?
        int teamScore = Integer.parseInt(team[team.length-1]);

        return new Pair<>(teamName.toString(), teamScore);
    }
    private void processInputLine(String line) {
        // Use ", " as a separator between the teams
        String[] teams = line.split(", ");

        // Get the first team name and score
        Pair<String, Integer> team1 = getTeamNameScore(teams[0]);
        String team1Name = team1.getKey();
        int team1Score = team1.getValue();

        // Get the second team name and score
        Pair<String, Integer> team2 = getTeamNameScore(teams[1]);
        String team2Name = team2.getKey();
        int team2Score = team2.getValue();

        // Update the scores of the Teams
        if (team1Score  == team2Score) {
            scoresMap.put(team1Name, (1 + scoresMap.getOrDefault(team1Name, 0 )));
            scoresMap.put(team2Name, (1 + scoresMap.getOrDefault(team2Name, 0 )));
        } else {
            scoresMap.put(team1Name, ((team1Score > team2Score) ? 3 : 0) + scoresMap.getOrDefault(team1Name, 0 ));
            scoresMap.put(team2Name, ((team2Score > team1Score) ? 3 : 0) + scoresMap.getOrDefault(team2Name, 0 ));
        }
    }

    protected void readScores() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null || line.isEmpty())
                    break;
                processInputLine(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            bufferedWriter.write("Invalid Input parameters : " + e.toString() + " \n" );
            bufferedWriter.close();
        }
    }

    private void rankScores() {
        // Create a ArrayList from the scoresMap
        scoresArray = new ArrayList<>(scoresMap.entrySet());

        scoresArray = scoresArray.stream().sorted((o1, o2) -> {
            if (o1.getValue().equals(o2.getValue())) {
                // If the scores are equal/tie, sort by Alphabetical order
                return o1.getKey().compareTo(o2.getKey());
            } else {
                // Sort in descending order
                return o2.getValue() - o1.getValue();
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private void printRanks() throws IOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int i = 0; i < scoresArray.size(); i++) {
                bufferedWriter.write(String.valueOf(i + 1) + ". ");
                bufferedWriter.write(String.valueOf(scoresArray.get(i).getKey()) + ", ");
                bufferedWriter.write(String.valueOf(scoresArray.get(i).getValue()) +
                        String.valueOf(scoresArray.get(i).getValue() == 1 ? " pt" : " pts"));
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            bufferedWriter.write("Error writing results to System.out");
            bufferedWriter.close();
        }
    }
    public void run() throws  IOException{
        readScores();
        rankScores();
        printRanks();
    }
    public static void main(String[] args) throws IOException {
        // Read from System.in and write to System.out, using buffered reader/writer
        try {
            SoccerRanking soccerRanking = new SoccerRanking();
            soccerRanking.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
