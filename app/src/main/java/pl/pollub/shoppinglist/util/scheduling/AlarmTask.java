package pl.pollub.shoppinglist.util.scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import pl.pollub.shoppinglist.service.NotifyService;


/**
 * Set an alarm for the date passed into the constructor
 * When the alarm is raised it will start the NotifyService
 * <p>
 * This uses the android build in alarm manager *NOTE* if the phone is turned off this alarm will be cancelled
 * <p>
 * This will run on it's own thread.
 *
 * @author paul.blundell
 */
public class AlarmTask implements Runnable {

    private static int UNIQUE_INT_PER_CALL = 0;

    private final Calendar date;
    private final AlarmManager am;
    private final Context context;
    private String title, message;

    public AlarmTask(Context context, Calendar date, String title, String message) {
        UNIQUE_INT_PER_CALL++;
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.title = title;
        this.message = message;
    }

    @Override
    public void run() {
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getService(context, UNIQUE_INT_PER_CALL, intent, 0);

        am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
    }
}