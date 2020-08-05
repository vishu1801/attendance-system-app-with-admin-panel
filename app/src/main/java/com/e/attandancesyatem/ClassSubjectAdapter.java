package com.e.attandancesyatem;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ClassSubjectAdapter extends ArrayAdapter {

    Context mContext;
    Activity mActivity;
    private ArrayList<ClassSubject> dataSet;
//  private ArrayList<DatabaseReference> databaseReferenceArrayList;

    private class ViewHolder {
        TextView clas,subject;
    }

    public ClassSubjectAdapter(ArrayList<ClassSubject> data, Context context, Activity mActivity) {
        super(context, R.layout.adapter_list_view, data);
        this.dataSet = data;
        this.mContext = context;
        this.mActivity = mActivity;
//        this.databaseReferenceArrayList = databaseList;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public ClassSubject getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        ClassSubjectAdapter.ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ClassSubjectAdapter.ViewHolder();

            view=mActivity.getLayoutInflater().inflate(R.layout.adapter_list_view, null);
            viewHolder.clas=view.findViewById(R.id.clas);
            viewHolder.subject=view.findViewById(R.id.subject);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ClassSubjectAdapter.ViewHolder) view.getTag();
        }


        ClassSubject item = getItem(i);

        viewHolder.clas.setText(item.getClasss());
        viewHolder.subject.setText(item.getSubject());
        Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide);
        viewGroup.startAnimation(animation);
        return view;
    }



}
