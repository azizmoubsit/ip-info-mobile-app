package ma.enset.moubsit_aziz_exam_m1_iibdcc_23;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this::login);
    }

    public void login(View view) {
        String uname = username.getText().toString();
        String pass = password.getText().toString();
        if (uname.isEmpty()) {
            username.setError("Username is required");
            return;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required");
            return;
        }
        if(!uname.equals("moubsit") || !pass.equals("aziz")) {
            showErrorSnackbar("wrong credentials");
            return;
        }
        Intent intent = new Intent(LoginActivity.this, IpInfoActivity.class);
        startActivity(intent);
        username.setText("");
        password.setText("");
    }

    private void showErrorSnackbar(String message) {
        View parentView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            ((CoordinatorLayout.LayoutParams) params).gravity = Gravity.TOP;
        } else if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
        }
        snackbar.getView().setLayoutParams(params);
        snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
//        snackbarView.setBackgroundColor(getResources().getColor(R.color.green));
        snackbar.show();
    }
}