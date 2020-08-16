package com.virtualcode7ecuador.puercos.Runnable;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuador.puercos.POO.cRolUser;
import com.virtualcode7ecuador.puercos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.virtualcode7ecuador.puercos.LoginActivity.OrolUserArrayList;

public class cRunnableRol implements Runnable
{
    private Context context;
    private ArrayList<cRolUser>arrayList;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    public cRunnableRol(Context context1,ArrayList<cRolUser>OrolUsers)
    {
        this.context=context1;
        this.arrayList=OrolUsers;
    }

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public ArrayList<cRolUser> getArrayList() {
        return arrayList;
    }
    public void setArrayList(ArrayList<cRolUser> arrayList) {
        this.arrayList = arrayList;
    }
    @Override
    public void run()
    {
        String url = getContext().getString(R.string.url_select_roles);
        jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        cRolUser Or = new cRolUser();
                        Or.setId_auto_rol(jsonObject.getInt("pk_id_autoincrement_rol"));
                        Or.setDetalle_rol_user(jsonObject.getString("detalle_rol"));
                        OrolUserArrayList.add(Or);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}
