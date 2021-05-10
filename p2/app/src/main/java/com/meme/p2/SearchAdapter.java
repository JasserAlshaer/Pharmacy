package com.meme.p2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] labels;
    private int[] images;

    public SearchAdapter(Context context, String[] labels, int[] images) {
        this.context = context;
        this.labels = labels;
        this.images = images;
    }

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.row_item,null);
        }
        ImageView imageView=convertView.findViewById(R.id.image_view);
        TextView textView=convertView.findViewById(R.id.text_view);
        imageView.setImageResource(images[position]);
        textView.setText(labels[position]);
        return convertView;
    }
}
