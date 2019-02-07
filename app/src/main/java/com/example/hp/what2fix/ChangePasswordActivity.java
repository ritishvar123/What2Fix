package com.example.hp.what2fix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_email, et_new_pass, et_confirm_pass;
    Button btn_save_changes, btn_cancel;
    ProgressDialog progressDialog;
    String result;
    /*Connection con = null;
    String sqlURL = "jdbc:mysql://192.168.43.132:3306/bia";
    String sqlUser = "root";
    String sqlPass = "";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        et_email = (EditText) findViewById(R.id.editText13);
        et_new_pass = (EditText) findViewById(R.id.editText14);
        et_confirm_pass = (EditText) findViewById(R.id.editText15);
        btn_save_changes = (Button) findViewById(R.id.button7);
        btn_cancel = (Button) findViewById(R.id.button6);
        progressDialog = new ProgressDialog(this);
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String new_pass = et_new_pass.getText().toString();
                String confirm_pass = et_confirm_pass.getText().toString();
                if (email.trim().length() == 0 || new_pass.trim().length() == 0 || confirm_pass.trim().length() == 0) {
                    if (email.trim().length() == 0)
                        et_email.setError("Field can't Empty");
                    if (new_pass.trim().length() == 0)
                        et_new_pass.setError("Field can't Empty");
                    if (confirm_pass.trim().length() == 0)
                        et_confirm_pass.setError("Field can't Empty");
                } else if (!email.contains("@")) {
                    et_email.setError("Invalid email address");
                } else if (new_pass.length() < 4) {
                    et_new_pass.setError("Password is too short");
                } else if (!new_pass.equals(confirm_pass)) {
                    et_new_pass.setError("Password do not match");
                    et_confirm_pass.setError("Password do not match");
                    Toast.makeText(ChangePasswordActivity.this, "Password do not match", Toast.LENGTH_LONG).show();
                } else {
                    BackgroundTask(email, confirm_pass);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void BackgroundTask(final String email, final String new_pass) {
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        String url = "https://boxinall.in/kshitiz/forgotpassword.php";
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    result = jsonObject1.getString("Result");
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Email", email);
                map.put("Password", new_pass);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result.equalsIgnoreCase("Success")) {
                    Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Email id is not registered.Please Try Again!!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();

            }
        }, 4000);
    }


}
    /*try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sqlURL, sqlUser, sqlPass);
        if(con==null){
            z = "No Internet Connection";
        }else {
            Statement st = con.createStatement();
            String query1 = "SELECT * FROM login WHERE email = '"+strings[0]+"';";
            ResultSet rs = st.executeQuery(query1);
            if(!rs.next()) {
                z = "Email address not found in database !!";
                isSuccess = false;
                rs.close();
            } else {
                String query2 = "UPDATE login SET password = '" + strings[1] + "' WHERE email = '" + strings[0] + "';";
                int c = st.executeUpdate(query2);
                if (c == 1) {
                    z = "Password changed Successfully !!";
                    isSuccess = true;
                } else {
                    z = "Sorry! Password could not changed.";
                    isSuccess = false;
                }
            }
            con.close();
        }
    } catch(SQLException se){}*/
