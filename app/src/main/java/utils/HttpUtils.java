package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lkl on 2016/4/7.
 */
public class HttpUtils {
    public static String getCourseUrl() {
        return ALL_COURSE;
    }

    private final static String ALL_COURSE = "http://10.108.150.154:7001/app/allCourse.json";

    public final static String getIP(){
        return"http://10.108.150.154:7001";//626
    }

    public static String GetResponseFromServer(String path, String code,String name, String password ){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        Map<String,String> map = new HashMap<String, String>();
        if(!"".equals(name)){
            map.put("userName",name);
        }
        if(!"".equals(password)){
            map.put("password",password);
        }
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(path);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for(Map.Entry<String,String>entry:map.entrySet()){
                params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity1 = response.getEntity();
                result = EntityUtils.toString(entity1,code);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static String sendPostForAllCourse(String Ip, String encoding) {
        StringBuilder result= new StringBuilder();
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(Ip);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(1*1000);
            urlConnection.setReadTimeout(1*1000);
            urlConnection.setRequestProperty("Accept-Charset", "utf-8");
            int responseCode = urlConnection.getResponseCode();
            if(responseCode==200){
                in = urlConnection.getInputStream();
            }
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=reader.readLine())!=null){
                result.append(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(urlConnection!=null)urlConnection.disconnect();
        }

        return result.toString();
    }
}
