package io.okhi.android_okcollect.utilities;

import io.okhi.android_core.models.OkHiAccessScope;

public class Constants {
    public static final String[] SCOPES = {OkHiAccessScope.VERIFY};
    //Prod
    public static final String PROD_HEART_URL_POST_22 = "https://manager-v5.okhi.io";
    public static final String PROD_HEART_URL_PRE_22 = "https://legacy-manager-v5.okhi.io";

    // Sandbox
    public static final String SANDBOX_HEART_URL_POST_22 = "https://sandbox-manager-v5.okhi.io";
    public static final String SANDBOX_HEART_URL_PRE_22 =  "https://sandbox-legacy-manager-v5.okhi.io";

    //Dev
    public static final String DEV_HEART_URL_POST_22 = "https://dev-manager-v5.okhi.io";
    public static final String DEV_HEART_URL_PRE_22 = "https://dev-legacy-manager-v5.okhi.io";
}
