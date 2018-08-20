package in.myinnos.awesomeimagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import in.myinnos.awesomeimagepicker.R;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by MyInnos on 03-11-2016.
 */
public class CustomImageSelectAdapter extends CustomGenericAdapter<Image> {

    public CustomImageSelectAdapter(Activity activity, Context context, ArrayList<Image> images) {
        super(activity, context, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_image_select, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_image_select);
            viewHolder.view = convertView.findViewById(R.id.view_alpha);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.getLayoutParams().width = size;
        viewHolder.imageView.getLayoutParams().height = size;

        viewHolder.view.getLayoutParams().width = size;
        viewHolder.view.getLayoutParams().height = size;

        if (arrayList.get(position).isSelected) {
            viewHolder.view.setAlpha(0.5f);
            ((FrameLayout) convertView).setForeground(context.getResources().getDrawable(R.drawable.ic_done_white));

        } else {
            viewHolder.view.setAlpha(0.0f);
            ((FrameLayout) convertView).setForeground(null);
        }

        Uri uri = Uri.fromFile(new File(arrayList.get(position).path));

        /*
        Glide.with(context).load(uri)
                .placeholder(R.color.colorAccent)
                .override(200, 200)
                .crossFade()
                .centerCrop()
                .into(viewHolder.imageView);*/

        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.overrideOf(200, 200))
                .apply(RequestOptions.placeholderOf(R.color.colorAccent))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public View view;
    }

}
