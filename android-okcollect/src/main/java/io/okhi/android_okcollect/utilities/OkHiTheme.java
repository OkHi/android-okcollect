package io.okhi.android_okcollect.utilities;

import androidx.annotation.NonNull;

/** OkHiTheme class for customizing the look and feel of the webview.
 * @author Ramogi Ochola
 * @author www.okhi.com
 */
public class OkHiTheme {
    private String primaryColor;
    private String url;
    private String appBarColor;

    private OkHiTheme(Builder builder) {
        this.primaryColor = builder.primaryColor;
        this.url = builder.url;
        this.appBarColor = builder.appBarColor;
    }
    /**OkHiTheme builder class
     */
    public static class Builder {
        private String primaryColor;
        private String url;
        private String appBarColor;
        /**OkHiTheme builder
         * @param primaryColor
         */
        public Builder(@NonNull String primaryColor) {
            this.primaryColor = primaryColor;
        }
        /**OkHiTheme builder enabling the appbar to be displayed in the webview
         * @param url
         * @param appBarColor
         */
        public Builder withAppBar(@NonNull String url, @NonNull String appBarColor){
            this.url = url;
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
    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
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
