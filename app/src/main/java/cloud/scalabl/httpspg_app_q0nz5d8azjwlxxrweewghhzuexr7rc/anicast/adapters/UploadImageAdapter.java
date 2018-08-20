/*
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import in.myinnos.awesomeimagepicker.models.Image;

public class UploadImageAdapter extends DragItemAdapter<Pair<Long, String>, UploadImageAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private RequestManager requestManager;
    private FunctionBase functionBase;
    private Boolean localFlag;

    public UploadImageAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress, RequestManager requestManager, FunctionBase functionBase, Boolean local) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        this.requestManager = requestManager;
        this.functionBase = functionBase;
        this.localFlag = local;
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mini_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String image = mItemList.get(position).second;
        //holder.mText.setText(text);
        holder.itemView.setTag(mItemList.get(position));

        ImageView imageLayout = ((ViewHolder)holder).getImage();

        Log.d("listInAdapter", mItemList.toString());
        Log.d("item", image);

        if(localFlag){

            requestManager
                    .load(image)
                    .into(imageLayout);

        } else {

            String[] imageUrlArrray = image.split("/");
            String imageFileName = imageUrlArrray[1];

            requestManager
                    .load(MediaManager.get().url().transformation(new Transformation().width("250").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .thumbnail(0.25f)
                    //.transition(new DrawableTransitionOptions().crossFade())
                    .into(imageLayout);
        }


    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        ImageView image;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            image = (ImageView) itemView.findViewById(R.id.patron_image);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        ImageView getImage(){
            return image;
        }

    }

}
