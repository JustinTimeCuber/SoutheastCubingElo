import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SimpleEloCalculator {
    public static final double K_FACTOR = 12;
    public static final double STARTING_ELO = 1000;
    public static void main(String[] args) {
        List<String> input;
        try {
            input = Files.readAllLines(Path.of("input.txt"));
        } catch(Exception ex) {
            System.err.println("Failed to load input.txt");
            return;
        }
        List<Competitor> competitors = new ArrayList<>();
        boolean isInit = true;
        for(int i = 1; i < input.size(); i++) {
            if(input.get(i).isBlank() || input.get(i).trim().startsWith("#")) {
                continue;
            }
            if(isInit) {
                if(input.get(0).trim().equalsIgnoreCase("start")) {
                  isInit = false;
                  continue;
                }
                String[] splitCompetitorList = input.get(0).split(" ");
                for(int j = 1; j < splitCompetitorList.length; j += 2) {
                    String n = splitCompetitorList[j-1];
                    String e = splitCompetitorList[j];
                    try {
                        competitors.add(new Competitor(n, Integer.parseInt(e)));
                    } catch(Exception ex) {
                        System.err.println("Failed to add competitor \"" + n + "\" with starting elo: " + e);
                    }
                }
                continue;
            }
            String[] splitLine = input.get(i).split(" ");
            Competitor[] round = new Competitor[splitLine.length];
            for(int j = 0; j < splitLine.length; j++) {
                round[j] = null;
                for(Competitor c : competitors) {
                    if(c.name.equalsIgnoreCase(splitLine[j])) {
                        round[j] = c;
                        break;
                    }
                }
                if(round[j] == null) {
                    Competitor newCompetitor = new Competitor(splitLine[j], STARTING_ELO);
                    competitors.add(newCompetitor);
                    round[j] = newCompetitor;
                }
            }
            for(int delta = 1; delta < round.length - 1; delta++) {
                for(int j = 0; j < round.length - delta; j++) {
                    if(round[j] != null && round[j + delta] != null) {
                        round[j].calculateEloChangeAfterWin(round[j + delta]);
                    }
                }
                for(Competitor c : competitors) {
                    c.updateElo();
                }
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("elos.txt"));
            competitors.sort(Comparator.comparingDouble(c -> -c.elo));
            System.out.println("Final Elo ratings:");
            for(Competitor c : competitors) {
                String output = c.name + ": " + (int)Math.round(c.elo);
                System.out.println(output);
                writer.write(output + "\n");
            }
            writer.close();
        } catch(Exception e) {
            System.err.println("Failed to write to elos.txt");
        }
    }
}
