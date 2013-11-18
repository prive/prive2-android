package prof7bit.torchat.android.gui;

import prof7bit.torchat.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ChatsFragment extends Fragment {
	
	ListView lvLastMessages;
	
//	HashMap
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.fragment_contact_list, null);
		
		lvLastMessages = (ListView)v.findViewById(R.id.lv_contacts);
		
		
		
		
		
		
		return v;
	}
	
	class LastMessageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	
	

}
