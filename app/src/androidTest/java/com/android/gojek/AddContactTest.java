package com.android.gojek;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.gojek.model.Contact;
import com.android.gojek.view.AddContactActivity;
import com.android.gojek.view.ContactDetailActivity;
import com.android.gojek.view.ContactListActivity;
import com.android.gojek.view.adapter.ContactListAdapter;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static android.support.test.espresso.intent.Intents.intending;
/**
 * Created by dev on 08/05/17.
 */

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddContactTest {
    private static final String EXTRA_SCREEN_NAME = "CONTACT_ADD";
    @Rule
    public ActivityTestRule<AddContactActivity> rule  = new  ActivityTestRule<AddContactActivity>(AddContactActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent intent = new Intent(targetContext, AddContactActivity.class);

            intent.putExtra(EXTRA_SCREEN_NAME, EXTRA_SCREEN_NAME);
            return intent;
        }
    };
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.gojek", appContext.getPackageName());
    }


    @Test
    public void ensureViewIsPresent() throws Exception {
        AddContactActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.imageLayout);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RelativeLayout.class));



          viewById = activity.findViewById(R.id.contactImage);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(ImageView.class));

          viewById = activity.findViewById(R.id.captureImage);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(ImageView.class));


    }

    @Test
    public void ensureNameViewPresent() throws Exception {
        AddContactActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.nameLayout);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(LinearLayout.class));

        viewById = activity.findViewById(R.id.nameField);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(EditText.class));

        viewById = activity.findViewById(R.id.lastNameField);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(EditText.class));
    }

    @Test
    public void ensureMobilePresent() throws Exception {
        AddContactActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.phoneLayout);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(LinearLayout.class));

        viewById = activity.findViewById(R.id.phoneField);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(EditText.class));


    }

    @Test
    public void ensureEmailPresent() throws Exception {
        AddContactActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.emailLayout);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(LinearLayout.class));

        viewById = activity.findViewById(R.id.emailField);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(EditText.class));


    }
   @Test
    public void saveContactTest()
    { AddContactActivity activity = rule.getActivity();
        String first_name = "First ";
        String last_name = "Last" ;
        String emailAddress="test@yahoo.com";
        String mobile = "1234567890";

        onView(withId(R.id.nameField)).perform(typeText(first_name), closeSoftKeyboard());

        //find the lastname edit text and type in the last name
        onView(withId(R.id.lastNameField)).perform(typeText(last_name), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.emailField)).perform(typeText(emailAddress), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.phoneField)).perform(typeText(mobile), closeSoftKeyboard());

        onView(withId(R.id.action_save))
                .perform(click());



    }


}
