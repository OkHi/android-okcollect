package io.okhi.android_okcollect.callbacks;

import io.okhi.android_core.models.OkHiException;

public interface OkCollectCallback<S, T> {
    void onSuccess(S result1, T result2);
    void onError(OkHiException e);
}