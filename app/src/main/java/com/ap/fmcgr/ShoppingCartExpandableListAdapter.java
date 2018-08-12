package com.ap.fmcgr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class ShoppingCartExpandableListAdapter extends BaseExpandableListAdapter {

  private final SparseArray<ShoppingCartGroup> groups;
  public LayoutInflater inflater;
  public Activity activity;

  public ShoppingCartExpandableListAdapter(Activity act, SparseArray<ShoppingCartGroup> groups) {
    activity = act;
    this.groups = groups;
    inflater = act.getLayoutInflater();
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return groups.get(groupPosition).children.get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @SuppressLint("InflateParams")
  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    final String children = (String) getChild(groupPosition, childPosition);
    TextView text = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.shopping_cart_row_details, null);
    }
    text = (TextView) convertView.findViewById(R.id.textViewShoppingCartRowDetails);
    text.setText(children);
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();
      }
    });
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return groups.get(groupPosition).children.size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return groups.size();
  }

  @Override
  public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

  @Override
  public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @SuppressLint("InflateParams")
  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.shopping_cart_row_group, null);
    }
    ShoppingCartGroup group = (ShoppingCartGroup) getGroup(groupPosition);
    ((CheckedTextView) convertView).setText(group.string);
    ((CheckedTextView) convertView).setChecked(isExpanded);
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
} 