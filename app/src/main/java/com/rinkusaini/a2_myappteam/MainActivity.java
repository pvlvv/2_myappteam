package com.rinkusaini.a2_myappteam;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.button.MaterialButton;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView email;
    private TextView password;
    private MaterialButton loginbtn;
    private MaterialButton signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

         email = findViewById(R.id.username);
         password = findViewById(R.id.password);
         loginbtn = findViewById(R.id.loginbtn);
         signupbtn = findViewById(R.id.signupbtn);

//        String email = username.getText().toString();
//        String password = password1.getText().toString();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                try {
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void login(){
        auth = FirebaseAuth.getInstance();

        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Fragment homefragment = new HomeFragment();
                try {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
//                        try{
//                            FragmentTransaction movetohome = getSupportFragmentManager().beginTransaction();
//                            movetohome.replace(R.id.mainactivity, homefragment).commit();
//                        }
//                        catch (Exception e){
//                            System.out.println(e);
//                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    public void signup(){
        auth = FirebaseAuth.getInstance();

//        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
//                    username.setError("Email empty");
//                    password1.setError("password empty");
//                }
//                else{
        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Fragment homefragment = new HomeFragment();
                    try {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//                            FragmentTransaction movetohome = getSupportFragmentManager().beginTransaction();
//                            movetohome.replace(R.id.mainactivity, homefragment).commit();
                        } else {
                            Toast.makeText(MainActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
//                }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = auth.getCurrentUser();
//        if(user == null){
//            startActivity(new Intent(MainActivity.this, Loginfragment_HOME.class));
//        }
//    }
}
