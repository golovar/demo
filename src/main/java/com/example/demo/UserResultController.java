package com.example.demo;

import com.example.demo.model.BookData;
import com.example.demo.model.InstaData;
import com.example.demo.model.TweetData;
import com.example.demo.utils.JSONUtils;
import com.example.demo.utils.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping(path="/")
public class UserResultController {

    private static String BOOK_URL = "http://reali-social.herokuapp.com/bookoffaces";
    private static String INSTA_URL = "http://reali-social.herokuapp.com/instaphoto";
    private static String TWEET_URL = "http://reali-social.herokuapp.com/tweety";

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<String>>> accumulateUserSocialData() {

        return new ResponseEntity<>(callMethod(), HttpStatus.OK);
    }


    public static Map<String, List<String>> callMethod() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<InputStream> response1 = executor.submit(new Request(BOOK_URL));
        Future<InputStream> response2 = executor.submit(new Request(INSTA_URL));
        Future<InputStream> response3 = executor.submit(new Request(TWEET_URL));

        String bookJsonString = null;
        String instaJsonString = null;
        String tweetJsonString = null;
        List<BookData> bookDataList = new ArrayList<>();
        List<TweetData> tweetDataList = new ArrayList<>();
        List<InstaData> instaDataList = new ArrayList<>();

        try {
            bookJsonString = JSONUtils.loadJSONFromResource(response1.get(), StandardCharsets.UTF_8.name());

            instaJsonString = JSONUtils.loadJSONFromResource(response2.get(), StandardCharsets.UTF_8.name());

            tweetJsonString = JSONUtils.loadJSONFromResource(response3.get(), StandardCharsets.UTF_8.name());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Check for valid and convert to list of objects
        if (bookJsonString != null && JSONUtils.isJSONValid(bookJsonString)) {
            bookDataList = JSONUtils.convertJsonToList(bookJsonString, bookDataList, BookData.class);
        }
        if (instaJsonString != null && JSONUtils.isJSONValid(instaJsonString)) {
            instaDataList = (JSONUtils.convertJsonToList(instaJsonString, instaDataList, InstaData.class));
        }
        if (tweetJsonString != null && JSONUtils.isJSONValid(tweetJsonString)) {
            tweetDataList = (JSONUtils.convertJsonToList(tweetJsonString, tweetDataList, TweetData.class));
        }

        Map<String, List<String>> socialDataMap = new HashMap<>();

        if (instaDataList.size() > 0) {
            socialDataMap.put("instaphoto", JSONUtils.resultToJsonArray(instaDataList));
        }
        if (tweetDataList.size() > 0) {
            socialDataMap.put("tweety", JSONUtils.resultToJsonArray(tweetDataList));
        }
        if (bookDataList.size() > 0) {
            socialDataMap.put("bookffaces", JSONUtils.resultToJsonArray(bookDataList));
        }

        executor.shutdown();

        return socialDataMap;
    }


}
