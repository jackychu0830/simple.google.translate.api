package app.jackychu.api.simplegoogletranslate;

import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public @Data
class SimpleGoogleTranslate {

    private String googleTranslateApiUrl;

    /**
     * Default constructor with default Google Translate API URL
     */
    public SimpleGoogleTranslate() {
        this("https://translate.googleapis.com/translate_a/single");
    }

    /**
     * Constructor with assigned Google Translate API URL
     *
     * @param googleTranslateApiUrl Google Translate API URL
     */
    public SimpleGoogleTranslate(String googleTranslateApiUrl) {
        this.googleTranslateApiUrl = googleTranslateApiUrl;
    }

    /**
     * Main method for run example
     *
     * @param args Runtime arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please type the text which you want to translate: ");
        String text = in.nextLine();

        int index = 1;
        StringBuilder sb = new StringBuilder();
        for (Language lang : Language.values()) {
            if (lang.equals(Language.auto)) continue;
            sb.append(index).append(": ").append(lang.name)
                    .append(" (").append(lang).append(")").append(System.lineSeparator());
            index++;
        }
        System.out.println(sb);

        System.out.print("Please select the language which you want translate to: ");
        int lang = in.nextInt();
        Language target = Language.values()[lang];

        SimpleGoogleTranslate translate = new SimpleGoogleTranslate();
        try {
            System.out.println("Translating to " + target.name);
            System.out.println("Result: ");
            System.out.println(translate.doTranslate(Language.auto, target, text));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Translate text from source language to target language
     *
     * @param source Source language
     * @param target Target language
     * @param text   The text which want to translate
     * @return Translated text
     * @throws UnsupportedEncodingException URL encoding error
     */
    public String doTranslate(Language source, Language target, String text) throws IOException, InterruptedException {
        String newUrl = this.googleTranslateApiUrl + "?client=gtx&dt=t" +
                "&sl=" + source +
                "&tl=" + target +
                "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(newUrl))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray arr1 = (JSONArray) JSONValue.parse(response.body());
        JSONArray arr2 = (JSONArray) JSONValue.parse(arr1.get(0).toString());
        JSONArray arr3 = (JSONArray) JSONValue.parse(arr2.get(0).toString());

        return arr3.get(0).toString();
    }

}
