package io.okhi.android_okcollect.utilities;

import java.util.ArrayList;

import io.okhi.android_okcollect.models.OkCollectAddressType;

/** OkHiConfig class for enabling streetview.
 * @author Ramogi Ochola
 * @author www.okhi.com
 */

public class OkHiConfig {
    private Boolean enableStreetView;
    private Boolean workAddressTypeEnabled = true;
    private Boolean homeAddressTypeEnabled = true;

    private Boolean permissionsOnboardingEnabled = true;

    private OkHiConfig(Builder builder) {
        this.enableStreetView = builder.enableStreetView;
        this.workAddressTypeEnabled = builder.workAddressTypeEnabled;
        this.homeAddressTypeEnabled = builder.homeAddressTypeEnabled;
        this.permissionsOnboardingEnabled = builder.permissionsOnboardingEnabled;
    }
    public static class Builder {
        private Boolean enableStreetView = false;
        private Boolean workAddressTypeEnabled = true;
        private Boolean homeAddressTypeEnabled = true;

        private Boolean permissionsOnboardingEnabled = true;

        /**OkHiConfig builder
         */
        public Builder() { }
        /** Enable streetview
         */
        public Builder withStreetView(Boolean enabled){
            this.enableStreetView = enabled;
            return this;
        }

        /** Enables different address types for creation
         */
        public Builder withAddressTypes(ArrayList<OkCollectAddressType> addressTypes){
            if (addressTypes.contains(OkCollectAddressType.HOME) && addressTypes.contains(OkCollectAddressType.WORK)) {
                this.homeAddressTypeEnabled = true;
                this.workAddressTypeEnabled = true;
            } else if (addressTypes.contains(OkCollectAddressType.WORK)) {
                this.workAddressTypeEnabled = true;
                this.homeAddressTypeEnabled = false;
            } else if (addressTypes.contains(OkCollectAddressType.HOME)) {
                this.workAddressTypeEnabled = false;
                this.homeAddressTypeEnabled = true;
            }
            return this;
        }

        /** Enables or Disables Home addresses for creation
         */
        public Builder withHomeAddressType(Boolean enabled) {
            this.homeAddressTypeEnabled = enabled;
            return this;
        }

        /** Enables or Disables Work addresses for creation
         */
        public Builder withWorkAddressType(Boolean enabled) {
            this.workAddressTypeEnabled = enabled;
            return this;
        }

        /** Enables or Disables permissions onboarding screens
         */
        public Builder withPermissionsOnboarding(Boolean enabled) {
            this.permissionsOnboardingEnabled = enabled;
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
    public Boolean isStreetViewEnabled() {
        return enableStreetView;
    }

    public Boolean isWorkAddressTypeEnabled() {
        return this.workAddressTypeEnabled;
    }

    public Boolean isHomeAddressTypeEnabled() {
        return this.homeAddressTypeEnabled;
    }

    public Boolean isPermissionsOnboardingEnabled() {
        return this.permissionsOnboardingEnabled;
    }
}
