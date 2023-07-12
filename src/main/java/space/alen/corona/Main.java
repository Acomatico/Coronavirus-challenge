package space.alen.corona;

import space.alen.corona.service.GetYearlyStatsService;
import space.alen.corona.repositories.GetUSDataRepository;
import space.alen.corona.repositories.CoronaRepositoryInterface;
import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.output.YearlyOutput;

public class Main {


    public static void main(String[] args) {
        var country = args[0];
        var year = args[1];
        
        CoronaRepositoryInterface repository;

        if (country == "US")
        {
            repository = new GetUSDataRepository();
        } else if (country == "UK")
        {
            throw new IllegalArgumentException("aaa");
        }

        var service = new GetYearlyStatsService(new GetUSDataRepository());
        CoronaYearlyStats yearlyStats = service.execute(year);

        var output = new YearlyOutput();

        output.output(yearlyStats);
    }
}
