package n.appwidgetsample;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MyWidget extends AppWidgetProvider {

    // Name of shared preferences file & key
    private static final String SHARED_PREF_FILE = "appwidgetsample";
    private static final String COUNT_KEY = "count";

    /**
     * Update a single app widget.  This is a helper method for the standard
     * onUpdate() callback that handles one widget update at a time.
     *
     * @param context          The application context.
     * @param appWidgetManager The app widget manager.
     * @param appWidgetId      The current app widget id.
     */
    private void updateAppWidget(Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Get the count from prefs.
        SharedPreferences prefs =
                context.getSharedPreferences(SHARED_PREF_FILE, 0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId, 0);
        count++;

        // Get the current time.
        String dateString =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // Construct the RemoteViews object.
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        views.setTextViewText(R.id.appwidget_id,
                String.valueOf(count));

        // Save count back to prefs.
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putInt(COUNT_KEY + appWidgetId, count);
        prefEditor.apply();

//        Toast.makeText(context, "Clicked!! "+count, Toast.LENGTH_SHORT).show();
        // Setup update button to send an update request as a pending intent.
        Intent intentUpdate = new Intent(context, MyWidget.class);

        // The intent action must be an app widget update.
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // Include the widget ID to be updated as an intent extra.
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        // Wrap it all in a pending intent to send a broadcast.
        // Use the app widget ID as the request code (third argument) so that
        // each intent is unique.
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        // Assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate);

        // Instruct the widget manager to update the widget.
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them.
        for (int appWidgetId : appWidgetIds) {
            Toast.makeText(context, "Clicked!! ", Toast.LENGTH_SHORT).show();
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }




//    List<JsonDTO> data = null;
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        //Update widget
//        for (int appWidgetId : appWidgetIds) {
//
//            String jsData = loadJSONFromAsset(context);
//
//            Type type = new TypeToken<ArrayList<JsonDTO>>() {}.getType();
//            data = new Gson().fromJson(jsData,type);
//            String text = data.get(new Random().nextInt(data.size())).data;
//
//
//            // Instantiate the RemoteViews object based on the application widget layout
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
//            //Update your app’s text, using the setTextViewText method of the RemoteViews class
//            views.setTextViewText(R.id.launch_url, text);
//
//
//            // Register an onClickListener
////            Intent clickIntent = new Intent(context.getApplicationContext(), MyWidget.class);
//////
////            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//////            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,allWidgetIds);
//////
////            PendingIntent pendingIntent = PendingIntent.getBroadcast(
////                    context.getApplicationContext(), 0, clickIntent,
////                    PendingIntent.FLAG_UPDATE_CURRENT);
////            views.setOnClickPendingIntent(R.id.launch_url, pendingIntent);
//            Toast.makeText(context, "Clicked!! "+text, Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://code.tutsplus.com/"));
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//            views.setOnClickPendingIntent(R.id.launch_url, pendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        }
//    }
//    private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO Auto-generated method stub
//        super.onReceive(context, intent);
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
//
//        if(data != null) {
//
//            String text = data.get(new Random().nextInt(data.size())).data;
//            Toast.makeText(context, "Clicked!! "+text, Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//
//
//    public static String loadJSONFromAsset(Context context) {
//        String json = null;
//        try {
//            InputStream is = context.getAssets().open("text.json");
//            int size = is.available();
//
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//
//    }
}
