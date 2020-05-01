package com.lms.exam.init;

import com.lms.exam.messagereader.MessageReceiver;

public class SingleTonClasses {

    public static MessageReceiver smsBroadcastReceiver;

    static {
        smsBroadcastReceiver = new MessageReceiver("EXAMON", "Your Verification");
    }
}
