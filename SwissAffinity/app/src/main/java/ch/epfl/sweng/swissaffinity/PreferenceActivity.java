package ch.epfl.sweng.swissaffinity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;

public class PreferenceActivity extends AppCompatActivity {

    private final Set<String> mMyLocations = new HashSet<>();
    private final Set<String> mAllLocations = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        String[] locations = getResources().getStringArray(R.array.location_list);
        mAllLocations.addAll(Arrays.asList(locations));
        Set<String> savedLocations = getSavedLocations();
        if (savedLocations == null) {
            mMyLocations.addAll(mAllLocations);
        } else {
            mMyLocations.addAll(savedLocations);
        }
        saveLocations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLocations();
    }

    private Set<String> getSavedLocations() {
        return MainActivity.getPreferences().getStringSet(
                LOCATIONS_INTEREST.get(),
                null);
    }

    private void updateUI() {
        LinearLayout group = (LinearLayout) findViewById(R.id.preferenceGroup);
        for (final String location : mAllLocations) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setTag(location);
            checkBox.setText(location);
            checkBox.setOnCheckedChangeListener(
                    new CheckBox.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                mMyLocations.add(location);
                            } else {
                                mMyLocations.remove(location);
                            }
                        }
                    });
            group.addView(checkBox);
        }

        for (final String location : mMyLocations) {
            CheckBox checkBox = ((CheckBox) group.findViewWithTag(location));
            if (checkBox != null) {
                checkBox.setChecked(true);
            }
        }
    }

    private void saveLocations() {
        MainActivity.getPreferences()
                    .edit()
                    .putStringSet(LOCATIONS_INTEREST.get(), mMyLocations)
                    .apply();
    }
}
