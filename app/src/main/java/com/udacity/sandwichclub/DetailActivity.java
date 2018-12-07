package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    /***
     * Flags
     */
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String SANDWICH_OBJECT = "SandwichObject";

    /***
     * Vars
     */
    private Sandwich sandwich = null;

    /***
     * Views
     */
    @BindView(R.id.tv_sandwich_name) TextView mNameTextView;
    @BindView(R.id.tv_place_of_origin) TextView mPlaceOfOriginTextView;
    @BindView(R.id.tv_also_known_as) TextView mAlsoKnownAsTextView;
    @BindView(R.id.tv_ingredients) TextView mIngredientsTextView;
    @BindView(R.id.tv_description) TextView mDescriptionTextView;
    @BindView(R.id.iv_sandwich_image) ImageView mSandwichImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SANDWICH_OBJECT)) {
            sandwich = (Sandwich) savedInstanceState.getSerializable(SANDWICH_OBJECT);
            populateUI();
            return;
        }

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (sandwich != null) {
            outState.putSerializable(SANDWICH_OBJECT, sandwich);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        setTitle(sandwich.getMainName());
        mNameTextView.setText(sandwich.getMainName());
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin().length() > 0 ? sandwich.getPlaceOfOrigin() : getString(R.string.unknown));
        for (String name : sandwich.getAlsoKnownAs()) {
            mAlsoKnownAsTextView.append(name + "\n");
        }

        for (String ingredient : sandwich.getIngredients()) {
            mIngredientsTextView.append(ingredient + "\n");
        }
        mDescriptionTextView.setText(sandwich.getDescription());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichImageView);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
