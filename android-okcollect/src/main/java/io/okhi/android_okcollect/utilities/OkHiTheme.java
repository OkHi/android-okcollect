package io.okhi.android_okcollect.utilities;

import androidx.annotation.NonNull;

public class OkHiTheme {
    private String primaryColor;
    private String url;
    private String appBarColor;

    private OkHiTheme(Builder builder) {
        this.primaryColor = builder.primaryColor;
        this.url = builder.url;
        this.appBarColor = builder.appBarColor;
    }

    public static class Builder {
        private String primaryColor;
        private String url;
        private String appBarColor;
        public Builder(@NonNull String primaryColor) {
            this.primaryColor = primaryColor;
        }
        public Builder withAppBar(@NonNull String url, @NonNull String appBarColor){
            this.url = url;
            this.appBarColor = appBarColor;
            return this;
        }
        public OkHiTheme build() {
            return new OkHiTheme(this);
        }
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppBarColor() {
        return appBarColor;
    }

    public void setAppBarColor(String appBarColor) {
        this.appBarColor = appBarColor;
    }
}
