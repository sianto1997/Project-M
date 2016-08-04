package com.pokkitnet.project_m.endtoend.pages;

import com.pokkitnet.project_m.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class AccountSetupPage extends AbstractPage {

    public AccountSetupPage inputEmailAddress(String emailAddress) {
        onView(withId(R.id.account_email)).perform(typeText(emailAddress));
        return this;
    }

    public AccountSetupPage inputPassword(String password) {
        onView(withId(R.id.account_password)).perform(typeText(password));
        return this;
    }

    public AccountTypePage clickManualSetup() {
        onView(withId(R.id.manual_setup)).perform(click());
        return new AccountTypePage();
    }

}
