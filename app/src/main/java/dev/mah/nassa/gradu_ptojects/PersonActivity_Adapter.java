package dev.mah.nassa.gradu_ptojects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonActivity_Adapter extends BaseAdapter {
    private int layout;
    private Context context;
    private ArrayList<String> arrayList;

    public PersonActivity_Adapter(int layout, Context context, ArrayList<String> arrayList) {
        this.layout = layout;
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(layout,null);
        TextView textView=view.findViewById(R.id.activityItem);
        textView.setText(arrayList.get(position));
        return view;
    }
}

