package com.ereinecke.eatsafe.util;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ereinecke.eatsafe.R;
import com.ereinecke.eatsafe.data.OpenFoodContract;
import com.squareup.picasso.Picasso;

import static com.ereinecke.eatsafe.util.Utility.Logd;

/**
 * List adapter to display list of products downloaded to this device
 */

public class ProductListAdapter extends CursorAdapter {

    private final static String LOG_TAG = ProductListAdapter.class.getSimpleName();

    public static class ViewHolder {
        public ImageView productImage = null;
        public TextView productName = null;
        public TextView productBrand = null;
        public TextView productCode = null;
        public int productImageSize;

        public ViewHolder(View view) {
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productBrand = view.findViewById(R.id.product_brand);
            productCode = view.findViewById(R.id.code);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public ProductListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Picasso picasso = Picasso.get();

        // TODO: Figure out how big to make the thumbnail, depends on listPreferredItemHeight
        String imgUrl = cursor.getString(cursor.getColumnIndex(OpenFoodContract.ProductEntry.IMAGE_URL));
        try {
            /* Picasso.with(context).load(imgUrl) */
            picasso.load(imgUrl)
                    // Want a square thumbnail: not sure how to get the right size
                    .resize(200, 200)
                    .centerCrop()
                    .into(viewHolder.productImage);
        } catch (Exception e) {
            Logd(LOG_TAG,"Exception loading " + imgUrl + "; Stacktrace:\n" + e.getMessage());
        }

        String productName = cursor.getString(cursor.getColumnIndex(OpenFoodContract.ProductEntry.PRODUCT_NAME));
        viewHolder.productName.setText(productName);

        String productBrand = cursor.getString(cursor.getColumnIndex(OpenFoodContract.ProductEntry.BRANDS));
        viewHolder.productBrand.setText(productBrand);

        String productCode = cursor.getString(cursor.getColumnIndex(OpenFoodContract.ProductEntry._ID));
        viewHolder.productCode.setText(productCode);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }
}
