package com.example.demo.utils;

import com.example.demo.model.BookData;
import com.example.demo.model.InstaData;
import com.example.demo.model.TweetData;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class JSONUtils {
    private JSONUtils(){}

    public static boolean isJSONValid(String jsonInString ) {
        try {
           final ObjectMapper mapper = new ObjectMapper();
           mapper.readTree(jsonInString);
           return true;
        } catch (IOException e) {
           return false;
        }
    }

    public static <T> List<T> convertJsonToList(String arrayToJson, List<T> list, Class contentClass) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, contentClass);

            list = mapper.readValue(arrayToJson, type);
            System.out.println("convert JSON to List of objects.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> resultToJsonArray(List<?> socialData) {
        List<String> sourceDataArray = new ArrayList<>();

        for (Object sd : socialData) {
            if (sd instanceof BookData) {
                sourceDataArray.add(((BookData) sd).getStatus());
            } else if (sd instanceof InstaData) {
                sourceDataArray.add(((InstaData) sd).getPicture());
            } else if (sd instanceof TweetData) {
                sourceDataArray.add(((TweetData) sd).getTweet());
            }
        }

        return sourceDataArray;
    }


    public static String loadJSONFromResource(InputStream inputStream, String charSet) {
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, charSet);
            StringWriter writer = new StringWriter();

            IOUtils.copy(reader, writer);

            writer.close();

            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

}