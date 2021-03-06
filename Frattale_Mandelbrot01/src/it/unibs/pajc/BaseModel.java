package it.unibs.pajc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.*;

/// attenzione a non infragere (troppo) il "single responsibility principle"

public class BaseModel {
	protected EventListenerList listenerList = new EventListenerList();

	public void addChangeListener(ChangeListener l) {
	    listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
	    listenerList.remove(ChangeListener.class, l);
	}

	protected void fireValuesChange() {
		fireValuesChange(new ChangeEvent(this));
	}
	
	protected void fireValuesChange(ChangeEvent changeEvent) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = listeners.length - 2; i >= 0; i -=2 ) {
	        if (listeners[i] == ChangeListener.class) {
	            ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
	        }
	    }
	}
}
