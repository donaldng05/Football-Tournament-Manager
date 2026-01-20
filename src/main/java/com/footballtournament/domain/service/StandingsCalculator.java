package java.com.footballtournament.domain.service;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Standing;
import java.com.footballtournament.domain.model.Tournament;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandingsCalculator {

    public static List<Standing> calculate(Tournament tournament) {
        Map<Club, Standing> standingsMap = new HashMap<>();

        // Initialize standings for all participating clubs
        for (Club club : tournament.getparticipatingClubs()) {
            standingsMap.put(club, new Standing(club));
        }

        // Process matches to accumulate stats
        // Note: Currently Tournament stores match results in a map `matchResults`.
        // We iterate over that map to calculate standings.
        Map<Match, Result> results = tournament.getmatchResult();

        for (Map.Entry<Match, Result> entry : results.entrySet()) {
            Match match = entry.getKey();
            Result result = entry.getValue();

            Standing homeStanding = standingsMap.get(match.gethomeClub());
            Standing awayStanding = standingsMap.get(match.getawayClub());

            if (homeStanding != null && awayStanding != null) {
                int hg = result.gethomeGoals();
                int ag = result.getawayGoals();
                String winner = result.determineWinner();

                if ("Home".equals(winner)) {
                    homeStanding.recordWin(hg, ag);
                    awayStanding.recordLoss(ag, hg);
                } else if ("Away".equals(winner)) {
                    homeStanding.recordLoss(hg, ag);
                    awayStanding.recordWin(ag, hg);
                } else {
                    homeStanding.recordDraw(hg, ag);
                    awayStanding.recordDraw(ag, hg);
                }
            }
        }

        // Sort: Points (desc), Goal Diff (desc), Goals For (desc)
        return standingsMap.values().stream()
                .sorted(Comparator.comparingInt(Standing::getPoints)
                        .thenComparingInt(Standing::getGoalDifference)
                        .thenComparingInt(Standing::getGoalsFor)
                        .reversed())
                .collect(Collectors.toList());
    }
}
