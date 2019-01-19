package com.walle.project.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public interface RequestResponse {
    default Integer checkResponse(HttpURLConnection con) throws IOException {
        Integer responseCode = con.getResponseCode ( );
        System.out.println ("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader (new InputStreamReader (
                    con.getInputStream ( )));
            String inputLine;
            StringBuffer response = new StringBuffer ( );

            while ((inputLine = in.readLine ( )) != null) {
                response.append (inputLine);
            }
            in.close ( );
            // print result
        } else {
            System.out.println ("POST request not worked");
        }
        return responseCode;
    }
}
