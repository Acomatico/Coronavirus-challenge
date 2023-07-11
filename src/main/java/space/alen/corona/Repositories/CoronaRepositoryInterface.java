package space.alen.corona.repositories;

import space.alen.corona.models.CoronaYearlyStats;

public interface CoronaRepositoryInterface
{
    public CoronaYearlyStats getStatsOfYear(String year);
}