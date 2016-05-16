package fragment;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.piano.R;
import com.example.piano.app.MainActivity;

import org.json.JSONObject;

import utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText loginName,loginPassword;
    Button loginSure,loginCancel;
    MainActivity activity;
    TextView loginTip;
    LoginFragment loginfragment;
    private String name;
    private String password;
    private String userId ="", userName = "", userPortrait = "";


    public LoginFragment() {
        // Required empty public constructor
        loginfragment = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mooc_login,container,false);
        loginName = (EditText) view.findViewById(R.id.login_name);
        loginPassword = (EditText)view.findViewById(R.id.login_password);
        loginSure = (Button)view.findViewById(R.id.login_sure);
        loginCancel = (Button)view.findViewById(R.id.login_cancel);
        activity = (MainActivity)getActivity();
        loginTip = (TextView)view.findViewById(R.id.login_tip);
        name = null;
        password = null;
        loginSure.setOnClickListener(loginListener);
        return view;
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.login_sure:
                    loginTip.setVisibility(View.GONE);
                    name = loginName.getText().toString();
                    password = loginPassword.getText().toString();
                    if(!"".equals(name) && !"".equals(password)){
                        myTask.execute();
                    }else{
                        loginTip.setText("用户名或者密码不能为空");
                        loginTip.setVisibility(View.VISIBLE);
                    }
            }
        }
    };

    protected AsyncTask<String,Integer,String> myTask = new AsyncTask<String, Integer, String>() {
        @Override
        protected String doInBackground(String... params) {
            String path = HttpUtils.getIP()+"/app/appLogin.json";
            String resJson = HttpUtils.GetResponseFromServer(path, "utf-8", name, password);
            Log.e("test","test");
            try{
                JSONObject jsonObject = new JSONObject(resJson);
                String errNo = jsonObject.getString("errNo");
                String errMsg = jsonObject.getString("errMsg");
                if("0".equals(errNo)){
                    userId = jsonObject.getString("userId");
                    userName = jsonObject.getString("userName");
                    userPortrait = jsonObject.getString("userPortrait");
                    Log.e("test", "testsuccess");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if(!"".equals(userId) && !"".equals(userName) && !"".equals(userPortrait)){
                MoocFragment moocFragment = new MoocFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content,moocFragment);
                fragmentTransaction.commit();
            }
        }
    };


}
