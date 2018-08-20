package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.RecommendCraftFollowListener;

/**
 * Created by ssamkyu on 2016. 11. 4..
 */

public class FunctionFollow {

    private Context context;
    private ParseUser currentUser;
    private FunctionBase functionBase;

    public FunctionFollow(Context context) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);

    }

    public void followFunction(final Button follow_button, final ParseObject followOb){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){


                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_button.setText("팔로우");

                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {



                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_button.setText("팔로우 취소");
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }

    public void followFunction(final BootstrapButton follow_button, final ParseObject followOb){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){


                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_button.setText("팔로우");

                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {



                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_button.setText("팔로우 취소");
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }


    public void followFunction(final LinearLayout follow_button, final TextView follow_text, final ParseObject followOb){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우");
                                        follow_text.setTextColor(Color.parseColor("#ffffff"));
                                        follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우 취소");
                                        follow_text.setTextColor(functionBase.likedColor);
                                        follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }


    public void followFunctionForUserActivity(final LinearLayout follow_button, final TextView follow_text, final ParseObject followOb){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우");
                                        follow_text.setTextColor(Color.parseColor("#ffffff"));
                                        follow_button.setBackgroundResource(R.drawable.button_profile_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                if (e == null) {

                                    Log.d("result", mapObject.toString());

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우 취소");
                                        follow_text.setTextColor(Color.parseColor("#ffffff"));
                                        follow_button.setBackgroundResource(R.drawable.button_profile_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }



    public void followFunction(final LinearLayout follow_button, final TextView follow_text, final ParseObject followOb, final RecommendCraftFollowListener recommendCraftFollowListener){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){


                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우");

                                        follow_button.setClickable(true);

                                        recommendCraftFollowListener.onFollowClicked();

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {



                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우 취소");
                                        follow_button.setClickable(true);

                                        recommendCraftFollowListener.onFollowClicked();

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }


    public void followFunctionPointButton(final LinearLayout follow_button, final TextView follow_text, final ParseObject followOb){

        follow_button.setClickable(false);

        if(functionBase.parseArrayCheck(currentUser, "follow_array", followOb.getObjectId())){

            TastyToast.makeText(context, "팔로우 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

            currentUser.removeAll("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.remove(followOb);
            currentUser.increment("follow_count", -1);

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "cancel");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우");
                                        follow_text.setTextColor(Color.parseColor("#ffffff"));
                                        follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });

                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우 취소를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();
                    }


                }
            });


        } else {

            TastyToast.makeText(context, "팔로우", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            currentUser.addAllUnique("follow_array", Arrays.asList(followOb.getObjectId()));
            ParseRelation followRelation = currentUser.getRelation("follow");
            followRelation.add(followOb);

            currentUser.increment("follow_count");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e== null){

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());
                        params.put("target", followOb.getObjectId());
                        params.put("action", "follow");

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        ParseCloud.callFunctionInBackground("follow", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                Log.d("result", mapObject.toString());

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        follow_text.setText("팔로우 취소");
                                        follow_text.setTextColor(Color.parseColor("#ffffff"));
                                        follow_button.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);
                                        follow_button.setClickable(true);

                                    } else {

                                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {

                                    e.printStackTrace();

                                }
                            }
                        });



                    } else {

                        follow_button.setClickable(true);
                        TastyToast.makeText(context, "팔로우를 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        e.printStackTrace();

                    }
                }
            });


        };


    }


}
