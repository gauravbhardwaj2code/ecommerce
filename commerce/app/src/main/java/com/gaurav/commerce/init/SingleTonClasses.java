package com.gaurav.commerce.init;

import com.gaurav.commerce.messagereader.MessageReceiver;

public class SingleTonClasses {

    public static MessageReceiver smsBroadcastReceiver;

    static {
        smsBroadcastReceiver=new MessageReceiver("EXAMON", "Your Verification");
    }
}
