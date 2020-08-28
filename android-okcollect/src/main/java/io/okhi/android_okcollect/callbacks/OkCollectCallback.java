package io.okhi.android_okcollect.callbacks;

import io.okhi.android_core.models.OkHiException;

public interface OkCollectCallback<S, T> {
    void onSuccess(S user, T location);
    void onError(OkHiException e);
}