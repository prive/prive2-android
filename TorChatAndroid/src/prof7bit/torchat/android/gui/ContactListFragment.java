package prof7bit.torchat.android.gui;


import java.util.List;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import ru.dtlbox.torchat.customviews.AvatarView;
import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.entities.Contact;
import ru.dtlbox.torchat.tests.AvatarSpike;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListFragment extends Fragment {
	
	DBManager mDbManager;
	ListView lvContacts;
	final String TITLE = "Contacts";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contact_list, null);
		lvContacts = (ListView)v.findViewById(R.id.lv_contacts);
		mDbManager = new DBManager().init(getActivity());
		List<Contact> contacts = mDbManager.getAllContact();
		
		//AvatarSpike.setAvatars(getResources(), contacts);
		
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
				TestChatActivity.openTestChatActivityWithMessage(getActivity(), contact.getNickName(), null);
			}
		});
		
		
		return v;
	}
	
	
	
	@Override
	public void onResume() {
		
		getActivity().setTitle(TITLE);
		List<Contact> contacts = mDbManager.getAllContact();
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
				TestChatActivity.openTestChatActivityWithMessage(getActivity(), contact.getNickName(), null);
			}
		});
		super.onResume();
	}
	
	class ContactListAdapter extends BaseAdapter {

		Context context;
		List<Contact> contacts;
		
		public ContactListAdapter(Context context, List<Contact> list) {
			this.context = context;
			this.contacts = list;
			AvatarSpike.setAvatars(getResources(), contacts);
			
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
			
			AvatarView avAvatar = (AvatarView)convertView.findViewById(R.id.av_contact_icon);
			
			avAvatar.setFrameImage(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.face_red));
			if(contacts.get(position).hasAvatar()) {
				avAvatar.setImageBitmap(contacts.get(position).getAvatar());
			}
			else {
				Log.d("++++++++++++","no avatar");
			}
				
			TextView tvNickname = (TextView)convertView.findViewById(R.id.tv_contact_name);
			tvNickname.setText(contacts.get(position).getNickName());
			
			convertView.setTag(contacts.get(position));			
			return convertView;
		}
		
	}
	

}
