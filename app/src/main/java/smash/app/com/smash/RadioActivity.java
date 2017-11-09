package smash.app.com.smash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by ranjansi on 11/9/2017.
 */

public class RadioActivity extends Activity {
    String state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_radio);

        Button submit = findViewById(R.id.submit);
        final RadioButton bug = findViewById(R.id.radiobug);
        final RadioButton task = findViewById(R.id.radiotask);

        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = (String) bug.getText();
            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = (String) task.getText();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, MainActivity.class);
                intent.putExtra("STATE", state);
                startActivity(intent);
            }
        });
    }
}
