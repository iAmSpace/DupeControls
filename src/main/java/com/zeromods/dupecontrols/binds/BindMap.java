package com.zeromods.dupecontrols.binds;

import java.util.ArrayList;
import java.util.List;

public class BindMap {
    public static final List<DupeBind> bindings = new ArrayList<>();

    public static int getKeycodeForDescription(String description) {
        for (DupeBind bind : bindings) {
            if (bind.keyDescription.equals(description))
                return bind.keyCode;
        }

        return 0;
    }

    public static String getDescriptionForKeycode(int keyCode) {
        for (DupeBind bind : bindings) {
            if (bind.keyCode == keyCode)
                return bind.keyDescription;
        }

        return "";
    }
}
