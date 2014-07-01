package annagn.simpletwitterclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import annagn.simpletwitterclient.models.Tweet;

/**
 * Created by annanatorilla on 6/30/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetArrayAdapter(Context context, List<Tweet> tweets){

        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item position
        Tweet tweet = getItem(position);
        //Find or Inflate the template
        View v;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item,parent,false);
        }else{
            v =convertView;
        }
        //Find the views within template
        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
    //  ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader imageLoader = ImageLoader.getInstance();
        //Populate view with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        return v;
    }
}
