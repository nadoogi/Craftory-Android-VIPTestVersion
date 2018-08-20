package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ChargeWithdrawFragment extends Fragment {


    private ParseUser currentUser;
    private TextView current_point;
    private FunctionBase functionBase;
    private EditText withdraw_input;

    private TextView left_amount;
    private int currentPoint;
    private TextView withdraw_money;

    private TextView message;

    private BootstrapDropDown bank_select;
    private String bankSelected;

    private EditText account_number;
    private EditText account_owner;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        return inflater.inflate(R.layout.fragment_charge_withdraw, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        current_point = (TextView) view.findViewById(R.id.current_point);
        withdraw_input = (EditText) view.findViewById(R.id.withdraw_input);
        left_amount = (TextView) view.findViewById(R.id.left_amount);
        withdraw_money = (TextView) view.findViewById(R.id.withdraw_money);

        LinearLayout button_3000 = (LinearLayout) view.findViewById(R.id.button_3000);
        LinearLayout button_5000 = (LinearLayout) view.findViewById(R.id.button_5000);
        LinearLayout button_10000 = (LinearLayout) view.findViewById(R.id.button_10000);
        LinearLayout button_50000 = (LinearLayout) view.findViewById(R.id.button_50000);

        account_number = (EditText) view.findViewById(R.id.account_number);
        account_owner = (EditText) view.findViewById(R.id.account_owner);

        bank_select = (BootstrapDropDown) view.findViewById(R.id.bank_select);

        LinearLayout save_button = (LinearLayout) view.findViewById(R.id.save_button);

        button_3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputValue = withdraw_input.getText().toString();
                int inputInt = Integer.parseInt(inputValue);

                int addInputInt = inputInt + 3000;

                int withdrawAmount = addInputInt * 10;

                if(currentPoint < addInputInt){

                    message.setText("보유한 BOX보다 더 많은 BOX가 입력되었습니다. 다시 입력해주세요");
                    TastyToast.makeText(getActivity(), "보유한 BOX를 초과했습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    withdraw_input.setText(String.valueOf(addInputInt));

                    message.setText("");
                    int leftAmount = currentPoint - addInputInt;
                    left_amount.setText(functionBase.makeComma(leftAmount));
                    withdraw_money.setText(functionBase.makeComma(withdrawAmount) + "원");

                }



            }
        });

        button_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputValue = withdraw_input.getText().toString();
                int inputInt = Integer.parseInt(inputValue);

                int addInputInt = inputInt + 5000;

                int withdrawAmount = addInputInt * 10;

                if(currentPoint < addInputInt){

                    message.setText("보유한 BOX보다 더 많은 BOX가 입력되었습니다. 다시 입력해주세요");
                    TastyToast.makeText(getActivity(), "보유한 BOX를 초과했습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    withdraw_input.setText(String.valueOf(addInputInt));

                    message.setText("");
                    int leftAmount = currentPoint - addInputInt;
                    left_amount.setText(functionBase.makeComma(leftAmount));
                    withdraw_money.setText(functionBase.makeComma(withdrawAmount) + "원");

                }

            }
        });

        button_10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputValue = withdraw_input.getText().toString();
                int inputInt = Integer.parseInt(inputValue);

                int addInputInt = inputInt + 10000;

                int withdrawAmount = addInputInt * 10;

                if(currentPoint < addInputInt){

                    message.setText("보유한 BOX보다 더 많은 BOX가 입력되었습니다. 다시 입력해주세요");
                    TastyToast.makeText(getActivity(), "보유한 BOX를 초과했습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    withdraw_input.setText(String.valueOf(addInputInt));

                    message.setText("");
                    int leftAmount = currentPoint - addInputInt;
                    left_amount.setText(functionBase.makeComma(leftAmount));
                    withdraw_money.setText(functionBase.makeComma(withdrawAmount) + "원");

                }

            }
        });

        button_50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputValue = withdraw_input.getText().toString();
                int inputInt = Integer.parseInt(inputValue);

                int addInputInt = inputInt + 50000;

                int withdrawAmount = addInputInt * 10;

                if(currentPoint < addInputInt){

                    message.setText("보유한 BOX보다 더 많은 BOX가 입력되었습니다. 다시 입력해주세요");
                    TastyToast.makeText(getActivity(), "보유한 BOX를 초과했습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    withdraw_input.setText(String.valueOf(addInputInt));

                    message.setText("");
                    int leftAmount = currentPoint - addInputInt;
                    left_amount.setText(functionBase.makeComma(leftAmount));
                    withdraw_money.setText(functionBase.makeComma(withdrawAmount) + "원");

                }


            }
        });


        bank_select.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {

                switch (id){

                    case 0:

                        bankSelected = "신한";
                        bank_select.setText("신한");

                        break;

                    case 1:

                        bankSelected = "농협";
                        bank_select.setText("농협");

                        break;

                    case 2:

                        bankSelected = "기업";
                        bank_select.setText("기업");

                        break;

                    case 3:

                        bankSelected = "국민";
                        bank_select.setText("국민");

                        break;

                    case 4:

                        bankSelected = "우리";
                        bank_select.setText("우리");

                        break;

                    case 5:

                        bankSelected = "KEB하나";
                        bank_select.setText("KEB하나");

                        break;

                    case 6:

                        bankSelected = "SC제일";
                        bank_select.setText("SC제일");

                        break;

                    case 7:

                        bankSelected = "경남";
                        bank_select.setText("경남");

                        break;

                    case 8:

                        bankSelected = "광주";
                        bank_select.setText("광주");

                        break;


                    case 9:

                        bankSelected = "대구";
                        bank_select.setText("대구");

                        break;

                    case 10:

                        bankSelected = "부산";
                        bank_select.setText("부산");

                        break;

                    case 11:

                        bankSelected = "산업";
                        bank_select.setText("산업");

                        break;

                    case 12:

                        bankSelected = "상호저축";
                        bank_select.setText("상호저축");

                        break;

                    case 13:

                        bankSelected = "새마을";
                        bank_select.setText("새마을");

                        break;

                    case 14:

                        bankSelected = "수협";
                        bank_select.setText("수협");

                        break;

                    case 15:

                        bankSelected = "신협";
                        bank_select.setText("신협");

                        break;

                    case 16:

                        bankSelected = "우체국";
                        bank_select.setText("우체국");

                        break;

                    case 17:

                        bankSelected = "전북";
                        bank_select.setText("전북");

                        break;

                    case 18:

                        bankSelected = "제주";
                        bank_select.setText("제주");

                        break;

                    case 19:

                        bankSelected = "씨티";
                        bank_select.setText("씨티");

                        break;


                }

            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accountNumberString = account_number.getText().toString();
                String accountOwnerString = account_owner.getText().toString();
                String inputValue = withdraw_input.getText().toString();
                int inputInt = Integer.parseInt(inputValue);

                if(bankSelected == null){

                    TastyToast.makeText(getActivity(), "은행을 선택하세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if( accountNumberString == null || accountNumberString.length() == 0){

                    TastyToast.makeText(getActivity(), "계좌번호를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if( accountOwnerString == null || accountOwnerString.length() == 0){

                    TastyToast.makeText(getActivity(), "예금주를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if( inputValue == null || inputValue.length() == 0 || inputInt == 0){

                    TastyToast.makeText(getActivity(), "환급 신청 BOX를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    ParseObject withdrawRequestOb = new ParseObject("WithdrawRequest");
                    withdrawRequestOb.put("user", currentUser);
                    withdrawRequestOb.put("account_number", accountNumberString);
                    withdrawRequestOb.put("account_owner", accountOwnerString);
                    withdrawRequestOb.put("request_amount", inputInt);
                    withdrawRequestOb.put("bank", bankSelected);
                    withdrawRequestOb.put("status", true);
                    withdrawRequestOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                TastyToast.makeText(getActivity(), "요청이 완료되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                getActivity().finish();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }


            }
        });

        message = (TextView) view.findViewById(R.id.message);

        if(currentUser != null){

            currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject pointOb, ParseException e) {

                    if(e==null){

                        currentPoint = pointOb.getInt("current_point");
                        current_point.setText( functionBase.makeComma( pointOb.getInt("current_point") ) + " BOX");
                        left_amount.setText( functionBase.makeComma(pointOb.getInt("current_point") ) + " BOX" );

                    } else {

                        e.printStackTrace();
                    }


                }
            });

            withdraw_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {



                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String inputValue = withdraw_input.getText().toString();
                    int inputInt = Integer.parseInt(inputValue);

                    int withdrawAmount = inputInt * 10;

                    if(currentPoint < inputInt){

                        message.setText("보유한 BOX보다 더 많은 BOX가 입력되었습니다. 다시 입력해주세요");

                    } else {

                        message.setText("");
                        int leftAmount = currentPoint - inputInt;
                        left_amount.setText(functionBase.makeComma(leftAmount));
                        withdraw_money.setText(functionBase.makeComma(withdrawAmount) + "원");

                    }



                }

                @Override
                public void afterTextChanged(Editable s) {



                }
            });




        }



    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
