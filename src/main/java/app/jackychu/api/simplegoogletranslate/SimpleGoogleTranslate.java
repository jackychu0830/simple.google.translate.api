package app.jackychu.api.simplegoogletranslate;

import lombok.Data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data
class SimpleGoogleTranslate {

    public static final String RPCIDS = "MkEWBc";
    public static final String FDIS = "-5088539941013039146";
    public static final String BL = "boq_translate-webserver_20210616.14_p0";

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String googleTranslateApiUrl;
    private boolean loggerEnabled = false;

    /**
     * Default constructor with default Google Translate API URL
     */
    public SimpleGoogleTranslate() {
        this("https://translate.google.com/_/TranslateWebserverUi/data/batchexecute");
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
//        translate.enableLogger(Level.INFO);
        try {
            System.out.println("Translating to " + target.name);
            System.out.println("Result: ");
            System.out.println(translate.doTranslate(Language.zh_cn, target, text));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enableLogger(Level lv) {
        logger.setLevel(Objects.requireNonNullElse(lv, Level.WARNING));
        logger.setUseParentHandlers(false);

        FileHandler fh;
        SimpleDateFormat format = new SimpleDateFormat("MMdy_HHmmss");
        try {
            fh = new FileHandler("simple_google.translate-"
                    + format.format(Calendar.getInstance().getTime()) + ".log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {
            System.err.println("Set logger failed!");
            e.printStackTrace();
        }

        loggerEnabled = true;
    }

    private void log(Level lv, String message) {
        if (loggerEnabled)
            logger.log(lv, message);
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

        HttpClient client = HttpClient.newHttpClient();
        String newUrl = this.googleTranslateApiUrl +
                "?rpcids=" + RPCIDS +
                "&f.sid=" + FDIS +
                "&bl=" + BL +
                "&hl=" + source +
                "&soc-app=1&soc-platform=1&soc-device=1&_reqid=970698&rt=c";
        log(Level.INFO, "Request URL: " + newUrl);

        String data = URLEncoder.encode(
                String.format("[[[\"MkEWBc\",\"[[\\\"%s\\\",\\\"%s\\\",\\\"%s\\\",true],[null]]\",null,\"generic\"]]]",
                        text, source, target), StandardCharsets.UTF_8);
        String requestBody = "f.req=" + data +
                "&at=AD08yZnAhdami3yZB5jq9Yi8EvBP%3A1624243093930";
        log(Level.INFO, "Form data: \n" + requestBody);

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(newUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Accept", "*/*")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log(Level.INFO, "Response status code: " + response.statusCode());

        String responseBody = response.body();
        String result = "N/A";
        if (response.statusCode() == 200) {
            log(Level.INFO, "Response body: \n" + responseBody);

            Pattern pattern1 = Pattern.compile(",\\[\\[\\\\\"(.*?)\\\\\"]");
            Matcher matcher1 = pattern1.matcher(responseBody);
            boolean matchFound1 = matcher1.find();

            if (matchFound1) {
                result = matcher1.group(1);
            }

            Pattern pattern2 = Pattern.compile(",\\[\\[\\\\\"(.*?)\\\\\",\\[");
            Matcher matcher2 = pattern2.matcher(responseBody);
            boolean matchFound2 = matcher2.find();

            if (matchFound1 && matchFound2) {
                if (result.length() > matcher2.group(1).length()) {
                    result = matcher2.group(1);
                }
            }

        } else {
            log(Level.WARNING, "Response body: \n" + responseBody);
            throw new IOException("Parse translate result error! ");
        }

        return result;
    }

}
