package com.example.navigationview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textC, textU, textM;
    RequestQueue requestQueue;
    String URL = "https://my-json-server.typicode.com/DianaAvilesCh/Json/db";
    ArrayList<String> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        textM = (TextView) findViewById(R.id.txtMensaje);
    }

    public void stringRequest() {
        Intent intent = new Intent(MainActivity.this, Navigation.class);
        StringRequest request = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray JSONlista = object.getJSONArray("usuarios");
                            int j = 0;

                            for (int i = 0; i < JSONlista.length(); i++) {
                                JSONObject user = JSONlista.getJSONObject(i);
                                if (user.getString("usuario").equals(textU.getText().toString())
                                        && user.getString("contraseña").equals(textC.getText().toString())) {
                                    Log.d("DATOOS", user.toString());
                                    Bundle b = new Bundle();
                                    b.putString("NOMBRE", user.getString("nombre"));
                                    b.putString("IMAGEN", user.getString("img"));
                                    b.putString("ROL", user.getString("rol"));
                                    intent.putExtras(b);

                                    i = JSONlista.length();
                                    j=-1;
                                }
                                j += 1;

                            }
                            if (j > 0) {
                                textM.setVisibility(View.VISIBLE);
                            } else {
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        if (error.networkResponse == null
                                && error instanceof NoConnectionError
                                && error.getMessage().contains("javax.net.ssl.SSLHandshakeException")) {
                            // Se ha producido un error con el certificado SSL y la conexión ha sido
                            // rechazada
                        }
                    }
                });
        requestQueue.add(request);
    }

    public void btIngresar(View view) {
        textU = (TextView) findViewById(R.id.txtUsuario);
        textC = (TextView) findViewById(R.id.txtContra);

        stringRequest();

    }
}