package com.android.gojek;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.gojek.view.ContactDetailActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by dev on 08/05/17.
 */

public class ContactDetailTest {

    @Rule
    public ActivityTestRule<ContactDetailActivity> rule  = new  ActivityTestRule<>(ContactDetailActivity.class);
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.gojek", appContext.getPackageName());
    }


    @Test
    public void ensureViewIsPresent() throws Exception {
        ContactDetailActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.movie_card);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(CardView.class));



        viewById = activity.findViewById(R.id.mobileNumber);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));

        viewById = activity.findViewById(R.id.emailLable);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));

        viewById = activity.findViewById(R.id.progress_contact);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(ProgressBar.class));
    }
}
