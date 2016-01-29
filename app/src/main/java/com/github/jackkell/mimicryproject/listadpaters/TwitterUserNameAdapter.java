package com.github.jackkell.mimicryproject.listadpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TwitterUserNameAdapter extends BaseAdapter{

    private List<String> twitterUserNames;

    public TwitterUserNameAdapter(){
        this.twitterUserNames = new ArrayList<>();
        twitterUserNames.add("");
    }

    @Override
    public int getCount() {
        return twitterUserNames.size();
    }

    @Override
    public Object getItem(int position) {
        return twitterUserNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.twitter_user_input_field, parent, false);
        }

        final EditText usernameEditText = (EditText) convertView.findViewById(R.id.etTwitterUserName);
        usernameEditText.setText(twitterUserNames.get(position));


        TextWatcher lastEditTextBehavior = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    twitterUserNames.set(position, s.toString());
                    twitterUserNames.add("");
                    notifyDataSetChanged();
                }
            }
        };

        TextWatcher standardEditTextBehavior = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    twitterUserNames.set(position, s.toString());
                    removeItem(position);
                    notifyDataSetChanged();
                }
            }
        };

        if (position == twitterUserNames.size() - 1) {
            usernameEditText.addTextChangedListener(lastEditTextBehavior);
        } else {
            usernameEditText.addTextChangedListener(standardEditTextBehavior);
        }

//        /*
//        If the edit text is last in the list and the user edits it then another edit
//        text will be added to the end of the list
//         */
//        if(position == twitterUserNames.size() - 1) {
//            usernameEditText.addTextChangedListener(lastEditTextWatcher);
//            usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                        addNewItem();
//                    }
//                    return false;
//                }
//            });
//        }
//
//        /*
//        If the given edit text is not the last in the list then it should be deleted if it is empty
//         */
//        if (position != twitterUserNames.size() - 1) {
//            usernameEditText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (s.toString().isEmpty()) {
//                        removeItem(position);
//                    }
//                }
//            });
//        }
//
//        /*
//        Save the string of the edittext within the list of strings when the user leaves focus
//         */
//
//        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String debugString = "";
//                for (String username : twitterUserNames) {
//                    debugString += "\"" + username + "\" ";
//                }
//                Log.d("TAG", debugString);
//                EditText currentEditText = (EditText) v;
//                if (!hasFocus) {
//                    setItem(position, currentEditText.getText().toString());
//                }
//            }
//        });

        String debugString = "";
        for (String username : twitterUserNames) {
            debugString += "\"" + username + "\" ";
        }
        Log.d("TAG", debugString);

        return convertView;
    }

    public void addNewItem() {
        twitterUserNames.add("");
        notifyDataSetChanged();
    }

    public void setItem(int index, String value) {
        twitterUserNames.set(index, value);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        twitterUserNames.remove(index);
        notifyDataSetChanged();
    }
}
