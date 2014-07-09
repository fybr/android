package systems.jarvis.fybr.receivers;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import systems.jarvis.fybr.providers.SmsModel;

public class Sms extends Receiver {

    @Override
    public void execute(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (android.telephony.SmsMessage sms : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                SmsModel model = new SmsModel();
                model.message = sms.getMessageBody();
                model.sender = sms.getOriginatingAddress();
                this.post(context, model);
            }
        }
    }

}
