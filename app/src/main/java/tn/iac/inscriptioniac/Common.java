package tn.iac.inscriptioniac;

import com.squareup.okhttp.MediaType;

public class Common {
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/1B8gUoyAfWxZpM877TpxS6qed7_GQswt-7PhceU55I6E/formResponse";
    //input element ids found from the live form page
    public static final String NAME_KEY="entry_1226439302";
    public static final String NUMBER_KEY="entry_307249848";
    public static final String EMAIL_KEY="entry_1996363870";
    public static final String STUDY_KEY="entry_1973542935";
    public static final String WORKSHOPS_KEY="entry_1873728906";
    public static final String TEAMS_KEY="entry_125137913";

    public static final String EXTRA = "success";
}
