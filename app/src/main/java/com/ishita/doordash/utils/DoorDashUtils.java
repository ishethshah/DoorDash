package com.ishita.doordash.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DoorDashUtils {

    private static final String TAG = "DoorDashUtils";

    public static String convertCentToDollar(int cents) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        return n.format(cents / 100.0);
    }

    public static String getCompleteAddressString(Context context, double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                return strReturnedAddress.toString();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Canont get Address!");
        }
        return null;
    }
}
