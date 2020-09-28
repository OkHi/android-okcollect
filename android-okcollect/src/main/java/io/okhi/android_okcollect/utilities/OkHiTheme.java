package io.okhi.android_okcollect.utilities;

import androidx.annotation.NonNull;

/** OkHiTheme class for customizing the look and feel of the webview.
 * @author Ramogi Ochola
 * @author www.okhi.com
 */
public class OkHiTheme {
    private String primaryColor;
    private String logoUrl;
    private String appBarColor;
    private Boolean appBarVisible;

    private OkHiTheme(Builder builder) {
        this.primaryColor = builder.primaryColor;
        this.logoUrl = builder.logoUrl;
        this.appBarColor = builder.appBarColor;
        this.appBarVisible = builder.appBarVisible;
    }
    /**OkHiTheme builder class
     */
    public static class Builder {
        private String primaryColor;
        private String logoUrl;
        private String appBarColor;
        private Boolean appBarVisible = true;
        /**OkHiTheme builder
         * @param primaryColor
         */
        public Builder(@NonNull String primaryColor) {
            this.primaryColor = primaryColor;
        }
        /**OkHiTheme builder enabling the appbar to be displayed in the webview
         * @param logoUrl Optional. Sets the logo image to display in your app bar
         */
        public Builder setAppBarLogo(@NonNull String logoUrl){
            this.logoUrl = logoUrl;
            return this;
        }

        /**
         * @param appBarColor Optional. Sets the color of your app bar
         */
        public Builder setAppBarColor(@NonNull String appBarColor){
            this.appBarColor = appBarColor;
            return this;
        }
        /** Create an instance of OkHiTheme class
         */
        public OkHiTheme build() {
            return new OkHiTheme(this);
        }
    }
    /** Get the value of the primary color
     */
    public String getPrimaryColor() {
        return primaryColor;
    }

    private void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }
    /** Get the value of the url for the displayed logo
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    /** Is the app bar visible?
     */
    public Boolean getAppBarVisible() {
        return appBarVisible;
    }

    public void setAppBarVisible(Boolean appBarVisible) {
        this.appBarVisible = appBarVisible;
    }

    /** Get the value of the color to be used in the appbar
     */
    public String getAppBarColor() {
        return appBarColor;
    }

    private void setAppBarColor(String appBarColor) {
        this.appBarColor = appBarColor;
    }
}
