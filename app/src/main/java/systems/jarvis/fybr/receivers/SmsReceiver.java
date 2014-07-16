package systems.jarvis.fybr.receivers;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import systems.jarvis.fybr.providers.Sms;

public class SmsReceiver extends Receiver {

    @Override
    public void execute(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (android.telephony.SmsMessage sms : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                Sms model = new Sms();
                model.message = sms.getDisplayMessageBody();
                model.from = sms.getDisplayOriginatingAddress();
                model.thread = model.from;
                this.post(context, model);
            }
        }
    }

}
