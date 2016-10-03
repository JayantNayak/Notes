package com.example.jayant.notes.interfaces;

/**
 * Created by I329687 on 9/25/2016.
 */
public interface Changeable {
    /**
     * Undoes an action
     */

    public void undo();

    /**
     * Redoes an action
     */
    public void redo();
}
