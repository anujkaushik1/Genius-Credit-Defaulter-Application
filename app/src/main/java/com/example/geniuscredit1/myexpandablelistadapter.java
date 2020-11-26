package com.example.geniuscredit1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class myexpandablelistadapter extends BaseExpandableListAdapter {

    Context context;
    List<String> items;
    Map<String,List<String>> subitems;

    public myexpandablelistadapter(Context context, List<String> items, Map<String, List<String>> subitems) {
        this.context = context;
        this.items = items;
        this.subitems = subitems;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return subitems.get(items.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return items.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return subitems.get(items.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertview, ViewGroup viewGroup) {

        String item = (String) getGroup(i);
        if(convertview==null){
            LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertview=layoutInflater.inflate(R.layout.navigationlistparent,null);

        }
        TextView textView= (TextView)convertview.findViewById(R.id.listparenttextview);
        textView.setText(item);


        return convertview;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertview, ViewGroup viewGroup) {
        String subitem =(String) getChild(i,i1);
        if(convertview==null){
            LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertview=layoutInflater.inflate(R.layout.navigationlistchild,null);

        }
        TextView textView1= (TextView)convertview.findViewById(R.id.listchildtextview);
        textView1.setText(subitem);
        return convertview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
