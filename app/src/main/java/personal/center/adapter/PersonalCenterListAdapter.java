package personal.center.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.piano.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by CC on 2016/4/7.
 */
public class PersonalCenterListAdapter extends BaseAdapter {
    private String[] text;
    private Drawable[] drawableLeft;
    private Drawable drawableRight;
    private LayoutInflater mInflater;
    private Context context;
    private View oldView;

    public PersonalCenterListAdapter(String[] text, Drawable[] drawableLeft,
                                     Drawable drawableRight, Context context) {
        this.text = text;
        this.drawableLeft = drawableLeft;
        this.drawableRight = drawableRight;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getCount() {
        return text.length;
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
        ViewHolder mHolder;
        if(convertView==null) {
            convertView = mInflater.inflate(R.layout.personal_center_listitem,
                    parent, false);
            mHolder = new ViewHolder();
            mHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTextView.setText(text[position]);
        mHolder.mTextView.setCompoundDrawables(drawableLeft[position],
                null, drawableRight, null);
        mHolder.mTextView.setTag(position);
//        if(position==0){
//            if(oldView==null){
//                oldView = mHolder.mTextView;
//            }
//            mHolder.mTextView.setBackgroundColor(
//                    context.getResources().getColor(R.color.listitem_selected)
//            );
//        }
        Log.i("Adapter","getView"+position);
        return convertView;

    }

    public View getOldView() {
        return oldView;
    }

    public void setOldView(View oldView) {
        this.oldView = oldView;
    }


    private static class ViewHolder {
        private TextView mTextView;
    }
}
