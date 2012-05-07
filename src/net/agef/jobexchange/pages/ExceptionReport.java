/**
 * 
 */
package net.agef.jobexchange.pages;

import org.apache.tapestry5.services.ExceptionReporter;

/**
 * 
 * Diese Klasse ist eine angepasste Implementierung des Standard Tapestry5 Fehlerberichts. 
 * Anhand der gleichnamigen .tml Datei kann das Layout der Fehlermeldungen der Anwendung bestimmt werden.
 *  
 * @author Andreas Pursian
 * @version 1.0
 */

public class ExceptionReport implements ExceptionReporter
{
    private String error;

    public void reportException(Throwable exception)
    {
        error = exception.getLocalizedMessage();
    }

    public String getError()
    {
        return error;
    }
}