package com.zeromods.dupecontrols.binds;

public class DupeBind {
    public String keyDescription;
    public int keyCode;

    public DupeBind(String keyDescription, int keyCode) {
        this.keyDescription = keyDescription;
        this.keyCode = keyCode;
    }

    public DupeBind(String keyDescription) {
        this(keyDescription, 0);
    }
}
