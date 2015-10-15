package in.feedboard.headlineclasses;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import in.feedboard.R;
import in.feedboard.savedobjects.SavedViewpagerJson;

/**
 * Created by Shubham on 10/2/2015.
 */
public class Head2 extends Fragment {

    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.head2, container, false);
        title = (TextView) rootView.findViewById(R.id.title);

        JSONObject jsonObject = SavedViewpagerJson.getObject().getObj().optJSONArray("stories").optJSONObject(1);

        String titleText = jsonObject.optString("title");
        title.setText(titleText);
        return rootView;
    }
}
