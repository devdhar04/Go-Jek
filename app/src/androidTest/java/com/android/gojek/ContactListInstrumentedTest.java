package com.android.gojek;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.gojek.view.ContactListActivity;
import com.android.gojek.view.adapter.ContactListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ContactListInstrumentedTest {
    @Rule
    public ActivityTestRule<ContactListActivity> rule  = new  ActivityTestRule<>(ContactListActivity.class);
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.gojek", appContext.getPackageName());
    }


    @Test
    public void ensureListViewIsPresent() throws Exception {
        ContactListActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.list_contact);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;
        ContactListAdapter adapter = (ContactListAdapter) listView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));
        assertThat(adapter.getItemCount(), greaterThan(5));

    }

    @Test
    public void ensureFavButtonIsPresent() throws Exception {
        ContactListActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.addContact);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(FloatingActionButton.class));
        onView(withId(R.id.addContact)).perform(click());
        onView(withId(R.id.contactImage)).check(matches(allOf(isDescendantOfA(withId(R.id.imageLayout)), isDisplayed())));
    }
    @Test
    public void ensureAddContactClickButton() throws Exception {

        onView(withId(R.id.addContact)).perform(click());
        onView(withId(R.id.contactImage)).check(matches(allOf(isDescendantOfA(withId(R.id.imageLayout)), isDisplayed())));
    }
}
