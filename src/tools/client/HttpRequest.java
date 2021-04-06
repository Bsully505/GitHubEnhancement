package github.tools.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    public static Response get(String endpoint, QueryParams queryParams, BasicAuth basicAuth) {
        try {
            String queryString = "";
            if (queryParams != null) {
                queryString = queryParams.toQueryString();
            }
            URL url = new URL(endpoint + queryString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");

            if (basicAuth != null) {
                conn.setRequestProperty("Authorization", basicAuth.toAuthHeader());
            }

            conn.setRequestMethod("GET");

            String response = extractResponseOutput(conn);
            int statusCode = conn.getResponseCode();
            if (!isStatusCodeSuccessful(statusCode)) {
                String errorMessage = extractErrorOutput(conn);
                throw new RequestFailedException("GET Request to " + endpoint + " failed: " + errorMessage, statusCode);
            }
            return new Response(convertResponseToJsonObject(response), statusCode);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Response post(String endpoint, RequestParams params, BasicAuth basicAuth) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");

            if (basicAuth != null) {
                conn.setRequestProperty("Authorization", basicAuth.toAuthHeader());
            }

            conn.setRequestMethod("POST");


            if (params != null) {
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = params.toJsonString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            String response = extractResponseOutput(conn);
            int statusCode = conn.getResponseCode();
            if (!isStatusCodeSuccessful(statusCode)) {
                String errorMessage = extractErrorOutput(conn);
                throw new RequestFailedException("POST Request to " + endpoint + " failed: " + errorMessage, statusCode);
            }
            return new Response(convertResponseToJsonObject(response), statusCode);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Response put(String endpoint, RequestParams params, BasicAuth basicAuth) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");

            if (basicAuth != null) {
                conn.setRequestProperty("Authorization", basicAuth.toAuthHeader());
            }

            conn.setRequestMethod("PUT");


            if (params != null) {
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = params.toJsonString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            String response = extractResponseOutput(conn);
            int statusCode = conn.getResponseCode();
            if (!isStatusCodeSuccessful(statusCode)) {
                String errorMessage = extractErrorOutput(conn);
                throw new RequestFailedException("PUT Request to " + endpoint + " failed: " + errorMessage, statusCode);
            }
            return new Response(convertResponseToJsonObject(response), statusCode);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Response patch(String endpoint, RequestParams params, BasicAuth basicAuth) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");

            if (basicAuth != null) {
                conn.setRequestProperty("Authorization", basicAuth.toAuthHeader());
            }

            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // for whatever reason HttpURLConnection doesn't support PATCH requests natively so this is a workaround
            conn.setRequestMethod("POST");


            if (params != null) {
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = params.toJsonString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            String response = extractResponseOutput(conn);
            int statusCode = conn.getResponseCode();
            if (!isStatusCodeSuccessful(statusCode)) {
                String errorMessage = extractErrorOutput(conn);
                throw new RequestFailedException("PATCH Request to " + endpoint + " failed: " + errorMessage, statusCode);
            }
            return new Response(convertResponseToJsonObject(response), statusCode);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Response delete(String endpoint, RequestParams params, BasicAuth basicAuth) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");

            if (basicAuth != null) {
                conn.setRequestProperty("Authorization", basicAuth.toAuthHeader());
            }

            conn.setRequestMethod("DELETE");

            if (params != null) {
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = params.toJsonString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            String response = extractResponseOutput(conn);
            int statusCode = conn.getResponseCode();
            if (!isStatusCodeSuccessful(statusCode)) {
                String errorMessage = extractErrorOutput(conn);
                throw new RequestFailedException("DELETE Request to " + endpoint + " failed: " + errorMessage, statusCode);
            }
            return new Response(convertResponseToJsonObject(response), statusCode);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String extractResponseOutput(HttpURLConnection conn) {
        try {
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            return result.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private static String extractErrorOutput(HttpURLConnection conn) {
        try {
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            return result.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private static JsonElement convertResponseToJsonObject(String response) {
        try {
            return JsonParser.parseString(response).getAsJsonObject();
        } catch (JsonParseException | IllegalStateException e) {
            try {
                return JsonParser.parseString(response).getAsJsonArray();
            } catch (JsonParseException | IllegalStateException e2) {
                return new JsonObject();
            }
        }
    }

    private static boolean isStatusCodeSuccessful(int statusCode) {
        return statusCode / 100 == 2;
    }
}
