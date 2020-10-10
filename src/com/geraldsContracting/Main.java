package com.geraldsContracting;

public class Main {

    public static void main(String[] args) {

        // typescript: console.log
        // python: print
        int width = getNumberByFlag( args, "--width" );
        int length = getNumberByFlag( args, "--length" );
        boolean inches = isFlagSet( args, "--inches" );

        House gerald = new House( width, length, inches );

        System.out.print("Beams: ");
        System.out.println( gerald.beams );

        System.out.print("Studs: ");
        System.out.println( gerald.studs );

    }

    public static int findIndexOfFlag( String[] args, String flag ){

        int length = args.length;
        int current = 0;

        if( length <= 0 ){
            return -1;
        }

        while( current < length ){
            if( args[current].equals( flag )){
                return current + 1;
            } else {
                current++;
            }
        }

        return -1;

    }

    public static String getValueByFlag( String[] args, String flag ){

        int index = findIndexOfFlag( args, flag );

        if( index < 0 ){
            return "";
        }

        return args[ index ];

    }

    public static int getNumberByFlag( String[] args, String flag ){

        String value = getValueByFlag( args,flag );
        return Integer.parseInt( value );

    }

    public static boolean isFlagSet( String[] args, String flag ){

        return findIndexOfFlag( args, flag ) > -1;

    }

}
