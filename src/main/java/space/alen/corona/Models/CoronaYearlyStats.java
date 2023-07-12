package space.alen.corona.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import space.alen.corona.models.CoronaMonthlyStats;

public class CoronaYearlyStats
{
    private String countryCode;
    private String year;
    private List<CoronaMonthlyStats> monthlyStats;

    public CoronaYearlyStats(String countryCode, String year)
    {
        this.countryCode = countryCode;
        this.year = year;
        this.monthlyStats = new ArrayList<CoronaMonthlyStats>();
    }

    public String countryCode()
    {
        return this.countryCode;
    }

    public String year()
    {
        return this.year;
    }

    public List<CoronaMonthlyStats> monthlyStats()
    {
        return this.monthlyStats;
    }

    public void addMonthlyStat(CoronaMonthlyStats newStats)
    {
        this.monthlyStats.add(newStats);
        this.monthlyStats.sort(Comparator.comparing(CoronaMonthlyStats::month));
    }
}