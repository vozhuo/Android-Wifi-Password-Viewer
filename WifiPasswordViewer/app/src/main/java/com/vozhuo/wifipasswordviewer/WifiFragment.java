package com.vozhuo.wifipasswordviewer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

;

/**
 * Created by qyz on 2016/5/14.
 */
public class WifiFragment extends ListFragment {

    private WifiViewer wifiViewer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifiinfo, container, false);
        wifiViewer = new WifiViewer();
        try {
            List<WifiInfo> wifiInfos = wifiViewer.View();
            ListView listView = (ListView) view.findViewById(android.R.id.list);
            WifiAdapter wifiAdapter = new WifiAdapter(wifiInfos, getActivity().getApplicationContext());
            listView.setAdapter(wifiAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public class WifiAdapter extends BaseAdapter {
        List<WifiInfo> wifiInfos = null;
        Context context;

        public WifiAdapter(List<WifiInfo> wifiInfos, Context context) {
            this.wifiInfos = wifiInfos;
            this.context = context;
        }

        @Override
        public int getCount() {
            return wifiInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return wifiInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_wifi, null);
            TextView textView = (TextView) convertView.findViewById(R.id.text1);
           // textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextColor(0xff2F4F4F);
            textView.setText("Wifi：" + wifiInfos.get(position).getSsid() +
                    "\n密码：" + wifiInfos.get(position).getPassword());

            return convertView;
        }
    }
}