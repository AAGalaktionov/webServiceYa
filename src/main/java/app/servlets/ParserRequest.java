package app.servlets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParserRequest {

    public static class Response {
        private String url;
        private String domain;

        public Response(String url, String domain) {
            this.url = url;
            this.domain = domain;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }


    static ArrayList<Response> list = new ArrayList<>();
    static HashMap<String, Integer> map = new HashMap<>();

    public static HashMap<String, Integer> parserToMap(String string) {
        String[] arrayString = string.split("><");
        for (int i = 0; i < arrayString.length-1; i++) {

            if (arrayString[i].contains("url")) {

                list.add(new Response ((arrayString[i].substring(arrayString[i].indexOf(">") + 1, arrayString[i].indexOf("<"))), (arrayString[i+1].substring(arrayString[i+1].indexOf(">") + 1, arrayString[i+1].indexOf("<")))));

            }
        }

        for (int i = 0; i < list.size(); i++) {
            Integer count = 1;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).getDomain().equals(list.get(j).getDomain()) && !list.get(i).getUrl().equals(list.get(j).getUrl()) )
                    count++;
            }
            map.put(list.get(i).getDomain(),count);
        }

        return map;
    }


    }

