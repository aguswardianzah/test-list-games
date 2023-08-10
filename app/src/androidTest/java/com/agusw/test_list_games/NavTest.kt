package com.agusw.test_list_games

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.agusw.test_list_games.pages.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navHome() {
        onView(withId(R.id.home_menu))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Games For You")).check(matches(isDisplayed()))
    }

    @Test
    fun navFavorite() {
        onView(withId(R.id.fav_menu))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Favorite Games")).check(matches(isDisplayed()))
    }
}