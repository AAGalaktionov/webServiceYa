package app.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RequestProcessing {

   static public ArrayList<String> listRP = new ArrayList<>();

    static public ArrayList<String> requestDevider(String request, RequestProcessing requestProcessing) {

        for (int i = 0; i < request.split("[+]").length; i++) {
            requestProcessing.listRP.add(request.split("[+]")[i]);
        }
        return requestProcessing.listRP;
    }

    public String reqToYa(String reqStr) throws IOException {
        URL url = new URL("https://yandex.ru/search/xml?user=balaschov-l&key=03.285653075:36ad08680e1bd616c489106ee2539405&query=" + reqStr
                + "&l10n=ru&sortby=tm.order%3Dascending&filter=strict&groupby=attr%3D%22%22.mode%3Dflat.groups-on-page%3D10.docs-in-group%3D1&page=1");
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder response = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append("\r");
        }
        rd.close();
        return response.toString();

    }


    public ArrayList<String> returnerRespFromYa(ExecutorService threadPool, RequestProcessing requestProcessing) throws InterruptedException, ExecutionException {
        // public void main(String[] args) throws ExecutionException, InterruptedException {
        // ExecutorService threadPool = Executors.newFixedThreadPool(4);
        // RequestProcessing requestProcessing = new RequestProcessing();
        ArrayList<String> answerFromYa = new ArrayList<>();


        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < requestProcessing.listRP.size(); i++) {
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                try {
                                    return requestProcessing.reqToYa(requestProcessing.listRP.get(j));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return "-1";
                                }
                            }, threadPool
                    ));
            threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        }


        for (Future<String> future : futures) {
            answerFromYa.add(future.get());
        }


        threadPool.shutdown();
        listRP.clear();
        return answerFromYa;
    }
}


