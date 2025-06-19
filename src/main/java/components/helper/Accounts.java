package src.main.java.components.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import src.server.java.ServerURL;

public class Accounts extends ServerURL {

    private String lastTime = "1970-01-01 00:00:00";

    public void loadAccountsRequests(DefaultTableModel model) {
        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;"
                        + "&since=" + URLEncoder.encode(lastTime, StandardCharsets.UTF_8);
                HttpURLConnection conn = createPostConnection("get_approvals.php", params);
                JSONArray employees = parseJsonArrayResponse(conn);

                List<Object[]> rows = new ArrayList<>();
                String newestTime = lastTime;

                for (int i = 0; i < employees.length(); i++) {
                    JSONObject emp = employees.getJSONObject(i);
                    String firstname = emp.getString("firstname");
                    String lastname = emp.getString("lastname");
                    String email = emp.getString("email");
                    String branch = emp.getString("branch");
                    String gender = emp.getString("gender");
                    String status = emp.getString("status");
                    String time = emp.getString("time");

                    rows.add(new Object[] { firstname, lastname, email, branch, gender, status });

                    if (time.compareTo(newestTime) > 0) {
                        newestTime = time;
                    }
                }

                lastTime = newestTime;
                return rows;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> newRows = get();
                    for (Object[] row : newRows) {
                        model.addRow(row);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    public void approveRequest(DefaultTableModel model) {
        model.setRowCount(0);

        HttpURLConnection conn = createPostConnection(
                "get_approvals.php",
                "client_fetch_token_request=;;mslaundryshop2025;;&approved=yes");

        JSONObject response = parseJsonResponse(conn);

        if (response != null && response.getBoolean("response")) {
            JSONArray employees = response.getJSONArray("data");

            for (int i = 0; i < employees.length(); i++) {
                JSONObject emp = employees.getJSONObject(i);
                String firstname = emp.getString("firstname");
                String lastname = emp.getString("lastname");
                String email = emp.getString("email");
                String branch = emp.getString("branch");
                String gender = emp.getString("gender");
                String status = emp.getString("status");

                SwingUtilities.invokeLater(() -> {
                    model.addRow(new Object[] {
                            firstname, lastname, email, branch, gender, status
                    });
                    model.fireTableDataChanged();
                });
            }
        }
    }

    private JSONObject parseJsonResponse(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }

            return new JSONObject(responseBuilder.toString());

        } catch (Exception e) {
            return null;
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
