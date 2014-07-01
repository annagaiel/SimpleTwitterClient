package annagn.simpletwitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by annanatorilla on 6/30/14.
 */
public class Tweet {
    private String body;
    private Long uid;
    private String createdAt;
    private User user;

    public String getBody() {
        return body;
    }
    public Long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for(int i=0; i < jsonArray.length(); i++){
            JSONObject tweetJson = null;
            try{
                tweetJson = jsonArray.getJSONObject(i);

            }catch(Exception e){
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if(tweet != null){
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        //Extract values from the json to populate the member variables
        try{
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        }catch(JSONException e){
            e.printStackTrace();;
            return null;
        }
        return tweet;
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }
}
