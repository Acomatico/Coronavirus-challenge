package space.alen.corona.repositories;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.lang.InterruptedException;
import org.json.JSONObject;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.models.CoronaMonthlyStats;

public class GetUSDataRepository implements CoronaRepositoryInterface
{
    private static final String URL = "https://api.covidtracking.com/v2/us/daily.json";

    public CoronaYearlyStats getStatsOfYear(String year)
    {
        var stats = new CoronaYearlyStats("US", year);

        try {
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder().uri(URI.create(this.URL)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            var rootJson = new JSONObject(response);
            var data = rootJson.getJSONArray("data");
            var monthStatsMap = new HashMap<String, Integer>();

            for (int i = 0; i < data.length(); i++) {
                var currentRow = data.getJSONObject(i);

                var date = currentRow.getString("date");
                var dateParts = date.split("-");

                var curYear = dateParts[0];
                var curMonth = dateParts[1];
                var curDay = dateParts[2];

                if (!year.equals(curYear)) {
                    continue;
                }

                JSONObject cases = currentRow.getJSONObject("cases");
                JSONObject total = cases.getJSONObject("total");
                JSONObject calculated = total.getJSONObject("calculated");
                int newCases = calculated.getInt("change_from_prior_day");

                if (monthStatsMap.get(curMonth) == null) {
                    monthStatsMap.put(curMonth, 0);
                }

                int curCases =  monthStatsMap.get(curMonth);
                monthStatsMap.put(curMonth, curCases + newCases);
            }

            for (String key : monthStatsMap.keySet()) {
                var cases = monthStatsMap.get(key);
                stats.addMonthlyStat(new CoronaMonthlyStats(key, cases));
            }
            
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
        }

        return stats;
    }
}
