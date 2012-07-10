/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vainolo.utils;

import java.util.logging.ConsoleHandler;

/**
 *
 * @author Arieh Bibliowicz
 */
public class VerySimpleHandler extends ConsoleHandler {
    public VerySimpleHandler() {
        super();
        setFormatter(new VerySimpleFormatter());

    }
}
