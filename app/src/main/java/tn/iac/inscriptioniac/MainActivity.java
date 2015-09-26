package tn.iac.inscriptioniac;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText inputName, inputNum, inputEmail, inputStudy;
    private TextInputLayout inputLayoutName, inputLayoutNum, inputLayoutEmail, inputLayoutStudy;
    private CheckBox cbMobileDev, cbArGames, cbRobotics, cbSoftSkills, cbSsTechs, cbSeBP, cbSponsoring, cbRedaction, cbCommunication;
    private TextView tvWorkshops;
    private Button btnSignUp;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutStudy = (TextInputLayout) findViewById(R.id.input_layout_study);

        inputName = (EditText) findViewById(R.id.input_name);
        inputNum = (EditText) findViewById(R.id.input_num);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputStudy = (EditText) findViewById(R.id.input_study);

        tvWorkshops = (TextView) findViewById(R.id.tv_workshops);

        cbMobileDev = (CheckBox) findViewById(R.id.cb_mobile_dev);
        cbArGames = (CheckBox) findViewById(R.id.cb_ar_games);
        cbRobotics = (CheckBox) findViewById(R.id.cb_robotics);
        cbSoftSkills = (CheckBox) findViewById(R.id.cb_soft_skills);
        cbSsTechs = (CheckBox) findViewById(R.id.cb_ss_techs);
        cbSeBP = (CheckBox) findViewById(R.id.cb_se_best_practices);

        cbSponsoring = (CheckBox) findViewById(R.id.cb_sponsoring);
        cbCommunication = (CheckBox) findViewById(R.id.cb_communication);
        cbRedaction = (CheckBox) findViewById(R.id.cb_redaction);

        btnSignUp = (Button) findViewById(R.id.btn_inscription);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputStudy.addTextChangedListener(new MyTextWatcher(inputStudy));


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateStudy()) {
            return;
        }

        if (!validateWorkshops()) {
            return;
        }

        String workshops = buildWorkshopsString();
        String teams = buildTeamsString();

        //Create an object for PostDataTask AsyncTask
        PostDataTask postDataTask = new PostDataTask(MainActivity.this, coordinatorLayout);

        //execute asynctask
        postDataTask.execute(Common.URL, inputName.getText().toString(),
                inputNum.getText().toString(), inputEmail.getText().toString(),
                inputStudy.getText().toString(), workshops, teams);
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateStudy() {
        if (inputStudy.getText().toString().trim().isEmpty()) {
            inputLayoutStudy.setError(getString(R.string.err_msg_study));
            requestFocus(inputStudy);
            return false;
        } else {
            inputLayoutStudy.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateWorkshops() {
        if (!cbMobileDev.isChecked()&&!cbArGames.isChecked()&&!cbRobotics.isChecked()&&
        !cbSoftSkills.isChecked()&&!cbSsTechs.isChecked()&&!cbSeBP.isChecked()){
            tvWorkshops.setError(getString(R.string.err_msg_workshop));
            return false;
        } else {
            return true;
        }
    }

    private String buildWorkshopsString() {
        String workshops="";
        if (cbMobileDev.isChecked())
            workshops = cbMobileDev.getText().toString();
        if(cbArGames.isChecked()) {
            if (!workshops.isEmpty())
                workshops.concat(", " + cbArGames.getText().toString());
            else
                workshops.concat(cbArGames.getText().toString());
        }
        if(cbRobotics.isChecked()) {
            if (!workshops.isEmpty())
                workshops.concat(", " + cbRobotics.getText().toString());
            else
                workshops.concat(cbRobotics.getText().toString());
        }
        if(cbSoftSkills.isChecked()){
            if (!workshops.isEmpty())
                workshops.concat(", " + cbSoftSkills.getText().toString());
            else
                workshops.concat(cbSoftSkills.getText().toString());
        }
        if(cbSsTechs.isChecked()){
            if (!workshops.isEmpty())
                workshops.concat(", " + cbSsTechs.getText().toString());
            else
                workshops.concat(cbSsTechs.getText().toString());
        }
        if(cbSeBP.isChecked()){
            if (!workshops.isEmpty())
                workshops.concat(", " + cbSeBP.getText().toString());
            else
                workshops.concat(cbSeBP.getText().toString());
        }
        Log.d("Workshops", workshops);
        return workshops;
    }

    private String buildTeamsString() {
        String teams="";
        if (cbSponsoring.isChecked())
            teams=cbSponsoring.getText().toString();
        if(cbRedaction.isChecked())
        {
            if (!teams.isEmpty())
                teams.concat(", " + cbRedaction.getText().toString());
            else
                teams.concat(cbRedaction.getText().toString());
        }
        if(cbCommunication.isChecked())
        {
            if (!teams.isEmpty())
                teams.concat(", " + cbCommunication.getText().toString());
            else
                teams.concat(cbCommunication.getText().toString());
        }
        Log.d("Teams", teams);
        return teams;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_study:
                    validateStudy();
                    break;
            }
        }
    }
}
