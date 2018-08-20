package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.woxthebox.draglistview.DragListView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ImageUploadSelectorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.UploadImageAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ImageArrayObject;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;


/**
 * Created by ssamkyu on 2017. 5. 11..
 */

public class TestAcitivity extends AppCompatActivity {

    Button button;

    RequestManager requestManager;
    TextView file_status_text;
    TextView except_file_text;

    private ArrayList<Image> images;
    //private ArrayList<ImageArrayObject> savedImages;
    private ArrayList<File> fileList;
    private ArrayList<String> exceptList;
    private ArrayList<Pair<Long,String>> previewImages;
    private ArrayList<Pair<Long,String>> uploadedImages;
    private String imagePath;
    private String finalImage;
    //private int total_image;
    private long total_data_amount;
    private long progress_amount;
    private int uploaded_image_count;



    private FunctionBase functionBase;

    private DragListView dragListView;
    private UploadImageAdapter uploadImageAdapter;

    private Button tag_test_button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        /*
        ImageView gif_test = (ImageView) findViewById(R.id.gif_test);

        String gifUrl = "http://res.cloudinary.com/dqn5e8b6u/image/upload/c_scale,w_500/v1491493378/le7c5y1rohzql4qjhesa.gif";
        //Glide.with(getApplicationContext()).load(gifUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gif_test);
        */

        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());
        file_status_text = (TextView) findViewById(R.id.file_status_text);
        except_file_text = (TextView) findViewById(R.id.except_file_text);

        tag_test_button = (Button) findViewById(R.id.tag_test_button);
        tag_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagInputActivity.class);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 5); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);


            }
        });

        dragListView = (DragListView) findViewById(R.id.list_view);
        dragListView.setDragListListener(new DragListView.DragListListener(){

            @Override
            public void onItemDragStarted(int position) {

                Log.d("startDrag", String.valueOf(position));

            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {

                Log.d("dragging", String.valueOf(itemPosition));

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {

                Log.d("dragFromEnd", String.valueOf(fromPosition));
                Log.d("dragtoEnd", String.valueOf(toPosition));
                Log.d("ItemList", uploadImageAdapter.getItemList().toString());

            }
        });

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);

        dragListView.setLayoutManager(layoutManager);

        requestManager = Glide.with(getApplicationContext());

        dragListView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }


        });





    }

    public class UploadThread implements Runnable {

        private File file;
        private int index;
        private int total_image_count;
        private File tempFile;
        private long preData;

        private UploadThread(File file, int index, int total_image_count){

            this.file = file;
            this.index = index;
            this.total_image_count = total_image_count;
            this.preData = 0;

        }

        @Override
        public void run() {

            //your api call to upload file using fileLocation
            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    //file_status_text.setText("파일 업로드 시작");

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {


                    long plusData = bytes - preData;
                    progress_amount += plusData;
                    preData = bytes;

                    //file_status_text.setText( String.valueOf(progress_amount) + "/" + String.valueOf( total_data_amount ));

                    //float progress = functionBase.makeProgress(Float.parseFloat(String.valueOf(bytes)), Float.parseFloat(String.valueOf(totalBytes)));
                    //file_status_text.setText("업로드 중 : " + String.valueOf(bytes) + "/" + String.valueOf(totalBytes) + " || "  + String.valueOf(progress * 100) + "%");

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {


                    //progress_text.setVisibility(View.GONE);

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    Pair<Long, String> uploadImage = new Pair<Long, String>(Long.valueOf(index), uploadTarget);
                    uploadedImages.add(uploadImage);

                    Log.d("file", file.getPath().toString());
                    Log.d("uploadedImages", uploadedImages.toString());

                    finalImage = uploadTarget;

                    //file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    uploaded_image_count += 1;

                    //ImageArrayObject savedImageOb = new ImageArrayObject(index, finalImage);
                    //savedImages.add(savedImageOb);



                    file_status_text.setText( String.valueOf(uploaded_image_count) + " / " +  String.valueOf( total_image_count ));
                    Log.d("resultImage", finalUploadTaget);

                    if(uploaded_image_count == total_image_count){

                        uploadImageAdapter = new UploadImageAdapter(uploadedImages, R.layout.list_item_mini_preview, R.id.item_layout, true, requestManager, functionBase, false);
                        //uploadImageAdapter.setFileOpenClickListener(this);
                        //uploadImageAdapter.setImageInfoClickListener(this);
                        dragListView.setAdapter(uploadImageAdapter, true);
                        dragListView.setDragEnabled(true);

                    }

                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                    //file_status_text.setText("업로드 실패");
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    //file_status_text.setText("업로드 실패");
                }
            };

            // 한글이 포함된 문자열
            Log.d("korean", "true");

            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/craftory";

            tempFile = new File(sdPath);
            tempFile.mkdirs();

            tempFile = new File(sdPath, "temp_" + String.valueOf(index) +  ".png");

            File gifFile = file;

            try {

                functionBase.copy(gifFile, tempFile);
                MediaManager.get().upload(sdPath+"/temp_"+ String.valueOf(index) +".png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        file_status_text.setText("파일 선택 완료 대기 중..");

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images

            //total_image = 0;
            uploaded_image_count = 0;
            uploadedImages = new ArrayList<>();

            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            HashMap<String, Object> fileData = makeFileFromLocalFile(images);

            fileList = (ArrayList<File>) fileData.get("file");
            exceptList = (ArrayList<String>) fileData.get("except");
            total_data_amount = (Integer) fileData.get("fileSize");
            previewImages = (ArrayList<Pair<Long,String>>) fileData.get("preview");

            uploadImageAdapter = new UploadImageAdapter(previewImages, R.layout.list_item_mini_preview, R.id.item_layout, true, requestManager, functionBase, true);
            //uploadImageAdapter.setFileOpenClickListener(this);
            //uploadImageAdapter.setImageInfoClickListener(this);
            dragListView.setAdapter(uploadImageAdapter, true);
            dragListView.setDragEnabled(false);


            //file_status_text.setText( String.valueOf(0) + "/" + String.valueOf( total_data_amount ));

            if(exceptList.size() > 0){

                String exceptFileList = "";

                for(String fileName:exceptList){

                    exceptFileList += fileName + " ";

                }

                except_file_text.setText(exceptFileList);

            }

            Log.d("fileList", fileList.toString());

            file_status_text.setText( String.valueOf(uploaded_image_count) + " / " +  String.valueOf( fileList.size() ));

            ExecutorService executor = Executors.newFixedThreadPool(fileList.size());

            for (int i = 0; fileList.size() > i ; i++) {

                Runnable worker = new UploadThread(fileList.get(i), i, fileList.size());

                executor.execute(worker);
            }
            executor.shutdown();

            while (!executor.isTerminated()) { }

            System.out.println("Finished uploading");


        } else if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){




        } else {

            file_status_text.setText("파일 선택 안됨");

        }



    }


    private HashMap<String, Object> makeFileFromLocalFile(ArrayList<Image> images){

        ArrayList<File> resultList = new ArrayList<>();
        ArrayList<String> exceptList = new ArrayList<>();
        ArrayList<Pair<Long, String>> previewList = new ArrayList<>();
        int total_file_size = 0;
        Long index = Long.valueOf(0);

        for(Image image:images){

            File file = new File(image.path);
            int fileSize = fileSizeCalculator(file);

            Log.d("fileSize", String.valueOf(fileSize));

            if(fileSize > 50000){

                String fileName = image.name;
                exceptList.add(fileName);

            } else {

                total_file_size += fileSize;

                resultList.add(file);

                Log.d("imagePath", image.path);
                Pair<Long, String> previewData = new Pair<>(index, image.path);
                previewList.add(previewData);
                index += 1;
            }

        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("file", resultList);
        hashMap.put("except", exceptList);
        hashMap.put("fileSize", total_file_size);
        hashMap.put("preview", previewList);


        return hashMap;

    }

    private int fileSizeCalculator(File file){

        ///1024
        int file_size = Integer.parseInt(String.valueOf(file.length()));

        return file_size;
    }

}
