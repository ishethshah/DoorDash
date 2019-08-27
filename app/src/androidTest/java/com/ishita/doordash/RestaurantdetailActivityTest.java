package com.ishita.doordash;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.ishita.doordash.view.RestaurantDetailActivity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RestaurantdetailActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<RestaurantDetailActivity> mRule = new ActivityTestRule<>(RestaurantDetailActivity.class, false, false);
    private RestaurantDetailActivity mActivity;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        launchActivity();
    }

    private void launchActivity() {
        Intent intent = new Intent();
        mRule.launchActivity(intent);
        mActivity = mRule.getActivity();
        assertNotNull(mActivity);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testUIIconsPresent() {
        ImageView detailBackropView = mActivity.findViewById(R.id.iv_backdrop);
        assertNotNull(detailBackropView);

        TextView restaurantTitle = mActivity.findViewById(R.id.tv_restaurant_title);
        assertNotNull(restaurantTitle);

        TextView restTags = mActivity.findViewById(R.id.tv_rest_tags);
        assertNotNull(restTags);

        TextView restAdress = mActivity.findViewById(R.id.tv_rest_address);
        assertNotNull(restAdress);

    }
}
