package com.heroGame.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Entity class mapped to the National table.
 */
public class National {

    private final IntegerProperty nationalId;
    private final StringProperty nationalName;

    public National() {
        this(0, "");
    }

    public National(int nationalId, String nationalName) {
        this.nationalId = new SimpleIntegerProperty(nationalId);
        this.nationalName = new SimpleStringProperty(nationalName);
    }

    public int getNationalId() {
        return nationalId.get();
    }

    public void setNationalId(int nationalId) {
        this.nationalId.set(nationalId);
    }

    public IntegerProperty nationalIdProperty() {
        return nationalId;
    }

    public String getNationalName() {
        return nationalName.get();
    }

    public void setNationalName(String nationalName) {
        this.nationalName.set(nationalName);
    }

    public StringProperty nationalNameProperty() {
        return nationalName;
    }

    @Override
    public String toString() {
        // Used so National objects display nicely inside a ComboBox
        return nationalName.get();
    }
}
