package fr.isen.android.filmlist.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.filmlist.R;

public abstract class FilmListFragment extends Fragment {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView listview;
	private HashMap<String, View> itemSelected;
	private Drawable b;
	
	public static final String LIST_KEY = "keyFilmList";

	public FilmListFragment() {
		itemSelected = new HashMap<String, View>();
	}

	public ListView getListView() {
		return listview;
	}

	public void setList(ArrayList<String> list_) {
		list = list_;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setAdapter(ArrayAdapter<String> adapter_) {
		this.adapter = adapter_;
	}

	public void setListview(ListView listview_) {
		listview = listview_;
	}

	public abstract int getPosition();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.

		View view = inflater.inflate(R.layout.fragment_film_list, container,
				false);

		Bundle args = getArguments();

		if (args != null && args.containsKey(FilmListFragment.LIST_KEY)) {
			list = args.getStringArrayList(FilmListFragment.LIST_KEY);
		} else {
			list = new ArrayList<String>();
		}

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, list);
		listview = (ListView) view.findViewById(R.id.listview);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		return view;
	}

	public void additemListener(ListView listFl, final String typeKey_) {
		if (listFl != null) {
			listFl.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {

					FilmDetailsFragment filmDetailsFragment = new FilmDetailsFragment();
					Bundle args = new Bundle();
					args.putString(FilmDetailsFragment.MOVIE_KEY,
							list.get(position));
					args.putString(FilmDetailsFragment.TYPE_KEY, typeKey_);
					filmDetailsFragment.setArguments(args);
					((Home) getActivity()).setFragment(filmDetailsFragment,
							Home.fragmentStack, true);
				}
			});
		}
	}

	public void addItemLongClick(ListView list) {
		if (list != null) {
			list.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					if (!itemSelected.containsKey(Integer.toString(position))) {
						itemSelected.put(Integer.toString(position), arg1);
						b = getListView().getChildAt(position).getBackground();
						getListView().getChildAt(position).setBackgroundColor(
								Color.CYAN);
					} else {
						itemSelected.remove(Integer.toString(position));
						getListView().getChildAt(position).setBackground(b);
					}
					return true;
				}
			});
		}
	}

	public void refresh(String film) {
		list.add(film);
		adapter.notifyDataSetChanged();
	}
}