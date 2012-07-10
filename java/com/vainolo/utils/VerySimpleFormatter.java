/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vainolo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Arieh Bibliowicz
 */
public class VerySimpleFormatter extends SimpleFormatter {

    @Override
    public synchronized String format(LogRecord record) {
        Calendar c = Calendar.getInstance();
        String retVal = "[" +
                c.get(Calendar.DAY_OF_MONTH) + "/" +
                (c.get(Calendar.MONTH) + 1) + "/" +
                c.get(Calendar.YEAR) + "-" +
                c.get(Calendar.HOUR_OF_DAY) + ":" +
                c.get(Calendar.MINUTE) + ":" +
                c.get(Calendar.SECOND) + " " +
                record.getLevel() + "] (" +
                record.getSourceClassName() + "." +
                record.getSourceMethodName() + ") " +
                record.getMessage() + "\n";
        Throwable thrown = record.getThrown();
        StringWriter writer = new StringWriter();
        if(thrown != null) {
            thrown.printStackTrace(new PrintWriter(writer));
            retVal += writer.getBuffer();
        }
        return retVal;
    }

    public static void main(String args[]) {
        System.out.println("This is working!");
    }
}
