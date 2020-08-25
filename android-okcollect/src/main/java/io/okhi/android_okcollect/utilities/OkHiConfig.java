package io.okhi.android_okcollect.utilities;

public class OkHiConfig {
    private Boolean enableStreetView;

    private OkHiConfig(Builder builder) {
        this.enableStreetView = builder.enableStreetView;
    }

    public static class Builder {
        private Boolean enableStreetView = false;
        public Builder() { }
        public Builder withStreetView(){
            this.enableStreetView = true;
            return this;
        }
        public OkHiConfig build() {
            return new OkHiConfig(this);
        }
    }


    public Boolean getEnableStreetView() {
        return enableStreetView;
    }

    public void setEnableStreetView(Boolean enableStreetView) {
        this.enableStreetView = enableStreetView;
    }
}
