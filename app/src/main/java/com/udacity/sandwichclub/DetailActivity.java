package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView imageIv;
    @BindView(R.id.main_name_tv)
    TextView mainNameTv;
    @BindView(R.id.origin_tv)
    TextView originTv;
    @BindView(R.id.origin_label_tv)
    TextView originLabelTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @BindView(R.id.description_label_tv)
    TextView descriptionLabelTv;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;
    @BindView(R.id.ingredients_label_tv)
    TextView ingredientsLabelTv;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownTv;
    @BindView(R.id.also_known_label_tv)
    TextView alsoKnownLabelTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            closeOnError();
        } else {
            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(imageIv);

            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mainNameTv.setText(sandwich.getMainName());

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            originTv.setVisibility(View.INVISIBLE);
            originLabelTv.setVisibility(View.INVISIBLE);
        }else{
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()){
            descriptionLabelTv.setVisibility(View.INVISIBLE);
            descriptionTv.setVisibility(View.INVISIBLE);
        }else{
            descriptionTv.setText(sandwich.getDescription());
        }

        if (sandwich.getAlsoKnownAs().size() == 0) {
            alsoKnownTv.setVisibility(View.INVISIBLE);
            alsoKnownLabelTv.setVisibility(View.INVISIBLE);
        } else {
            for (int j = 0; j < sandwich.getAlsoKnownAs().size(); j++) {
                alsoKnownTv.append(sandwich.getAlsoKnownAs().get(j) + "\n");
            }
        }

        if (sandwich.getIngredients().size() == 0) {
            ingredientsTv.setVisibility(View.INVISIBLE);
            ingredientsLabelTv.setVisibility(View.INVISIBLE);
        }else{
            for (int j = 0; j < sandwich.getIngredients().size(); j++) {
                ingredientsTv.append(sandwich.getIngredients().get(j) + "\n");
            }
        }

    }
}
