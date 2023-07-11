package space.alen.corona.service;

import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.repositories.CoronaRepositoryInterface;

public class GetYearlyStatsService
{
    private CoronaRepositoryInterface repository;

    public GetYearlyStatsService(CoronaRepositoryInterface repository)
    {
        this.repository = repository;
    }

    public CoronaYearlyStats execute(String year)
    {
        return this.repository.getStatsOfYear(year);
    }
}