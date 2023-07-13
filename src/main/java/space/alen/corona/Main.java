package space.alen.corona;

import space.alen.corona.service.GetYearlyStatsService;
import space.alen.corona.repositories.GetUSDataRepository;
import space.alen.corona.repositories.GetUKDataRepository;
import space.alen.corona.repositories.CoronaRepositoryInterface;
import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.output.YearlyOutput;
import space.alen.corona.output.YearlyGraphOutput;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("The command requires at least 2 arguments, Country and Year");
            System.exit(1);
        }

        var country = args[0];
        var year = args[1];
        
        CoronaRepositoryInterface repository = null;

        if (country.equals("US")) {
            repository = new GetUSDataRepository();
        } else if (country.equals("UK")) {
            repository = new GetUKDataRepository();
        } else {
            System.err.println("Only accepeted countries are US and UK");
            System.exit(1);
        }

        var service = new GetYearlyStatsService(repository);
        CoronaYearlyStats yearlyStats = service.execute(year);

        YearlyOutput.output(yearlyStats);
        YearlyGraphOutput.output(yearlyStats);
    }
}
