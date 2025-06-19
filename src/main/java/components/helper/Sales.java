package src.main.java.components.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import src.server.java.ServerURL;

public class Sales extends ServerURL {
    private String lastTime = "0000-00-00 00:00:00";

    public void loadSalesChartData(DefaultCategoryDataset dataset, @Nullable String filterMonth,
            @Nullable Integer filterYear) {
        new SwingWorker<Void, Void>() {
            private final List<Object[]> rows = new ArrayList<>();
            private final Map<String, Double> totalPerMonth = new HashMap<>();
            private String newestTime = lastTime;

            @Override
            protected Void doInBackground() throws Exception {
                String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;"
                        + "&since=" + lastTime;
                HttpURLConnection conn = createPostConnection("get_sales.php", params);
                JSONArray arr = parseJsonArrayResponse(conn);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String time = obj.getString("time");
                    String monthName = obj.getString("month_name");
                    int year = obj.getInt("year");
                    String branch = obj.getString("branch");
                    double totalSales = obj.getDouble("total_sales");

                    if ((filterMonth != null && !monthName.equalsIgnoreCase(filterMonth)) ||
                            (filterYear != null && filterYear != year)) {
                        continue;
                    }

                    String columnKey = monthName + " " + year;
                    rows.add(new Object[] { branch, columnKey, totalSales });
                    totalPerMonth.merge(columnKey, totalSales, Double::sum);

                    if (time.compareTo(newestTime) > 0) {
                        newestTime = time;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                lastTime = newestTime;
                SwingUtilities.invokeLater(() -> {
                    dataset.clear();
                    for (Object[] row : rows) {
                        dataset.setValue((Double) row[2], (String) row[0], (String) row[1]);
                    }
                    for (Map.Entry<String, Double> entry : totalPerMonth.entrySet()) {
                        dataset.setValue(entry.getValue(), "Total", entry.getKey());
                    }
                });
            }
        }.execute();
    }

    public void loadSalesChartDataSpecBranch(DefaultCategoryDataset dataset, String SPECBranch,
            @Nullable String filterMonth, @Nullable Integer filterYear) {
        String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;"
                + "&since=" + lastTime;

        HttpURLConnection conn = createPostConnection("get_sales.php", params);
        JSONArray arr = parseJsonArrayResponse(conn);

        dataset.clear();

        Map<String, Double> totalPerMonth = new HashMap<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String time = obj.getString("time");
            String monthName = obj.getString("month_name");
            int year = obj.getInt("year");
            String branch = obj.getString("branch");
            double totalSales = obj.getDouble("total_sales");

            if (!branch.equalsIgnoreCase(SPECBranch))
                continue;

            if ((filterMonth != null && !monthName.equalsIgnoreCase(filterMonth)) ||
                    (filterYear != null && filterYear != year)) {
                continue;
            }

            String columnKey = monthName + " " + year;

            dataset.setValue(totalSales, branch, columnKey);

            totalPerMonth.put(columnKey, totalPerMonth.getOrDefault(columnKey, 0.0) + totalSales);

            if (time.compareTo(lastTime) > 0) {
                lastTime = time;
            }
        }

        for (Map.Entry<String, Double> entry : totalPerMonth.entrySet()) {
            String columnKey = entry.getKey();
            double total = entry.getValue();
            dataset.setValue(total, "Total", columnKey);
        }
    }

    private JSONArray parseJsonArrayResponse(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }

            return new JSONArray(responseBuilder.toString());

        } catch (Exception e) {
            return new JSONArray();
        }
    }

}
