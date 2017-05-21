package application.minor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class adapterclass extends BaseAdapter {
    private Context mContext;
    public ArrayList<StoredReminders> remlist=new ArrayList<StoredReminders>();

    public adapterclass(Context context, ArrayList<StoredReminders> list) {
        mContext = context;
        this.remlist = list;
    }

    @Override
    public int getCount() {
        return remlist.size();
    }

    @Override
    public StoredReminders getItem(int position) {
        return remlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapteritem, null);
        }

        ImageView mImgSong = (ImageView) convertView.findViewById(R.id.img_listitem_file);
        TextView message = (TextView) convertView.findViewById(R.id.remindermessage);
        TextView address = (TextView) convertView.findViewById(R.id.reminderaddress);
        TextView date = (TextView) convertView.findViewById(R.id.reminderdate);



        message.setText(remlist.get(position).getMessage());
        address.setText(remlist.get(position).getAddress());
        date.setText(remlist.get(position).getDate());

        return convertView;
    }
    public void setReminders(ArrayList<StoredReminders> list) {
        remlist = list;
        this.notifyDataSetChanged();
    }
}
