package apdroid.clinica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apdroid.clinica.R;

/**
 * Created by ANTONIO on 05/09/2015.
 */
public class DrawerListAdapter extends ArrayAdapter<DrawerItem> {


    public DrawerListAdapter(Context context, List<DrawerItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerListAdapterHolder drawerListAdapterHolder = null;

        if( convertView == null || !(convertView.getTag() instanceof DrawerListAdapterHolder)  ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_menu_item, null);

            drawerListAdapterHolder = new DrawerListAdapterHolder();
            drawerListAdapterHolder.name = (TextView)convertView.findViewById(R.id.name);
            drawerListAdapterHolder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(drawerListAdapterHolder);

        }else{
            drawerListAdapterHolder = (DrawerListAdapterHolder)convertView.getTag();
        }

        DrawerItem drawerItem = getItem(position);
        drawerListAdapterHolder.icon.setImageResource(drawerItem.getIconId());
        drawerListAdapterHolder.name.setText(drawerItem.getName());

        return convertView;
    }

    static class DrawerListAdapterHolder {
        TextView name;
        ImageView icon;
    }

}
