package com.maiwodi.networkanalyzer.app.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utilities {
	/**
	 * @param title   (if the title is null, the default title is Info)
	 * @param message
	 */
	public static void showInfoMessage(String title, String message) {

		title = title == null ? "Info" : title;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(title, message));
	}

	/**
	 * 
	 * @param title   (if the title is null, the default title is Warning)
	 * @param message
	 */
	public static void showWarningMessage(String title, String message) {
		title = title == null ? "Warning" : title;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, message));
	}

	/**
	 * 
	 * @param title   (If the title is null, the default title is Error)
	 * @param message
	 */
	public static void showErrorMessage(String title, String message) {
		title = title == null ? "Error" : title;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
	}

	/**
	 * 
	 * @param title   (If the title is null, the default title is Fatal)
	 * @param message
	 */
	public static void showFatalMessage(String title, String message) {
		title = title == null ? "Fatal" : title;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, title, message));
	}

}
