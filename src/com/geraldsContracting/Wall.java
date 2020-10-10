package com.geraldsContracting;

public class Wall {

	final public static double STUDS_OFFSET = 16;
	final public static double BEAMS_REQUIRED_EVERY_INCHES = (20 * 12);
	final public static double FULL_BOARDS_IN_SECTION = Math.floor( BEAMS_REQUIRED_EVERY_INCHES / House.BOARD_LENGTH );
	final public static double FULL_BOARD_SECTION_SIZE = FULL_BOARDS_IN_SECTION * House.BOARD_LENGTH;

	private double inches;
	public double studs;
	public double beams;

	public Wall( double inches ){

		this.inches = inches;
		this.beams = this.getRequiredBeams();
		double fullSections = getFullSections( inches, this.beams );
		double lastSectionSize = getLastSectionSize( inches, this.beams );
		this.studs = getBoardsInLength( FULL_BOARD_SECTION_SIZE ) * fullSections + getBoardsInLength( lastSectionSize );

	}

	public double getRequiredBeams(){

		double wallLengthOverMinRequired = getWallLengthOverMinimumRequiredBeforeBeam( inches );
		double wallLengthPlusBeam = BEAMS_REQUIRED_EVERY_INCHES + House.BEAM_WIDTH;
		return Math.ceil( wallLengthOverMinRequired / wallLengthPlusBeam );

	}

	static double isBeamRequired( double inches ){

		double wallLengthOverMinRequired = Math.max( inches - BEAMS_REQUIRED_EVERY_INCHES, 0 );
		double wholeNumber = Math.ceil( wallLengthOverMinRequired );
		return Math.min( wholeNumber, 1 );

	}

	static double getLastSectionSize( double inches, double beams ){

		double fullSections = getFullSections( inches, beams );
		return inches - ( beams * House.BEAM_WIDTH ) - ( fullSections * FULL_BOARD_SECTION_SIZE );

	}

	static double getFullSections( double inches, double beams ){

		// how many inches will we remove from a section between beams to get to the last full board
		double inchesReducedPerSection = BEAMS_REQUIRED_EVERY_INCHES - FULL_BOARD_SECTION_SIZE;

		// how big is the last section if all beams are at BEAMS_REQUIRED_EVERY_INCHES
		double lastSectionSize = inches - ( beams * ( BEAMS_REQUIRED_EVERY_INCHES + House.BEAM_WIDTH ));

		// how many inches of boards can we add to the last section before it will add an additional beam to the structure
		double remainingBeforeNewBeam = BEAMS_REQUIRED_EVERY_INCHES - lastSectionSize;

		// how many complete portions of the inchesReducedPerSection can we move to the last section
		double fullSections = Math.floor( remainingBeforeNewBeam / inchesReducedPerSection );

		// even if we can FIT fullSections moved into the last portion, we might not HAVE them in our length
		fullSections = Math.min( fullSections, beams );

		// safeguard inches not requiring a beam and return value
		return fullSections * isBeamRequired( inches );

	}

	static double getBoardsInLength( double inches ){

		double plates = getPlatesInLength( inches );
		double studs = getStudsInLength( inches );
		return plates + studs;

	}

	static double getPlatesInLength( double inches ){

		// devide the length by 96 inches (8 feet) and round up
		// multiply by two because we're doing the top and bottom in one calculation
		return Math.ceil( inches / House.BOARD_LENGTH ) * 2;

	}

	static double getStudsInLength( double inches ){

		// calculate the studs across
		// round up to account for the last one
		double studs = Math.ceil( inches / STUDS_OFFSET );

		// make sure we add an end piece if we have a perfect multiple of 16
		double isNotPerfectWidth = Math.min( inches % STUDS_OFFSET, 1 );
		double perfectWidthExtension = (isNotPerfectWidth * -1) + 1;
		return studs + perfectWidthExtension;

	}

	static double getWallLengthOverMinimumRequiredBeforeBeam( double inches ){
		return Math.max(inches - BEAMS_REQUIRED_EVERY_INCHES, 0);
	}

}
