package prof7bit.torchat.android.gui;

import java.util.List;
import java.util.Map;

import prof7bit.torchat.android.R;
import ru.dtlbox.torchat.customviews.AvatarView;
import ru.dtlbox.torchat.entities.ChatMessage;
import ru.dtlbox.torchat.entities.MessageContainerSpike;
import ru.dtlbox.torchat.entities.Contact.ContactStatus;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChatsFragment extends Fragment {
	
	ListView lvLastMessages;
	
	Map<String,List<ChatMessage>> map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.fragment_contact_list, null);
		
		lvLastMessages = (ListView)v.findViewById(R.id.lv_contacts);
		
		
		
		
		
		
		return v;
	}
	
	@Override
	public void onResume() {
		
		
		map = MessageContainerSpike.getInstanse().getAllMessages();
		lvLastMessages.setAdapter(new HashMapAdapter(getActivity(), map));
		super.onResume();
	}
	
	public class HashMapAdapter extends BaseAdapter {

		Context context;
	    private Map<String,List<ChatMessage>> mData;
	    private String[] mKeys;
	    public HashMapAdapter(Context context, Map<String,List<ChatMessage>> data){
	    	this.context = context;
	        mData  = data;
	        mKeys = mData.keySet().toArray(new String[data.size()]);
	    }

	    @Override
	    public int getCount() {
	        return mData.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        return mData.get(mKeys[position]);
	    }

	    @Override
	    public long getItemId(int arg0) {
	        return arg0;
	    }

	    @Override
	    public View getView(int pos, View convertView, ViewGroup parent) {
	        LayoutInflater inflater = LayoutInflater.from(context);
	    	String name = mKeys[pos];
	        List<ChatMessage> messages = (List<ChatMessage>)getItem(pos);
	        
	        if(convertView==null) {
	        	convertView = inflater.inflate(R.layout.item_last_message, null);
	        }
	        
	        
	        AvatarView avAvatar = (AvatarView)convertView.findViewById(R.id.av_contact_icon);
			
			
			
	        ((TextView)convertView.findViewById(R.id.tv_contact_name)).setText(name);
	        ((TextView)convertView.findViewById(R.id.tv_contact_name)).setText(messages.get(messages.size()-1).getText());


	        return convertView;
	    }
	}
	
	
	

}
