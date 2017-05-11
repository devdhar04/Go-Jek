package com.android.gojek;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.gojek.model.Contact;
import com.android.gojek.view.AddContactActivity;
import com.android.gojek.view.ContactDetailActivity;
import com.android.gojek.view.ContactListActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by dev on 08/05/17.
 */

public class ContactDetailTest {

    private static final String EXTRA_URL = "DETAIL_URL";
    private static final String EXTRA_FIRST_NAME = "CONTACT_FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "CONTACT_LAST_NAME";
    private static final String EXTRA_IMAGE = "IMAGE_URL";
    private static final String EXTRA_FAVORITE = "FAVORITE";


    @Rule
    public ActivityTestRule<ContactDetailActivity> rule =
            new ActivityTestRule<ContactDetailActivity>(ContactDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent intent = new Intent(targetContext, ContactDetailActivity.class);

                    Contact contact = new Contact();
                    contact.setId(30);
                    contact.setFirstName("ABC ");
                    contact.setLastName("cuz" );
                    contact.setEmail("test@yahoo.com");
                    contact.setPhoneNumber("1234567890");
                    contact.setContactDetailUrl("http://gojek-contacts-app.herokuapp.com/contacts/30.json");
                    intent.putExtra(EXTRA_URL, contact.contactDetailUrl);
                    intent.putExtra(EXTRA_FIRST_NAME, contact.firstName);
                    intent.putExtra(EXTRA_LAST_NAME, contact.lastName);
                    intent.putExtra(EXTRA_IMAGE, contact.contactDetailUrl);
                    intent.putExtra(EXTRA_FAVORITE, contact.isFavorite);
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


    @Test
    public void testMenuItemDelete() throws Exception {
        ContactDetailActivity activity = rule.getActivity();
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.action_delete, 0);
    }



    @Test
    public void testMenuItemEdit() throws Exception {
        ContactDetailActivity activity = rule.getActivity();
        onView(withId(R.id.action_edit))
                .perform(click());


        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(AddContactActivity.class.getName(), null, true);

        am.waitForActivityWithTimeout(1000);
        assertEquals(0, am.getHits());
    }
}
