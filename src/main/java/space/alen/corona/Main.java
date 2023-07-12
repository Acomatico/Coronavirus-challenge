package space.alen.corona;

import space.alen.corona.service.GetYearlyStatsService;
import space.alen.corona.repositories.GetUSDataRepository;
import space.alen.corona.repositories.GetUKDataRepository;
import space.alen.corona.repositories.CoronaRepositoryInterface;
import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.output.YearlyOutput;

public class Main {
    public static void main(String[] args) {
        var country = args[0];
        var year = args[1];
        
        CoronaRepositoryInterface repository;

        if (country.equals("US")) {
            repository = new GetUSDataRepository();
        } else if (country.equals("UK")) {
            repository = new GetUKDataRepository();
        } else {
            throw new IllegalArgumentException("Only accepeted countries are US and UK");
        }

        var service = new GetYearlyStatsService(repository);
        CoronaYearlyStats yearlyStats = service.execute(year);

        YearlyOutput.output(yearlyStats);
    }
}
