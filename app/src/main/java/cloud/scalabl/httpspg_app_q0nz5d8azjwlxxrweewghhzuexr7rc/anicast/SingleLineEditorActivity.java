package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SingleLineEditorActivity extends AppCompatActivity {


    private static String type;
    private static EditText edit_line;
    private static ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_line_editor);

        Intent intent = getIntent();

        type = intent.getExtras().getString("type");

        edit_line = (EditText) findViewById(R.id.edit_line);
        Button save_button = (Button) findViewById(R.id.save_button);

        currentUser = ParseUser.getCurrentUser();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputString = edit_line.getText().toString();
                Log.d("inputString", String.valueOf(inputString.length()));


                if(inputString.length() == 0){

                    Toast.makeText(SingleLineEditorActivity.this, "error3", Toast.LENGTH_SHORT).show();


                } else {

                    if(type.equals("username")){

                        currentUser.put("name", inputString);
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    finish();

                                } else {

                                    Toast.makeText(SingleLineEditorActivity.this, "error5", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        });

                    } else {


                        Toast.makeText(SingleLineEditorActivity.this, "error4", Toast.LENGTH_SHORT).show();

                    }



                }

            }
        });

    }
}
