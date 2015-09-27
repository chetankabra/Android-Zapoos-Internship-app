package com.intern.zappos.zappos;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Product extends Fragment  {
    TextView txtquery;
    RecyclerView list_items;


    private ArrayList<Alldetails> alldetails= new ArrayList<>();
    private Adapterproduct adapterproduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_product, container, false);
        v.setBackground(new ColorDrawable( Color.parseColor("#ffffff")));
        list_items = (RecyclerView) v.findViewById(R.id.list_item);
        list_items.setLayoutManager(new LinearLayoutManager(getActivity()));
        //list_items.setHasFixedSize(true);
        adapterproduct = new Adapterproduct(getActivity());
        list_items.setAdapter(adapterproduct);
        return v;
    }
    public void showText(String query){
        alldetails= new ArrayList<>();
        String endp = "https://zappos.amazon.com/mobileapi/v1/search?term=";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = endp+""+query;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //txtQuery.setText(response.toString());
                try {
                    JSONArray results = response.getJSONArray("results");
                    String jsonResponse = "";
                    for(int index = 0 ; index < results.length(); index++) {
                        JSONObject jsonObj = results.getJSONObject(index);
                        String brandName = jsonObj.getString("brandName");
                        String productRating = jsonObj.getString("productRating");
                        String price = jsonObj.getString("price");
                        String asin = jsonObj.getString("asin");
                        String originalPrice = jsonObj.getString("originalPrice");
                        String URL = jsonObj.getString("imageUrl");
                        //String productURL = jsonObj.getString("productUrl");
                        String productName = jsonObj.getString("productName");

                        Alldetails all = new Alldetails(brandName,originalPrice,price,URL,asin,productRating,productName);
                        alldetails.add(all);
                    }
                    adapterproduct.setalldetails(alldetails);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                //findViewById(R.id.progressBar1).setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
        queue.add(jsObjRequest);
    }

}
