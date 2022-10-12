package org.debian.maven.cliargs;

/**
 * Command line argument, either an option or a parameter.
 */
public class Argument {
    /** The name of the option, or null for a parameter */
    public final String name;

    /** */
    public final String value;

    /** The type of the argument (short option, long option or a parameter) */
    public final Type type;

    public enum Type {
        /** Long option (i.e. --foo) */
        LONG,
        
        /** Short option (i.e. -f) */
        SHORT,
        
        /** Parameter */
        ARG
    }
    
    Argument(final String arg) {
        final String trimmed = arg.trim();

        if (trimmed.startsWith("--")) {
            type = Type.LONG;
            int equalsPosition = trimmed.indexOf("=");
            if (-1 == equalsPosition) {
                name = trimmed.substring(2);
                value = null;
            } else {
                name = trimmed.substring(2, equalsPosition);
                value = trimmed.substring(equalsPosition + 1);
            }
        } else if (trimmed.startsWith("-")) {
            type = Type.SHORT;
            name = trimmed.substring(1, 2);
            value = trimmed.length() <= 2 ? null : trimmed.substring(2);
        } else {
            type = Type.ARG;
            name = null;
            value = trimmed;
        }
    }
}
