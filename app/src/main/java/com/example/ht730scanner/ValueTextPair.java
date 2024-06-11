package com.example.ht730scanner;

/**
 * Used for adding key/value pairs to a Spinner.
 */
public class ValueTextPair {
    private int value;
    private String text;

    public ValueTextPair(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ValueTextPair){
            ValueTextPair p = (ValueTextPair) obj;
            if (p.getText().equals(text) && p.getValue()==value) return true;
        }
        return false;
    }
}
