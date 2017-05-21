package application.minor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class adapterspot extends BaseAdapter {
    private Context mContext;
    public ArrayList<spot1> spotlist=new ArrayList<spot1>();

    public adapterspot(Context context, ArrayList<spot1> list) {
        mContext = context;
        this.spotlist = list;
    }

    @Override
    public int getCount() {
        return spotlist.size();
    }

    @Override
    public spot1 getItem(int position) {
        return spotlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spotadapter, null);
        }

        ImageView mImgSong = (ImageView) convertView.findViewById(R.id.img_listitem_file);
        TextView message = (TextView) convertView.findViewById(R.id.spotmessage);
        TextView address = (TextView) convertView.findViewById(R.id.spotplace);
        TextView date = (TextView) convertView.findViewById(R.id.spotdate);



        message.setText(spotlist.get(position).getMessage());
        address.setText(spotlist.get(position).getPlace());
        date.setText(spotlist.get(position).getDate());

        return convertView;
    }
    public void setSpotlist(ArrayList<spot1> list) {
        spotlist = list;
        this.notifyDataSetChanged();
    }
}
