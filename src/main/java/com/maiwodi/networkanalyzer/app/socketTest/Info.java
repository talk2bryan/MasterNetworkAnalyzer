package com.maiwodi.networkanalyzer.app.socketTest;

import java.io.Serializable;

public class Info implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1863113025933724491L;

    private String name;

    private String description;

    private boolean readOnly = true;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public boolean isReadOnly() {
	return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
	this.readOnly = readOnly;
    }

}
