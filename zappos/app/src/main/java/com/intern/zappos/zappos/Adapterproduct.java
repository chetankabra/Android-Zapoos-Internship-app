package com.intern.zappos.zappos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by SONY on 9/24/2015.
 */

public class Adapterproduct extends RecyclerView.Adapter <Adapterproduct.ViewHolderProduct> {
    private LayoutInflater layoutInflater;
    private ArrayList<Alldetails> alldetails= new ArrayList<>();
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Context context;

    public Adapterproduct(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        imageLoader = volleySingleton.getImageLoader();

    }

    public void setalldetails(ArrayList<Alldetails> alldetails){
        this.alldetails = alldetails;
        notifyItemRangeChanged(0, alldetails.size());
    }


    class ViewHolderProduct extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private ImageView image_product;
        private TextView name;
        private TextView Rating;
        private TextView price;
        private RatingBar star;

        public ViewHolderProduct(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image_product = (ImageView) itemView.findViewById(R.id.image_product);
            star = (RatingBar) itemView.findViewById(R.id.ratingBar);
            name = (TextView) itemView.findViewById(R.id.Name);
            price = (TextView) itemView.findViewById(R.id.price);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String asin = alldetails.get(position).asin;
            Intent i = new Intent(context,ProductInfo.class);
            i.putExtra("asin",asin);
            context.startActivity(i);

        }
    }

    @Override
    public ViewHolderProduct onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =layoutInflater.inflate(R.layout.product_box, viewGroup,false);
        ViewHolderProduct viewHolder = new ViewHolderProduct(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderProduct viewHolderProduct, int i) {
        Alldetails all = alldetails.get(i);
        String url = all.imageUrl;
        viewHolderProduct.price.setText(all.price);
        viewHolderProduct.name.setText(all.productName);
        viewHolderProduct.star.setIsIndicator(true);
        viewHolderProduct.star.setMax(5);
        viewHolderProduct.star.setRating(Float.parseFloat(all.productRating));
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                viewHolderProduct.image_product.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return alldetails.size();
    }
}
