/*
*
* Copyright (C) 2011 Dmitry Savchenko <dg.freak@gmail.com>
*
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
*
*/

package com.phonegap.plugins.statusBarNotification;

import com.jumahe.bluetoothapp.R;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import org.apache.cordova.DroidGap;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;

@TargetApi(16) public class StatusBarNotification extends Plugin {
	//	Action to execute
	public static final String NOTIFY = "notify";
	public static final String CLEAR = "clear";
	
	/**
	 * 	Executes the request and returns PluginResult
	 * 
	 * 	@param action		Action to execute
	 * 	@param data			JSONArray of arguments to the plugin
	 *  @param callbackId	The callback id used when calling back into JavaScript
	 *  
	 *  @return				A PluginRequest object with a status
	 * */
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		String ns = Context.NOTIFICATION_SERVICE;
		mNotificationManager = (NotificationManager) cordova.getActivity().getSystemService(ns);
        context = cordova.getActivity();
		
		PluginResult result = null;
		if (NOTIFY.equals(action)) {
			try {

				String title = data.getString(0);
				String body = data.getString(1);
				Log.d("NotificationPlugin", "Notification: " + title + ", " + body);
				showNotification(title, body);
				result = new PluginResult(Status.OK);
			} catch (JSONException jsonEx) {
				Log.d("NotificationPlugin", "Got JSON Exception "
						+ jsonEx.getMessage());
				result = new PluginResult(Status.JSON_EXCEPTION);
			}
		} else if (CLEAR.equals(action)){
			clearNotification();
		} else {
			result = new PluginResult(Status.INVALID_ACTION);
			Log.d("NotificationPlugin", "Invalid action : "+action+" passed");
		}
		return result;
	}

	/**
	 * 	Displays status bar notification
	 * 
	 * 	@param contentTitle	Notification title
	 *  @param contentText	Notification text
	 * */
	public void showNotification( CharSequence contentTitle, CharSequence contentText ) {
		int icon = R.drawable.nofication;
        
        Notification noti = new Notification.Builder(context)
          .setContentTitle(contentTitle)
          .setContentText(contentText)
          .setSmallIcon(icon)
          .build();
        //notification.flags |= Notification.FLAG_NO_CLEAR; //Notification cannot be clearned by user


        mNotificationManager.notify(1, noti);
	}
	
	/**
	 * Removes the Notification from status bar
	 */
	public void clearNotification() {
		mNotificationManager.cancelAll();
	}
	
	private NotificationManager mNotificationManager;
    private Context context;
}
