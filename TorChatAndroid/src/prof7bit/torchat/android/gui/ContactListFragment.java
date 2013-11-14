package prof7bit.torchat.android.gui;


import java.util.List;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.entities.Contact;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListFragment extends Fragment {
	
	final static String LOG_TAG = "ContactListFragment";
	
	DBManager mDbManager;
	ListView lvContacts;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contact_list, container);
		lvContacts = (ListView)v.findViewById(R.id.lv_contacts);
		mDbManager = new DBManager().init(getActivity());
		List<Contact> contacts = mDbManager.getAllContact();
		lvContacts.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
		lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));
		lvContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Contact contact = (Contact)(arg1.getTag());
				Intent intent = new Intent(getActivity(), Backend.class);
				intent.setAction(Backend.ACTION_OPEN_CONNECTION);
				intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, contact.getOnionAddress());
				try {
					((TorChat)getActivity()).startService(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});

		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		List<Contact> contacts = mDbManager.getAllContact();
		lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));

		super.onResume();
	}
	
	class ContactListAdapter extends BaseAdapter {

		Context context;
		List<Contact> contacts;
		
		public ContactListAdapter(Context context, List<Contact> list) {
			this.context = context;
			this.contacts = list;
		}
		
		
		@Override
		public int getCount() {
			return contacts.size();
		}

		@Override
		public Object getItem(int position) {
			return contacts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.item_contact, null);
			}
			
			TextView tvNickname = (TextView)convertView.findViewById(R.id.tv_contact_name);
			tvNickname.setText(contacts.get(position).getNickName());
			
			convertView.setTag(contacts.get(position));			
			return convertView;
		}
		
	}
	

}
