package es.nxtlink.manageat.adapters;

import es.nxtlink.manageat.R;
import es.nxtlink.manageat.db.DbHelper;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CategoryCursorAdapter extends CursorAdapter{
	private LayoutInflater inflater;
	
	public CategoryCursorAdapter(Context context) {
		super(context, null, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public Object getItem(int position) {
		Cursor current = this.getCursor();
		int currentPostion = current.getPosition();
		current.moveToPosition(position);
		String id = current.getString(current.getColumnIndex(DbHelper.CATEGORY_ID));
		current.moveToPosition(currentPostion);
		return id;
	}

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		((TextView)view.findViewById(R.id.categories_list_element_name)).setText(cursor.getString(cursor.getColumnIndex(DbHelper.CATEGORY_NAME)));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View created = inflater.inflate(R.layout.categories_list_element, null);
		return created;
	}
}
