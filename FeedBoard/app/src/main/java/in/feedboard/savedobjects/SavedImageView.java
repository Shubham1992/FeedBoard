package in.feedboard.savedobjects;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Admin-PC on 10/19/2015.
 */
public class SavedImageView
{
	private SavedImageView(){};

	public ImageView getImageView()
	{
		return imageView;
	}

	public void setImageView(ImageView imageView)
	{
		this.imageView = imageView;
		Log.e("image", imageView.toString());
	}

	private ImageView imageView;

	private  static  SavedImageView savedImageView = new SavedImageView();

	public  static  SavedImageView getInstance()
	{
		return savedImageView;
	}
}
