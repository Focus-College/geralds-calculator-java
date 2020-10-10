package com.geraldsContracting;

public class House {

	final public static double BEAM_WIDTH = 3.5;
	final public static double BOARD_LENGTH = 8 * 12;
	final public static double WASTE_MULTIPLIER = 0.1;

	public double studs;
	public double beams;

	public House( int width, int length, boolean isInches ){

		width = isInches ? width : width * 12;
		length = isInches ? length : length * 12;

		// calculate the space in between corner beams
		double innerWidthOfHouse = width - ( BEAM_WIDTH * 2 );
		double innerLengthOfHouse = length - ( BEAM_WIDTH * 2 );

		Wall wall1 = new Wall( innerWidthOfHouse );
		Wall wall2 = new Wall( innerLengthOfHouse );

		this.studs = accountForWaste(( wall1.studs + wall2.studs ) * 2);
		this.beams = accountForWaste((( wall1.beams + wall2.beams ) * 2) + 4);

	}

	private double accountForWaste( double items ){
		return Math.ceil( items * WASTE_MULTIPLIER ) + items;
	}

}
