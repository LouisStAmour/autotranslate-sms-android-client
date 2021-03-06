package com.moez.QKSMS.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.SwitchPreference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moez.QKSMS.R;

/**
 * Regular android preferences don't have basic functionality when you manually add them to views
 * other than preferencegroups, this just cleans up some boilerplate code to set ours up
 */
public class QKSwitchPreference extends SwitchPreference {

    private SharedPreferences mPrefs;
    private OnPreferenceClickListener mOnPreferenceClickListener;
    private boolean mDefaultValue;
    private QKSwitch mCheckBox;

    public QKSwitchPreference(Context context, OnPreferenceClickListener onPreferenceClickListener,
                              String key, SharedPreferences prefs, boolean defaultValue, int title, int summary) {
        super(context);
        mPrefs = prefs;
        mOnPreferenceClickListener = onPreferenceClickListener;

        setKey(key);
        setEnabled(true);
        mDefaultValue = prefs.getBoolean(key, defaultValue);
        setLayoutResource(R.layout.list_item_preference);
        setWidgetLayoutResource(R.layout.view_switch);
        if (title != 0) setTitle(title);
        if (summary != 0) setSummary(summary);
    }

    public View getView() {
        return getView(null, null);
    }

    @Override
    public View getView(View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        mCheckBox = (QKSwitch) view.findViewById(android.R.id.checkbox);
        mCheckBox.setChecked(mDefaultValue);

        view.setOnClickListener(v -> {
            mPrefs.edit().putBoolean(getKey(), !mCheckBox.isChecked()).apply();
            mCheckBox.setChecked(!mCheckBox.isChecked());
            if (mOnPreferenceClickListener != null) {
                mOnPreferenceClickListener.onPreferenceClick(QKSwitchPreference.this);
            }
        });


        return view;
    }

    @Override
    public boolean isChecked() {
        return mCheckBox == null ? super.isChecked() : mCheckBox.isChecked();
    }
}
