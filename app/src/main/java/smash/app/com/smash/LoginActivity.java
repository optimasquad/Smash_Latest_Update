package smash.app.com.smash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import smash.app.com.smash.AppDatabase;
import smash.app.com.smash.Login;


public class LoginActivity extends AppCompatActivity {
    Button b1, b2;
    EditText ed1, ed2;

    private Login user;
    private AppDatabase database;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = AppDatabase.getDatabase(getApplicationContext());

                // cleanup for testing some initial data
                database.loginDao().removeAllUsers();
                // add some data
                List<Login> users = database.loginDao().getAllUser();

                if (users.size() == 0) {
                    database.loginDao().addUser(new Login(1, "test", "test"));
                    user = database.loginDao().getAllUser().get(0);


                    String p = ed1.getText().toString();
                    String n = ed2.getText().toString();


                    if (ed1.getText().toString().equals(user.name) &&
                            ed2.getText().toString().equals(user.password)) {
                        Intent intent = new Intent(LoginActivity.this, RadioActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
