package src.main.java.auth.session.java;

import org.json.JSONObject;

public class FetchUser {
    private static JSONObject userData;

    private FetchUser() {}

    public static void setUserData(JSONObject data) {
        userData = data;
    }

    public static JSONObject getUserData() {
        return userData;
    }

    // public static JSONObject fetchUser() {
    //     return userData;
    // }
}
