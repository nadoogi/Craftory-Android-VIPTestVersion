package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

public class PatronDescriptionEditorActivity extends AppCompatActivity {

    private static EditText editor;
    private static LinearLayout save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_description_editor);

        Intent intent = getIntent();

        String objectId = intent.getExtras().getString("objectId");

        editor = (EditText) findViewById(R.id.editor);
        save_button = (LinearLayout) findViewById(R.id.save_button);

        Log.d("PatronDescription", objectId);

        ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                if(e==null){

                    if(postOb.get("patron_detail_info") != null){

                        editor.setText(postOb.getString("patron_detail_info"));

                    }

                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            save_button.setClickable(false);

                            String saveString = editor.getText().toString();

                            Log.d("saveString", saveString);
                            Log.d("postOb", postOb.getObjectId());

                            if(saveString.length() == 0){

                                editor.setError("내용을 입력하세요");

                            } else {

                                postOb.put("patron_detail_info", saveString);
                                postOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            finish();

                                        } else {

                                            e.printStackTrace();
                                        }

                                    }
                                });


                            }


                        }
                    });

                } else {


                    e.printStackTrace();
                }


            }

        });



    }

    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        builder.title("확인");
        builder.content("이 페이지에서 나가시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                finish();

            }
        });
        builder.show();

    }

}
