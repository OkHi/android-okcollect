package io.okhi.android_okcollect.callbacks;

import io.okhi.android_core.models.OkHiException;

public interface OkCollectCallback<OkHiUser, OkHiLocation> {
    void onSuccess(OkHiUser user, OkHiLocation location);
    void onClose();
    void onError(OkHiException e);
}