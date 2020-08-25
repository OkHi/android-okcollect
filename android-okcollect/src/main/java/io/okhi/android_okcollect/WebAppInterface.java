package io.okhi.android_okcollect;

import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;


class WebAppInterface {
    OkHeartActivity mContext;
    WebAppInterface(OkHeartActivity c) {
        mContext = c;
    }

    @JavascriptInterface
    public void receiveMessage(String results) {
        displayLog(results);
        mContext.receiveMessage(results);
        /*
        try {
            final JSONObject jsonObject = new JSONObject(results);

            String message = jsonObject.optString("message");
            JSONObject payload = jsonObject.optJSONObject("payload");
            if (payload == null) {
                String backuppayload = jsonObject.optString("payload");
                if (backuppayload != null) {
                    payload = new JSONObject();
                    payload.put("error", backuppayload);
                }
            }

                switch (message) {
                    case "app_state":
                        if (payload != null) {
                            Boolean ready = payload.optBoolean("ready");
                            if (ready != null) {
                                if (ready) {
                                    mContext.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mContext.startApp();
                                        }
                                    });
                                }
                            }
                        }
                        break;
                    case "location_created":
                        try {
                            OkCollect.getCallback().querycomplete(jsonObject);
                        } catch (Exception e) {

                        }
                        try {
                            OkHeartActivity.setCompletedWell(true);
                            OkHeartActivity.setIsWebInterface(true);
                            mContext.finish();
                        } catch (Exception e) {

                        } finally {
                        }

                        break;
                    case "location_updated":
                        try {
                            OkCollect.getCallback().querycomplete(jsonObject);
                        } catch (Exception e) {
                        }
                        try {
                            OkHeartActivity.setCompletedWell(true);
                            OkHeartActivity.setIsWebInterface(true);
                            mContext.finish();
                        } catch (Exception e) {
                        } finally {

                        }
                        break;
                    case "location_selected":
                        try {
                            OkCollect.getCallback().querycomplete(jsonObject);
                        } catch (Exception e) {
                        }
                        try {
                            OkHeartActivity.setCompletedWell(true);
                            OkHeartActivity.setIsWebInterface(true);
                            mContext.finish();
                        } catch (Exception e) {
                        } finally {
                        }
                        break;
                    case "fatal_exit":
                        try {
                            jsonObject.put("payload", payload);
                            jsonObject.put("message", "fatal_exit");
                            //OkCollect.getCallback().querycomplete(jsonObject);
                        } catch (Exception e) {
                        }
                        try {
                            //OkHeartActivity.setCompletedWell(true);
                            //OkHeartActivity.setIsWebInterface(true);
                            mContext.finish();
                        } catch (Exception e) {
                        } finally {

                        }
                        break;

                    default:
                        break;

                }
        } catch (JSONException e) {
            displayLog("JSON object error "+e.toString());
        }
        */

    }
    private void displayLog(String log){
        Log.i("WebAppInterface", log);
    }
}

