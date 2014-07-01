package annagn.simpletwitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import annagn.simpletwitterclient.models.Tweet;

public class TimelineActivity extends Activity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client =TwitterApplication.getRestClient();
        populateTimeline();
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
     // aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray json) {
              //  Log.d("Debug", json.toString());
                  aTweets.addAll(Tweet.fromJSONArray(json));
            }
            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("Debug", e.toString());
                Log.d("Debug", s.toString());

            }
        });
    }
}
