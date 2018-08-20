package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 3. 21..
 */

public class WebtoonContentsEndFragment extends Fragment {


    private String cardId;
    private ImageView content_thumb;
    private CircleImageView profile_image;

    private TextView writter_name;
    private LinearLayout follow_button;
    private TextView follow_button_text;

    private ParseUser currentUser;
    private TextView content_name;


    private ParseObject channelOb;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    private AppCompatActivity activity;
    private RequestManager requestManager;

    private Boolean serieseIn;
    private TabLayout my_tabs;
    private ViewPager my_container;
    private FunctionBase functionBase;

    public WebtoonContentsEndFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        cardId = getArguments().getString("cardId");
        Log.d("cardId", cardId);
        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_webtoon_end, container, false);

        content_thumb = (ImageView) view.findViewById(R.id.content_thumbnail);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);

        content_name = (TextView) view.findViewById(R.id.content_name);
        writter_name = (TextView) view.findViewById(R.id.writter_name);
        follow_button = (LinearLayout) view.findViewById(R.id.follow_button);
        follow_button_text = (TextView) view.findViewById(R.id.follow_button_text);


        my_tabs = (TabLayout) view.findViewById(R.id.my_tabs);
        my_container = (ViewPager) view.findViewById(R.id.my_container);

        follow_button.setClickable(false);




        ParseQuery artistPostQuery = ParseQuery.getQuery("ArtistPost");
        artistPostQuery.include("user");
        artistPostQuery.getInBackground(cardId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject contentOb, ParseException e) {

                if(e==null){

                    if(contentOb.get("seriese_in") == null){

                        serieseIn = false;

                    } else {

                        serieseIn = contentOb.getBoolean("seriese_in");

                    }

                    //강제로 연재 보기 없앰
                    //serieseIn = false;


                    FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {


                        private final String[] menuFragmentNamesType1 = new String[]{

                                "연재보기",
                                "추천작품",
                                "작가작품"

                        };

                        private final String[] menuFragmentNamesType2 = new String[]{

                                "추천작품",
                                "작가작품"

                        };

                        @Override
                        public Fragment getItem(int position) {

                            if(serieseIn){

                                switch (position){

                                    case 0:

                                        ContentSerieseFragment contentSerieseFragment = new ContentSerieseFragment();

                                        Bundle timelineBundle = new Bundle();
                                        timelineBundle.putString("postId", contentOb.getObjectId());
                                        timelineBundle.putInt("page", position);
                                        contentSerieseFragment.setArguments(timelineBundle);


                                        return contentSerieseFragment;

                                    case 1:

                                        ContentRecommendFragment contentRecommendFragment = new ContentRecommendFragment();

                                        Bundle recommendBundle = new Bundle();
                                        recommendBundle.putString("postId", contentOb.getObjectId());
                                        recommendBundle.putInt("page", position);
                                        contentRecommendFragment.setArguments(recommendBundle);

                                        return contentRecommendFragment;

                                    case 2:

                                        ContentEndFragment contentEndFragment = new ContentEndFragment();

                                        Bundle contentEndBundle = new Bundle();
                                        contentEndBundle.putInt("page", position);
                                        contentEndBundle.putString("cardId", contentOb.getObjectId());
                                        contentEndFragment.setArguments(contentEndBundle);

                                        return contentEndFragment;

                                    default:

                                        return null;

                                }

                            } else {

                                switch (position){

                                    case 0:

                                        ContentRecommendFragment contentRecommendFragment = new ContentRecommendFragment();

                                        Bundle recommendBundle = new Bundle();
                                        recommendBundle.putString("postId", contentOb.getObjectId());
                                        recommendBundle.putInt("page", position);
                                        contentRecommendFragment.setArguments(recommendBundle);

                                        return contentRecommendFragment;

                                    case 1:

                                        ContentEndFragment contentEndFragment = new ContentEndFragment();

                                        Bundle contentEndBundle = new Bundle();
                                        contentEndBundle.putString("cardId", contentOb.getObjectId());
                                        contentEndBundle.putInt("page", position);
                                        contentEndFragment.setArguments(contentEndBundle);

                                        return contentEndFragment;

                                    default:

                                        return null;

                                }

                            }



                        }

                        @Override
                        public int getCount() {

                            if(serieseIn){

                                return menuFragmentNamesType1.length;

                            } else {

                                return menuFragmentNamesType2.length;

                            }


                        }

                        @Override
                        public CharSequence getPageTitle(int position) {

                            if(serieseIn){

                                return menuFragmentNamesType1[position];

                            } else {

                                return menuFragmentNamesType2[position];

                            }

                        }

                    };

                    my_container.setAdapter(pageAdapter);

                    my_tabs.setupWithViewPager(my_container);

                    content_name.setText(contentOb.getString("title"));
                    functionBase.generalImageLoading(content_thumb, contentOb, requestManager);

                    if(contentOb.get("user") != null){

                        functionBase.profileImageLoading(profile_image, contentOb.getParseObject("user"), requestManager);
                        functionBase.profileNameLoading(writter_name, contentOb.getParseObject("user"));

                        follow_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(currentUser == null){

                                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                } else {

                                    FunctionFollow functionFollow = new FunctionFollow(getActivity());
                                    functionFollow.followFunctionPointButton(follow_button, follow_button_text, contentOb.getParseObject("user"));

                                }

                            }
                        });

                        if(functionBase.parseArrayCheck(currentUser, "follow_array", contentOb.getParseObject("user").getObjectId())){

                            //팔로우 취소
                            follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);
                            follow_button_text.setText("팔로우 취소");

                        } else {

                            //팔로우

                            follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);
                            follow_button_text.setText("팔로우");

                        };

                        writter_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getActivity(), UserActivity.class);
                                intent.putExtra("userId", contentOb.getParseObject("user").getObjectId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                            }
                        });

                        profile_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getActivity(), UserActivity.class);
                                intent.putExtra("userId", contentOb.getParseObject("user").getObjectId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);


                            }
                        });

                    }





                } else {

                    e.printStackTrace();

                }

            }

        });



        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();



    }


}
