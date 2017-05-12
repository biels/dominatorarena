package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.model.entities.*;
import com.biel.dominatorarena.model.repositories.StatisticBattleReportRepository;
import com.biel.dominatorarena.model.repositories.StatisticBattleReportSVResultRepository;
import com.biel.dominatorarena.model.repositories.StatisticBattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Biel on 10/5/2017.
 */
@Component
public class StatisticBattleReportGenerator {
    @Autowired
    StatisticBattleRepository statisticBattleRepository;
    @Autowired
    StatisticBattleReportRepository statisticBattleReportRepository;
    @Autowired
    StatisticBattleReportSVResultRepository statisticBattleReportSVResultRepository;
    public void generateReport(StatisticBattle statisticBattle) {
        //if(statisticBattle.getReport() != null)statisticBattleReportRepository.delete(statisticBattle.getReport());
        statisticBattleReportRepository.removeAllByStatisticBattle_Id(statisticBattle.getId());
        StatisticBattleReport report = new StatisticBattleReport(statisticBattle);
        List<Battle> battles = statisticBattle.getBattles();
        report.setBattleCount(battles.size());
        statisticBattleReportRepository.save(report);
        List<StatisticBattleReportSVResult> svResults = statisticBattle.getStrategyVersions().stream().map(strategyVersion -> {
            //For specific StrategyVersion
            StatisticBattleReportSVResult svResult = new StatisticBattleReportSVResult(report, strategyVersion);
            List<List<BattlePlayer>> filteredBattlePlayers = battles.stream().map(battle ->
                    battle.getBattlePlayers().stream()
                            .filter(bp -> bp.getStrategyVersion() == strategyVersion)
                            .collect(Collectors.toList())
            ).collect(Collectors.toList());
            svResult.setInGamePlayerMultiplicity(filteredBattlePlayers.stream().mapToInt(List::size).average().getAsDouble());
            List<BattlePlayerResult> fbpResults = filteredBattlePlayers.stream()
                    .flatMap(fbpl -> fbpl.stream())
                    .filter(fbp -> fbp.getResult() != null)
                    .map(BattlePlayer::getResult)
                    .collect(Collectors.toList());
            List<Integer> scores = fbpResults.stream().map(BattlePlayerResult::getScore).collect(Collectors.toList());
            List<Integer> places = fbpResults.stream().map(BattlePlayerResult::getPlace).collect(Collectors.toList());

            Supplier<IntStream> scoreSupplier = () -> scores.stream().mapToInt(i -> i);
            svResult.setMaxScore(scoreSupplier.get().max().getAsInt());
            svResult.setMinScore(scoreSupplier.get().min().getAsInt());
            svResult.setAverageScore(scoreSupplier.get().average().getAsDouble());

            Supplier<IntStream> placeSupplier = () -> places.stream().mapToInt(i -> i);
            svResult.setAveragePlace(placeSupplier.get().average().getAsDouble());
            svResult.setWinRatio(placeSupplier.get().filter(p -> p == 1).count() / (double)places.size());
            svResult.setJutgeWinRatio(placeSupplier.get().filter(p -> p == 1 || p == 2).count() / (double)places.size());
            //scoreSupplier.get().mapToDouble(i -> i)
            //svResult.setScoreStandardDeviation(scoreSupplier.get().collect());
            //svResult.setMaxScore();
            return statisticBattleReportSVResultRepository.save(svResult);
        }).collect(Collectors.toList());
        report.setSvResults(svResults);
        statisticBattle.setReport(statisticBattleReportRepository.save(report));
        statisticBattleRepository.save(statisticBattle);
    }


    static class DoubleStatistics extends DoubleSummaryStatistics {

        private double sumOfSquare = 0.0d;
        private double sumOfSquareCompensation; // Low order bits of sum
        private double simpleSumOfSquare; // Used to compute right sum for
        // non-finite inputs

        @Override
        public void accept(double value) {
            super.accept(value);
            double squareValue = value * value;
            simpleSumOfSquare += squareValue;
            sumOfSquareWithCompensation(squareValue);
        }

        public DoubleStatistics combine(DoubleStatistics other) {
            super.combine(other);
            simpleSumOfSquare += other.simpleSumOfSquare;
            sumOfSquareWithCompensation(other.sumOfSquare);
            sumOfSquareWithCompensation(other.sumOfSquareCompensation);
            return this;
        }

        private void sumOfSquareWithCompensation(double value) {
            double tmp = value - sumOfSquareCompensation;
            double velvel = sumOfSquare + tmp; // Little wolf of rounding error
            sumOfSquareCompensation = (velvel - sumOfSquare) - tmp;
            sumOfSquare = velvel;
        }

        public double getSumOfSquare() {
            double tmp = sumOfSquare + sumOfSquareCompensation;
            if (Double.isNaN(tmp) && Double.isInfinite(simpleSumOfSquare)) {
                return simpleSumOfSquare;
            }
            return tmp;
        }

        public final double getStandardDeviation() {
            long count = getCount();
            double sumOfSquare = getSumOfSquare();
            double average = getAverage();
            return count > 0 ? Math.sqrt((sumOfSquare - count * Math.pow(average, 2)) / (count - 1)) : 0.0d;
        }

        public static Collector<Double, ?, DoubleStatistics> collector() {
            return Collector.of(DoubleStatistics::new, DoubleStatistics::accept, DoubleStatistics::combine);
        }

    }
}
