public class Competitor {
    String name;
    double elo;
    double delta_elo;
    public Competitor(String n, double e) {
        name = n;
        elo = e;
    }
    public void calculateEloChangeAfterWin(Competitor other) {
        double rating_difference = elo - other.elo;
        double win_chance = 1 / (1 + Math.pow(10, -rating_difference/400));
        double change = SimpleEloCalculator.K_FACTOR * (1 - win_chance);
        delta_elo += change;
        other.delta_elo -= change;
    }
    public void updateElo() {
        elo += delta_elo;
        delta_elo = 0;
    }
}
