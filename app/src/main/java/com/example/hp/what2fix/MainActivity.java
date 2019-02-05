package com.example.hp.what2fix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    EditText ET_USER_NAME, ET_USER_PASS;
    TextView FORGET_PASS;
    RelativeLayout relativeLayout;
    Button login;
    ProgressDialog progressDialog;
    String result = null;
    /* Connection con = null;
     String sqlURL = "jdbc:mysql://192.168.43.132:3306/bia";
     String sqlUser = "root";
     String sqlPass = "";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // for hide keyboard on startup
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_1);
        FORGET_PASS = (TextView) findViewById(R.id.textView17);
        ET_USER_NAME = (EditText) findViewById(R.id.editText);
        ET_USER_PASS = (EditText) findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(MainActivity.this);
        login = (Button) findViewById(R.id.button);
        if (!isOnline()) Snackbar.make(relativeLayout, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    String user = ET_USER_NAME.getText().toString();
                    String pass = ET_USER_PASS.getText().toString();
                    if (user.trim().length() == 0 || pass.trim().length() == 0) {
                        if (user.trim().length() == 0) ET_USER_NAME.setError("Enter valid Username");
                        if (pass.trim().length() == 0) ET_USER_PASS.setError("Enter valid Password");
                    } else BackgroundTask(user, pass);
                } else Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        FORGET_PASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
        });
    }

    public void BackgroundTask(final String user, final String pass) {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        String url = "https://boxinall.in/kshitiz/login.php";
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    result = jsonObject1.getString("Result");
                } catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", user);
                map.put("password", pass);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    if (!result.equalsIgnoreCase("N")) {
                        Toast.makeText(MainActivity.this, "Welcome " + result + " !!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        i.putExtra("Username", ET_USER_NAME.getText().toString());
                        i.putExtra("Password", ET_USER_PASS.getText().toString());
                        startActivity(i);
                        ET_USER_NAME.setText("");
                        ET_USER_PASS.setText("");
                        ET_USER_NAME.requestFocus();
                    } else Toast.makeText(MainActivity.this, "Invalid Username or Password !!" + result, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(MainActivity.this, "Connection error !!", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }, 5000);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }


}
