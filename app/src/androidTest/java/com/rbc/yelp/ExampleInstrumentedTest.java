package com.rbc.yelp;

import android.view.View;

import com.rbc.yelp.activity.MainActivity;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkSearchBar() {
        onView(withId(R.id.et_location)).perform(clearText());
        onView(withId(R.id.et_location)).perform(typeText("New York"));
        onView(withId(R.id.et_search_term)).perform(typeText("Sushi"));
        onView(withId(R.id.button)).check(matches(isClickable()));
    }

    @Test
    public void checkBusinessList() {
        waitForView(withIndex(withId(R.id.tv_category), 0));
        onView(withIndex(withId(R.id.tv_business_name), 0)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.iv_imageView), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.rb_ratingBar), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.tv_address), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.tv_business_category), 0)).check(matches(not(isDisplayed())));

        onView(withIndex(withId(R.id.tv_business_name), 0)).perform(click());

        onView(withIndex(withId(R.id.iv_imageView), 0)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.rb_ratingBar), 0)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.tv_address), 0)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.tv_business_category), 0)).check(matches(isDisplayed()));

        onView(withIndex(withId(R.id.tv_business_name), 0)).perform(click());

        onView(withIndex(withId(R.id.iv_imageView), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.rb_ratingBar), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.tv_address), 0)).check(matches(not(isDisplayed())));
        onView(withIndex(withId(R.id.tv_business_category), 0)).check(matches(not(isDisplayed())));

    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    private void waitForView(@NotNull Matcher<View> viewMatcher){
        final int INTERVAL = 1500;
        final int TIME_OUT = 30000;
        int totalWaitingTime = 0;
        while (totalWaitingTime < TIME_OUT) {
            try {
                onView(viewMatcher).check(matches(isDisplayed()));
                return;
            } catch (NoMatchingViewException e) {
                totalWaitingTime += INTERVAL;
            }
        }
        Assert.fail("Timeout(30s) to wait for screen");
    }

}