package com.github.jackkell.mimicryproject.listadpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

        TextWatcher lastEditTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    addNewItem();
                }
            }
        };

        EditText usernameEditText = (EditText) convertView.findViewById(R.id.etTwitterUserName);
        usernameEditText.setText(twitterUserNames.get(position));

        if(position == twitterUserNames.size() - 1) {
            //usernameEditText.addTextChangedListener(lastEditTextWatcher);
            usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_NEXT) {
                        addNewItem();
                    }
                    return false;
                }
            });
        }

        if (position != 0 && position != twitterUserNames.size() - 1) {
            usernameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty()) {
                        removeItem(position);
                    }
                }
            });
        }

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setItem(position, s.toString());
            }
        });
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
