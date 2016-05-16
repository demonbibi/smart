package teacher;

import android.support.design.widget.TabLayout;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by CC on 2016/5/9.
 */
public class HttpHelper {
    private static final String TAG = "HTTPHELPER";
    public static String fetchStudentsInClass(String param){
        HttpURLConnection con = null;
        InputStream in = null;
        String result = "";
        try {
            URL url = new URL(param);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3*1000);
            con.setReadTimeout(5*1000);
            con.setRequestProperty("Accept-Charset", "utf-8");
            int responseCode = con.getResponseCode();
            Log.i("responseCode", responseCode + "");
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                in = con.getInputStream();
                result = inputStream2String(in);
                Log.i(TAG, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return result;
    }

    private static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        while((i=is.read())!=-1){
            byteArrayOutputStream.write(i);
        }
        return byteArrayOutputStream.toString();
    }

    public static String fetchSceneScore(String path, int courseId) {
        HttpURLConnection con = null;
        InputStream in = null;
//        path = path + courseId;
        String result = "";
        try{
            URL url = new URL(path);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3*1000);
            con.setReadTimeout(5*1000);
            con.setRequestProperty("Accept-Charset", "utf-8");
            int responseCode = con.getResponseCode();
            if(responseCode==200){
                in = con.getInputStream();
                result = inputStream2String(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                con.disconnect();
            }
        }
        Log.i(TAG, result);
        return result;
    }

    public static String fetchExerciseRecord(String path, int studentId){
        HttpURLConnection con = null;
        InputStream in = null;
//        path = path + studentId;
        String result = "";
        try{
            URL url = new URL(path);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3*1000);
            con.setReadTimeout(5*1000);
            con.setRequestProperty("Accept-Charset", "utf-8");
            int responseCode = con.getResponseCode();
            if(responseCode==200){
                in = con.getInputStream();
                result = inputStream2String(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                con.disconnect();
            }
        }
        Log.i(TAG, result);
        return result;
    }


    public static int teacherUpload(String path, String comment){
        HttpURLConnection con = null;
        OutputStream out = null;
        int responseCode=400;
        try{
            comment = URLEncoder.encode(comment, "utf-8");
            path = path + comment;
            Log.i(TAG, path);
            byte[] data = path.getBytes("utf-8");
            URL url = new URL(path);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(3*1000);
//            con.setReadTimeout(5*1000);
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Content-Length", String.valueOf(data.length));
            out = con.getOutputStream();
            out.write(data);
            out.flush();
            responseCode = con.getResponseCode();
            Log.i(TAG, responseCode+"");
            if(responseCode==200){
                Log.i(TAG, "uploadsuccess");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                con.disconnect();
            }
        }
        return responseCode;
    }

}
