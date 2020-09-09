package io.okhi.android_okcollect.utilities;

/** OkHiConfig class for enabling streetview.
 * @author Ramogi Ochola
 * @author www.okhi.com
 */
public class OkHiConfig {
    private Boolean enableStreetView;
    private OkHiConfig(Builder builder) {
        this.enableStreetView = builder.enableStreetView;
    }
    public static class Builder {
        private Boolean enableStreetView = false;
        /**OkHiConfig builder
         */
        public Builder() { }
        /** Enable streetview
         */
        public Builder withStreetView(){
            this.enableStreetView = true;
            return this;
        }
        /** Create OkHiConfig instance
         */
        public OkHiConfig build() {
            return new OkHiConfig(this);
        }
    }
    /** Is streetview enabled
     */
    public Boolean getEnableStreetView() {
        return enableStreetView;
    }
    private void setEnableStreetView(Boolean enableStreetView) {
        this.enableStreetView = enableStreetView;
    }
}
