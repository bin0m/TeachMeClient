package com.project.levitg.teachmeclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Greg L on 04.12.2017.
 */

public class CustomAdapter extends ArrayAdapter<User> {

    public CustomAdapter(Context context, int resource, List<User> user) {
        super(context, resource, user);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_user_entry, parent, false);
        }

        User user = getItem(position);

        if (user != null) {
            TextView tvUserId = (TextView) v.findViewById(R.id.user_Id);
            TextView tvUserName = (TextView) v.findViewById(R.id.user_name);
            tvUserId.setText(user.getId());
            tvUserName.setText(user.getFullName());
        }

        return v;
    }
}