package annagn.simpletwitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import annagn.simpletwitterclient.models.Tweet;

public class TimelineActivity extends Activity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    private Long lastTweetId;


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

        //Add the endless scroll for the list view
        lvTweets.setOnScrollListener(new EndlessScrollListener(10) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreTweets(lastTweetId);
            }
        });
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray json) {
              //  Log.d("Debug", json.toString());
                  aTweets.addAll(Tweet.fromJSONArray(json));
                  getLastTweetId(json);

            }
            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("Debug", e.toString());
                Log.d("Debug", s.toString());

            }
        });
    }

    public void getLastTweetId(JSONArray array){

        try {
            //https://dev.twitter.com/docs/platform-objects/tweets
            //id_str is a  The string representation of the unique identifier for every Tweet.
            //get the last tweet string identifier and convert it to long
            lastTweetId = array.getJSONObject(array.length() -1).getLong("id_str");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadMoreTweets(Long lastTweetId){
        client.getPrecedingTweet(lastTweetId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray array) {
                //Know the last tweet each time user scrolls
                getLastTweetId(array);
                aTweets.addAll(Tweet.fromJSONArray(array));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                // super.onFailure(throwable, s);
                Log.d("Debug", throwable.toString());
            }
        });
    }

    //create the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
          //  case R.id.action_compose:
          //        return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
