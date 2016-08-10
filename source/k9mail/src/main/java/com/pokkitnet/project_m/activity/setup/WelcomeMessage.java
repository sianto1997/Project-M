package com.pokkitnet.project_m.activity.setup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pokkitnet.project_m.R;
import com.pokkitnet.project_m.activity.Accounts;
import com.pokkitnet.project_m.activity.K9Activity;

/**
 * Displays a welcome message when no accounts have been created yet.
 */
public class WelcomeMessage extends K9Activity implements OnClickListener{

    public static void showWelcomeMessage(Context context) {
        Intent intent = new Intent(context, WelcomeMessage.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.welcome_message);

        getActionBar().hide();

        //Set title
        TextView tv = (TextView) findViewById(R.id.product_name);
        //using product sans (Copyright Google)
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/product_sans.ttf");
        tv.setTypeface(face);


        //TextView welcome = (TextView) findViewById(R.id.welcome_message);
        //welcome.setText(HtmlConverter.htmlToSpanned(getString(R.string.accounts_welcome)));
        //welcome.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.import_settings).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next: {
                AccountSetupBasics.actionNewAccount(this);
                finish();
                break;
            }
            case R.id.import_settings: {
                Accounts.importSettings(this);
                finish();
                break;
            }
        }
    }
}
