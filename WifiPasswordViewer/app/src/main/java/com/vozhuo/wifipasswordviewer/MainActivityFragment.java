package com.vozhuo.wifipasswordviewer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView textView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String link = "项目源码";
        SpannableString text = new SpannableString(link);
        NoUnderLineSpan noUnderLineSpan = new NoUnderLineSpan("https://github.com/vozhuo/Android-Wifi-Password-Viewer");
        text.setSpan(noUnderLineSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView = (TextView) rootView.findViewById(R.id.linkView);
        textView.setText(text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment, new WifiFragment())
                        .commit();
            }
        });
        return rootView;
    }
    //超链接无下划线
    public static class NoUnderLineSpan extends URLSpan {
        public NoUnderLineSpan(String url) {
            super(url);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}