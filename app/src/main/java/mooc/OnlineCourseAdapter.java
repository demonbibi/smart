package mooc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.piano.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by CC on 2016/3/9.
 */
public class OnlineCourseAdapter extends ArrayAdapter<String> implements
        AbsListView.OnScrollListener {

    private GridView mCourseWall;
    private Set<BitmapDownloadTask> taskSet;
    private LruCache<String, Bitmap> mMemoryCache;
    private int mVisibleItemCount;
    private int mFirstVisibleItem;
    private boolean isFirstEnter = false;
    private ArrayList<String> coursePicUrl;

    public OnlineCourseAdapter(Context context, int textViewResourceId,
                               GridView courseWall, ArrayList<String> coursePicUrl){
        super(context, textViewResourceId, coursePicUrl);
        mCourseWall = courseWall;
        this.coursePicUrl = coursePicUrl;
        taskSet = new HashSet<BitmapDownloadTask>();
        int memoryMax = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = memoryMax/8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mCourseWall.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
        } else {
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        if(isFirstEnter || mVisibleItemCount>0){
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }
    }

    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        for(int i=firstVisibleItem; i<firstVisibleItem+visibleItemCount; i++){
            String imageUrl = coursePicUrl.get(i);
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if(bitmap==null){
                BitmapDownloadTask task = new BitmapDownloadTask();
                taskSet.add(task);
                task.execute(imageUrl);
                Log.i("Download", "begin");
            } else {
                ImageView imageView = (ImageView) mCourseWall.findViewWithTag(imageUrl);
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void cancelAllTasks() {
        if(taskSet!=null){
            for(BitmapDownloadTask task:taskSet){
                task.cancel(false);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        ViewHolder mHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.onlinecourse_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.mPic = (ImageView) convertView.findViewById(R.id.courseViewItem);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mPic.setTag(url);
        setImageView(url, mHolder.mPic);
//        Log.i("getView", ""+position);
        return convertView;
    }

    public void setImageView(String imageUrl, ImageView imageView){
        Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.load);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if(getBitmapFromMemoryCache(key)==null){
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

//    private int calculateInSampleSize(BitmapFactory.Options options,
//                                                        int reqWidth, int reqHeight) {
//        // 源图片的高度和宽度
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            // 计算出实际宽高和目标宽高的比率
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
//            // 一定都会大于等于目标的宽和高。
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//        return inSampleSize;
//    }
//    private Bitmap decodeSampledBitmapFromStream(InputStream in, int reqWidth, int reqHeight) {
//        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(in, null, options);
//        // 调用上面定义的方法计算inSampleSize值
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//        // 使用获取到的inSampleSize值再次解析图片
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeStream(in, null, options);
//    }
    class BitmapDownloadTask extends AsyncTask<String, Void ,Bitmap>{
        String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = downloadBitmap(params[0]);
            if (bitmap != null) {
                // 图片下载完成后缓存到LrcCache中
                addBitmapToMemoryCache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView imageView = (ImageView) mCourseWall.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskSet.remove(this);
        }

        private Bitmap downloadBitmap(String param) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            InputStream in = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                int responseCode = con.getResponseCode();
                Log.i("reponseCode", responseCode + "");
                if (responseCode == 200) {
                    // 从服务器返回一个输入流
                    in = con.getInputStream();
                }
//                bitmap = decodeSampledBitmapFromStream(in, 100, 100);
                bitmap = BitmapFactory.decodeStream(in);
                Log.i("compressed", "cc");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }

    private class ViewHolder {
        ImageView mPic;
    }
}
