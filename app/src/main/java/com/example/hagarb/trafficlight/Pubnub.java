package com.example.hagarb.trafficlight;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonPrimitive;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;


public class Pubnub {
    private Configuration pubnubConfig = new Configuration();
    public Pubnub() {
        PNConfiguration pnConfiguration = new PNConfiguration();
        //The subscribeKey and publishKey can be changed
        //Please subscribe with pubnub on pubnub.com and create your own project

        pnConfiguration.setSubscribeKey(pubnubConfig.getPubnubSubscribeKey());
        pnConfiguration.setPublishKey(pubnubConfig.getPubnubPublishKey());
        pnConfiguration.setSecure(true);
        PubNub pubnub = new PubNub(pnConfiguration);
        pubnublistner(pubnub); // create listner
    }

    private void pubnublistner(PubNub pubnub) {
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

                            /*
                                   if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                                       // This event happens when radio / connectivity is lost
                                   }

                                   else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                                       // Connect event. You can do stuff like publish, and know you'll get it.
                                       // Or just use the connected event to confirm you are subscribed for
                                       // UI / internal notifications, etc

                                       ///////////////***************Publish messages************ No Need!!!!!!!!!!!!!!!!!!

                                       if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
                                           pubnub.publish().channel("guy").message("kori").async(new PNCallback<PNPublishResult>() {
                                               @Override
                                               public void onResponse(PNPublishResult result, PNStatus status) {
                                                   // Check whether request successfully completed or not.
                                                   if (!status.isError()) {

                                                       // Message successfully published to specified channel.
                                                   }
                                                   // Request processing failed.
                                                   else {

                                                       // Handle message publish error. Check 'category' property to find out possible issue
                                                       // because of which request did fail.
                                                       //
                                                       // Request can be resent using: [status retry];
                                                   }
                                               }
                                           });
                                       }
                                   }
                                   else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {

                                       // Happens as part of our regular operation. This event happens when
                                       // radio / connectivity is lost, then regained.
                                   }
                                   else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                                       // Handle messsage decryption error. Probably client configured to
                                       // encrypt messages and on live data feed it received plain text.
                                   }
                            */
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                JsonPrimitive contacts = message.getMessage().getAsJsonPrimitive();
                String s = contacts.getAsString();
                int statusIndex = s.indexOf("conditions");
                int TlcCodeIndex = s.indexOf("id");
                if (s.charAt(statusIndex + 1) == '0') {

                }
                Log.d(s, s);
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }

        });

        pubnub.subscribe().channels(Arrays.asList(pubnubConfig.getChanel())).execute();

    }//onCreate
}
