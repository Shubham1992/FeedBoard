package in.feedboard.tabsswipe.headlineclasses;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import in.feedboard.tabsswipe.R;
import in.feedboard.tabsswipe.savedobjects.SavedViewpagerJson;

/**
 * Created by Shubham on 10/2/2015.
 */
public class Head3 extends Fragment {

    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("inside","head1");
        View rootView =  inflater.inflate(R.layout.head3, container, false);
        title = (TextView) rootView.findViewById(R.id.title);

        JSONObject jsonObject = SavedViewpagerJson.getObject().getObj().optJSONArray("stories").optJSONObject(2);
        Log.e("head3 json", jsonObject.toString());
        String titleText = jsonObject.optString("title");
        title.setText(titleText);
        return rootView;
    }
}
