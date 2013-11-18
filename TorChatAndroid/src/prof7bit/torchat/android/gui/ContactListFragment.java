package prof7bit.torchat.android.gui;


import java.util.List;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.gui.TestChatActivity.ChatAdapter;
import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.Backend.ContactListener;
import prof7bit.torchat.core.Buddy.Status;
import ru.dtlbox.torchat.customviews.AvatarView;
import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.dbworking.DBManager.DBHelper;
import ru.dtlbox.torchat.entities.Contact;
import ru.dtlbox.torchat.entities.Contact.ContactStatus;
import ru.dtlbox.torchat.tests.AvatarSpike;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListFragment extends Fragment implements ContactListener {
	
	final static String LOG_TAG = "ContactListFragment";
	
	DBManager mDbManager;
	ListView lvContacts;
	final String TITLE = "Contacts";
	List<Contact> contacts;
	boolean mIsBound;
	
	Backend mBackend = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contact_list, null);
		lvContacts = (ListView)v.findViewById(R.id.lv_contacts);
		mDbManager = new DBManager().init(getActivity());
		contacts = mDbManager.getAllContact();
		
		//AvatarSpike.setAvatars(getResources(), contacts);
		
		lvContacts.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
		lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));
		lvContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Contact contact = (Contact)(arg1.getTag());
//				Intent intent = new Intent(getActivity(), Backend.class);
//				intent.setAction(Backend.ACTION_OPEN_CONNECTION);
//				intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, contact.getOnionAddress());
//				try {
//					((TorChat)getActivity()).startService(intent);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//				TestChatActivity.openTestChatActivityWithMessage(getActivity(), contact.getNickName(), null);
				ProfileFragment profile = new ProfileFragment();
				Bundle bundle = new Bundle();
				bundle.putString(Contact.KEY_NICKNAME, contact.getNickName());
				bundle.putString(Contact.KEY_ONION_ADDRESS, contact.getOnionAddress());
				profile.setArguments(bundle);
				((TorChat)getActivity()).changeFragment(profile);
			}
		});
		
		
		return v;
	}
	
	
	
	@Override
	public void onResume() {
		setStatuses(contacts);
		doBindService();
		getActivity().setTitle(TITLE);
		List<Contact> contacts = mDbManager.getAllContact();
		lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));
		lvContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Contact contact = (Contact)(arg1.getTag());
				/*Intent intent = new Intent(getActivity(), Backend.class);
				intent.setAction(Backend.ACTION_OPEN_CONNECTION);
				intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, contact.getOnionAddress());
				try {
					((TorChat)getActivity()).startService(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}
				TestChatActivity.openTestChatActivityWithMessage(getActivity(), contact.getNickName(), null);*/
				ProfileFragment profile = new ProfileFragment();
				Bundle bundle = new Bundle();
				bundle.putString(Contact.KEY_NICKNAME, contact.getNickName());
				bundle.putString(Contact.KEY_ONION_ADDRESS, contact.getOnionAddress());
				profile.setArguments(bundle);
				((TorChat)getActivity()).changeFragment(profile);
			}
		});
		super.onResume();
	}
	
	@Override
	public void onPause() {
		if(mBackend != null)
			mBackend.removeContactListener(ContactListFragment.this);
		doUnbindService();
		super.onPause();
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
			
			avAvatar.setFrameImage( contacts.get(position).getStatus() == ContactStatus.ONLINE ?
					BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.face_green_status) : 
						BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.face_red_status));
			if(contacts.get(position).hasAvatar()) {
				avAvatar.setImageBitmap(contacts.get(position).getAvatar());
			}
			else {
				Log.d("++++++++++++","no avatar");
			}
				
			TextView tvNickname = (TextView)convertView.findViewById(R.id.tv_contact_name);
			tvNickname.setText(contacts.get(position).getNickName());
			
			
			((TextView)convertView.findViewById(R.id.tv_status)).setText(contacts.get(position).getTag());
			
			convertView.setTag(contacts.get(position));			
			return convertView;
		}
		
	}

	@Override
	public void onMessage(String user, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChange(final String user, final Status status) {
		Log.i(LOG_TAG, "onStatusChange user: " + user + "| status: " + (status == Status.ONLINE ? "online" : "offline"));
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				for(Contact contact : contacts)
					if(user.equals(contact.getOnionAddress())) {
						Log.i(LOG_TAG, "WARD BL9");
						contact.setStatus(status == Status.ONLINE ? ContactStatus.ONLINE : ContactStatus.OFFLINE);
					}
				if (lvContacts != null){
//					((ContactListAdapter)(lvContacts.getAdapter())).notifyDataSetChanged();
					lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));
				}
			}
		});
		
	}
	
	ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			mBackend.removeContactListener(ContactListFragment.this);
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mBackend = ((Backend.LocalBinder)service).getService();
			mBackend.addContactListener(ContactListFragment.this);
		}
	};
	
	void doBindService() {
		   
		getActivity().bindService(new Intent(getActivity(), Backend.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	}

	void doUnbindService() {
	    if (mIsBound) {

	        getActivity().unbindService(mConnection);
	        mIsBound = false;
	    }
	}



	@Override
	public void onAddNewContact(String user, Status status) {
		Contact contact = new Contact(user, user).setStatus(status == Status.ONLINE ? ContactStatus.ONLINE : ContactStatus.OFFLINE);
		mDbManager.insertContact(contact);
		contacts = mDbManager.getAllContact();
		setStatuses(contacts);
		lvContacts.setAdapter(new ContactListAdapter(getActivity(), contacts));
	}
	
	public void setStatuses(List<Contact> contacts) {
		if(mIsBound) {
			for(Contact contact : contacts) {
				contact.setStatus(mBackend.getBuddyStatus(contact.getOnionAddress()) == Status.ONLINE ? ContactStatus.ONLINE : ContactStatus.OFFLINE);
			}
		}
	}
	

}
