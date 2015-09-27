package com.intern.zappos.zappos;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ProductInfo extends ActionBarActivity {
    public TextView brandName,description,genders,defaultProductType,productName;
    private ImageView imageview1;
    private ImageLoader imageLoader;
    private VolleySingleton volleySingleton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ActionBar br = getSupportActionBar();
        br.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#45a4af")));
        br.setDisplayHomeAsUpEnabled(true);
        volleySingleton = VolleySingleton.getInstance(getBaseContext());
        imageLoader = volleySingleton.getImageLoader();

        brandName = (TextView) findViewById(R.id.brandnameinfo);
        description = (TextView) findViewById(R.id.description);
        genders = (TextView) findViewById(R.id.genders);
        defaultProductType = (TextView) findViewById(R.id.ProductType);
        productName = (TextView) findViewById(R.id.productName);
        imageview1 = (ImageView) findViewById(R.id.imageView1);

        String asin = getIntent().getStringExtra("asin");
        String Url = "https://zappos.amazon.com/mobileapi/v1/product/asin/";
        String url = Url+""+asin;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //txtQuery.setText(response.toString());
                try {
                    String brandName = response.getString("brandName");
                    String description = response.getString("description");
                    String genders = response.getString("genders");
                    String asin = response.getString("asin");
                    String defaultProductType = response.getString("defaultProductType");
                    String URL = response.getString("defaultImageUrl");
                    String productName = response.getString("productName");
                    description = description.replace("<ul>","");

                    description = description.replace("<li>", "");
                    description = description.replace("</li>","");
                    description = description.replace("</ul>","");
                    assign(brandName, description, genders, asin, defaultProductType, URL, productName);

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

    private void assign(String brandN, String descript, String gender, String asin, String ProductType, String url, String productN) {
            brandName.setText("BrandName: "+brandN);
            productName.setText("Product Name:" +productN);
            description.setText("Description: "+descript);
            genders.setText(Html.fromHtml ("Gender: "+gender));
            defaultProductType.setText("ProductType: "+ProductType);

            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageview1.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
