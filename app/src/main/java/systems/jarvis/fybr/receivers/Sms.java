package systems.jarvis.fybr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;

public class Sms extends Receiver {

    @Override
    public void execute(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (android.telephony.SmsMessage sms : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody =  sms.getOriginatingAddress() + " - " + sms.getMessageBody();
                Log.i("", messageBody);
            }
        }
    }

}
