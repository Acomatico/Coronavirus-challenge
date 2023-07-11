package space.alen.corona.repositories;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;
import java.lang.InterruptedException;
import space.alen.corona.models.CoronaYearlyStats;

public class GetUSDataRepository implements CoronaRepositoryInterface
{
    private static final String URL = "https://api.covidtracking.com/v2/us/daily.json";

    public CoronaYearlyStats getStatsOfYear(String year)
    {
        try {
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder().uri(URI.create(this.URL)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            var rootJson = new JSONObject(response);
            var data = rootJson.getJSONArray("data");
            var monthStatsMap = new Hashmap<String, MinMaxPair>();

            for (int i = 0; i < data.length(); i++) {
                var currentRow = data.getJSONObject(i);

                var date = currentRow.getJSONObject("date");
                var dateParts = date.Split("-");

                var curYear = dateParts[0];
                var curMonth = dateParts[1];
                var curDay = dateParts[2];

                if (year != curYear) {
                    continue;
                }

                int totalCases = Integer.parseInt(currentRow.getJSONObject("cases").getJSONObject("total").getJSONObject("value"));
                
                if (monthStatsMap.get(curMonth) == null) {
                    monthStatsMap.put(new MinMaxPair(totalCases,totalCases));
                    continue;
                }

                var curMinMaxPair = monthStatsMap.get(curMonth);

                if (totalCases > curMinMaxPair.max()) {
                    curMinMaxPair.setMax(totalCases);
                }

                if (totalCases < curMinMaxPair.min()) {
                    curMinMaxPair.setMin(totalCases);
                }
            }

            for (String key : monthStatsMap.keySet()) {
                var minMaxPair = monthStatsMap.get(key);
            }
            
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
        }

        return new CoronaYearlyStats("US", year);
    }

    public static class MinMaxPair
    {
        private int min;
        private int max;

        private MinMaxPair(int min, int max)
        {
            this.min = min;
            this.max = max;
        }

        public int min()
        {

        }

        public void setMin(int min)
        {
            this.min = min;
        }

        public int max()
        {
            return this.max;
        }

        public void setMax(int max)
        {
            this.max = max;
        }
    }
}
