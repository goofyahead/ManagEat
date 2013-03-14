package es.nxtlink.manageat.adapters;

import java.io.File;

import es.nxtlink.manageat.R;
import es.nxtlink.manageat.db.DbHelper;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DishCursorAdapter extends CursorAdapter {
	private final LayoutInflater inflater;
	private Context mContext;
	private final int imageColumIndex;
	private final int nameColumnIndex;
	private final int priceColumnIndex;
	private final int indexColumnIndex;

	public DishCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mContext = context;
		inflater = LayoutInflater.from(context);
		indexColumnIndex = c.getColumnIndex(DbHelper.DISH_ID);
		imageColumIndex = c.getColumnIndex(DbHelper.DISH_IMAGE);
		nameColumnIndex = c.getColumnIndex(DbHelper.DISH_NAME);
		priceColumnIndex = c.getColumnIndex(DbHelper.DISH_PRICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		setImage(holder, cursor.getString(imageColumIndex));
		holder.dishName.setText(cursor.getString(nameColumnIndex));
		holder.dishPrice.setText(cursor.getString(priceColumnIndex));
	}

	private void setImage(ViewHolder holder, String nameFile) {
		new LoadImageOnCell(nameFile, holder).execute();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View created = inflater.inflate(R.layout.dish_list_cell, null);
		ViewHolder holder = new ViewHolder();
		holder.dishImage = (ImageView) created.findViewById(R.id.dishImage);
		holder.dishName = (TextView) created.findViewById(R.id.dishName);
		holder.dishPrice = (TextView) created.findViewById(R.id.dishPrice);
		created.setTag(holder);
		return created;
	}


	@Override
	public String getItem(int position) {
		int previousPos = this.getCursor().getPosition();
		this.getCursor().moveToPosition(position);
		String id = this.getCursor().getString(indexColumnIndex);
		this.getCursor().moveToPosition(previousPos);
		return id;
	}

	public static class ViewHolder {
		private ImageView dishImage;
		private TextView dishName;
		private TextView dishPrice;
	}

	public class LoadImageOnCell extends AsyncTask<Void, Void, Void> {
		private ViewHolder holder;
		private String nameFile;
		private Bitmap myImage;

		public LoadImageOnCell(String nameFile, ViewHolder holder) {
			this.holder = holder;
			this.nameFile = nameFile;
		}

		@Override
		protected Void doInBackground(Void... params) {
			final File path = mContext.getFileStreamPath(nameFile);
			myImage = BitmapFactory.decodeFile(path.getPath(), null);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			holder.dishImage.setImageBitmap(myImage);
			super.onPostExecute(result);
		}

	}
}
