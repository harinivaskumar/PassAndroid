package org.ligi.passandroid.model;

import com.google.common.base.Optional;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class PassWriter {

    public static String toJSON(Pass pass) {

        final JSONObject object = new JSONObject();

        try {
            object.put("description", pass.getDescription());
            object.put("id", pass.getId());
            object.put("type", pass.getType());

            if (pass.getBarCode().isPresent()) {
                final JSONObject barcode = new JSONObject();
                barcode.put("message", pass.getBarCode().get().getMessage());
                barcode.put("type", pass.getBarCode().get().getBarcodeFormat());

                final Optional<String> alternativeText = pass.getBarCode().get().getAlternativeText();
                if (alternativeText.isPresent()) {
                    barcode.put("altText", alternativeText.get());
                }

                object.put("barcode", barcode);
            }

            object.put("fgColor", "#" +String.format("%08X", pass.getForegroundColor()));
            object.put("bgColor", "#" +String.format("%08X", pass.getBackGroundColor()));

            final Optional<DateTime> relevantDate = pass.getRelevantDate();

            if (relevantDate.isPresent()) {
                final JSONObject timeObject = new JSONObject();
                timeObject.put("dateTime", relevantDate.get().toString());
                object.put("when", timeObject);
            }

            return object.toString(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
