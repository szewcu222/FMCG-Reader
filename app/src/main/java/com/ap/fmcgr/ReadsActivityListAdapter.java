//Adapter listy 

package com.ap.fmcgr;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReadsActivityListAdapter extends ArrayAdapter<String[]> {
    Context context;
    int layoutResourceId;   
    List<String[]> data = null;
    
    static class RowBeanHolder
    {
        public TextView value;
		public TextView header;
    }
 
    public ReadsActivityListAdapter(Context context, int layoutResourceId, List<String[]> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
 
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
 
            holder = new RowBeanHolder();
            holder.header = (TextView)row.findViewById(R.id.textViewReadedListHeader);
            holder.value = (TextView)row.findViewById(R.id.textViewReadedListValue);
 
            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }
        
        String str[] = data.get(position);
               
        holder.header.setText(str[ProductInfo.headerColIndex]);
        holder.value.setText(str[ProductInfo.formatedValColIndex]);
        holder.value.setTextColor(Integer.parseInt(str[ProductInfo.valueColorColIndex]));

        
//        ProductInfoListRowBean object = data[position];
//        holder.value.setText(object.header);
//        holder.header.setText(object.value);
 
        return row;
    }

}
