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

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;
import com.woxthebox.draglistview.DragItemAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditPostActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditWebtoonActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseManagerDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.OrderSaveListener;

public class SerieseItemAdapter extends DragItemAdapter<Pair<Long, ParseObject>, SerieseItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;

    private FunctionBase functionBase;
    private RequestManager requestManager;
    private Context mContext;
    private ParseObject parentObject;
    private OrderSaveListener orderSaveListener;


    private void setOrderSaveListener(OrderSaveListener orderSaveListener){

        this.orderSaveListener = orderSaveListener;
    }

    public SerieseItemAdapter(Context context, ArrayList<Pair<Long, ParseObject>> list, int layoutId, int grabHandleId, boolean dragOnLongPress, ParseObject parentOb) {

        functionBase = new FunctionBase(context);
        requestManager = Glide.with(context);

        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        mContext = context;
        parentObject = parentOb;
        setItemList(list);

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);


        final ParseObject timelineOb = mItemList.get(position).second;

        if(position == 0){

            parentObject.put("first_item", timelineOb);
            parentObject.saveInBackground();

        }

        timelineOb.put("order", position + 1);
        timelineOb.saveInBackground();

        holder.itemView.setTag(mItemList.get(position));

        ImageView seriese_image = holder.getSerieseImage();
        TextView post_body = holder.getPostBody();
        TextView post_updated = holder.getUpdated();
        TextView price = holder.getPrice();
        TextView type = holder.getType();
        TextView free_date = holder.getFreeDate();
        TextView order = holder.getOrder();
        TextView open_status = holder.getOpenStatus();
        LinearLayout edit_button = holder.getEditButton();
        LinearLayout manage_button = holder.getManageButton();

        functionBase.generalImageLoading(seriese_image, timelineOb, requestManager);

        if(timelineOb.get("title") != null){

            post_body.setText( timelineOb.getString("title") );

        } else {

            post_body.setText( timelineOb.getString("body") );

        }

        if(timelineOb.get("order") != null){

            order.setText("회차: " + timelineOb.getInt("order") +"화");

        } else {

            order.setText("미입력");

        }

        if(timelineOb.get("open_flag") != null){

            if(timelineOb.getBoolean("open_flag")){

                open_status.setText("[공개]");

            } else {

                open_status.setText("[비공개]");

            }

        } else {

            open_status.setText("[공개]");

        }

        if(timelineOb.get("commercial") != null){

            ParseObject commercialOb = timelineOb.getParseObject("commercial");

            post_updated.setText( "오픈일: " + functionBase.dateToStringForDisplay( commercialOb.getDate("open_date") ) );

            if(commercialOb.get("type") != null){

                if(commercialOb.getString("type").equals("free")){

                    type.setText("무료");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.GONE);
                    free_date.setVisibility(View.GONE);

                } else if(commercialOb.getString("type").equals("charge")) {

                    type.setText("유료");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    price.setText( functionBase.makeComma(commercialOb.getInt("price")) + " BOX"  );
                    free_date.setVisibility(View.GONE);


                } else if(commercialOb.getString("type").equals("preview_charge")){

                    type.setText("부분유료(미리보기 구매)");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    price.setText( functionBase.makeComma(commercialOb.getInt("price")) + " BOX"  );
                    free_date.setVisibility(View.VISIBLE);
                    free_date.setText( "무료 공개일: " + functionBase.dateToStringForDisplay( commercialOb.getDate("free_date") ) );

                }

            } else {

                type.setText("무료");
                type.setVisibility(View.VISIBLE);
                price.setVisibility(View.GONE);
                free_date.setVisibility(View.GONE);

            }


        } else {

            free_date.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            post_updated.setVisibility(View.VISIBLE);
            post_updated.setText("설정 안됨");

        }

        manage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SerieseManagerDetailActivity.class);
                intent.putExtra("parentId", parentObject.getObjectId());
                intent.putExtra("parent_status", parentObject.getString("seriese_status"));
                intent.putExtra("artworkId", timelineOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(parentObject.getString("content_type").equals("post")){

                    Intent intent = new Intent(mContext, SerieseItemEditPostActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);


                } else if(parentObject.getString("content_type").equals("album")){

                    Intent intent = new Intent(mContext, SerieseItemEditIllustActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);


                } else if(parentObject.getString("content_type").equals("webtoon")){

                    Intent intent = new Intent(mContext, SerieseItemEditWebtoonActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);


                }

            }
        });


    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }


    class ViewHolder extends DragItemAdapter.ViewHolder {

        ImageView seriese_image;
        TextView post_body;
        TextView post_updated;
        TextView price;
        TextView type;
        TextView free_date;
        TextView order;
        TextView open_status;

        LinearLayout edit_button;
        LinearLayout manage_button;

        LinearLayout post_layout;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            seriese_image = (ImageView) itemView.findViewById(R.id.patron_image);
            post_body = (TextView) itemView.findViewById(R.id.post_body);
            post_updated = (TextView) itemView.findViewById(R.id.post_updated);
            price = (TextView) itemView.findViewById(R.id.price);
            type = (TextView) itemView.findViewById(R.id.type);
            free_date = (TextView) itemView.findViewById(R.id.free_date);
            order = (TextView) itemView.findViewById(R.id.order);
            open_status = (TextView) itemView.findViewById(R.id.open_status);
            post_layout = (LinearLayout) itemView.findViewById(R.id.post_layout);

            edit_button = (LinearLayout) itemView.findViewById(R.id.edit_button);
            manage_button = (LinearLayout) itemView.findViewById(R.id.manage_button);

        }

        @Override
        public void onItemClicked(View view) {

            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            Log.d("list", mItemList.toString());

        }

        @Override
        public boolean onItemLongClicked(View view) {

            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            Log.d("long click", "Item long clicked" );

            return true;

        }

        public ImageView getSerieseImage(){return seriese_image;}
        public TextView getPostBody(){return post_body;}
        public TextView getUpdated(){return post_updated;}
        public TextView getPrice(){return price;}
        public TextView getType(){return type;}
        public TextView getFreeDate(){return free_date;}
        public TextView getOrder(){return order;}
        public TextView getOpenStatus(){return open_status;}
        public LinearLayout getPostLayout(){return post_layout;}
        public LinearLayout getEditButton(){return edit_button;}
        public LinearLayout getManageButton(){return manage_button;}



    }
}
