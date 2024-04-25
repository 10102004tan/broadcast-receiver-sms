<h1>Project broadcast receiver</h1>
<h2>Libraries: </h2>
<ul>
  <li>implementation ("com.google.android.gms:play-services-auth:19.0.0")</li>
  <li>implementation ("com.google.android.gms:play-services-auth-api-phone:17.5.0")</li>
</ul>


<div>
  <h2>How to register broadcast Receiver</h2>
  <div>
    <div>
      private void registerBroadcastReceiver(){

        broadcastReceiver = new SmsBroadcastReceiver();
        broadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,REQ_USER_CONSENT);
            }
            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        this.registerReceiver(broadcastReceiver,intentFilter,RECEIVER_EXPORTED);
    }
    </div>

    Why use RECEIVER_EXPORTED , u can read : https://stackoverflow.com/questions/77235063/one-of-receiver-exported-or-receiver-not-exported-should-be-specified-when-a-rec
    
  </div>
  
  <h2>How to show dialog bottom when have otp</h2>
  Create method startSmartUserConsent in Activity
  <p>
    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }
  </p>
  <h2>How to get otp </h2>
  <p>
    private void getOtpFromMessage(String message) {
        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()){
            otp.setText(matcher.group(0));
        }
    }
  </p>
</div>
