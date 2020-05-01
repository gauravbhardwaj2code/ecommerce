package com.lms.exam.messagereader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsBroadcastReceiver";

    private String serviceProviderNumber;
    private String serviceProviderSmsCondition;

    private Map<String, GauravMessageListener> messageListenerMap = new HashMap<>();

    public MessageReceiver(String serviceProviderNumber, String serviceProviderSmsCondition) {
        this.serviceProviderNumber = serviceProviderNumber;
        this.serviceProviderSmsCondition = serviceProviderSmsCondition;
    }

    public MessageReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("$$$$$$$$$$$$");
        if (messageListenerMap != null && messageListenerMap.size() > 0) {
            for (GauravMessageListener gauravMessageListener : messageListenerMap.values()) {
                gauravMessageListener.onTextReceived("hgwdsvhjabsjasdbsbdkjanksdnkashkjdkjashdkjahsdkhaskjdhkajhdkjhaskd");
            }

        }
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody += smsMessage.getMessageBody();
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        // Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            if (smsSender.equals(serviceProviderNumber) && smsBody.contains(serviceProviderSmsCondition)) {
                if (messageListenerMap != null && messageListenerMap.size() > 0) {
                    for (GauravMessageListener gauravMessageListener : messageListenerMap.values()) {
                        gauravMessageListener.onTextReceived(smsBody);
                    }

                }
            }
        }
    }

    public void setGauravMessageListener(String key, GauravMessageListener gauravMessageListener) {
        messageListenerMap.put(key, gauravMessageListener);
    }


    public void clear() {
        messageListenerMap = new HashMap<>();
    }


}