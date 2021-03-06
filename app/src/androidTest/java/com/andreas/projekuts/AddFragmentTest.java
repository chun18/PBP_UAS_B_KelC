package com.andreas.projekuts;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addFragmentTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_username),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_username),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_username),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("32jovan@gmail.com"), closeSoftKeyboard());



        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_password_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("jovan1234"), closeSoftKeyboard());



        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_add), withText("Tambah Ujian"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        3),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_update), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.input_tanggal),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_tanggal_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("18-052020"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_tanggal), withText("18-052020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_tanggal_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.input_tanggal), withText("18-052020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_tanggal_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.input_tanggal), withText("18-052020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_tanggal_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("18-05-2020"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.input_tanggal), withText("18-05-2020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_tanggal_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.input_mapel),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_mapel_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("Sains"), closeSoftKeyboard());



        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_update), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
