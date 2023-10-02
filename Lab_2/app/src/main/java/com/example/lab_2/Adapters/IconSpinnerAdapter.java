package com.example.lab_2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab_2.AdditionalClasses.IconClass;

import java.util.ArrayList;

import com.example.lab_2.R;

public class IconSpinnerAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private ArrayList<IconClass> iconList;

    public IconSpinnerAdapter(Context context, ArrayList<IconClass> iconList){
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.iconList = iconList;
    }

    @Override
    public int getCount()
    {
        return iconList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return iconList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = layoutInflater.inflate(R.layout.spinner_item, parent, false);

        IconClass iconBrand = (IconClass) getItem(position);

        TextView tvIconName = (TextView) view.findViewById(R.id.tvBrandName);
        tvIconName.setText(iconBrand.getBrandName());

        TextView tvBrandOther = (TextView) view.findViewById(R.id.tvBrandSite);
        tvBrandOther.setText(iconBrand.getBrandOther());

        ImageView ivBrandLogo = (ImageView) view.findViewById(R.id.ivBrandLogo);
        ivBrandLogo.setImageResource(iconBrand.getBrandLogo());

        return view;
    }
}
