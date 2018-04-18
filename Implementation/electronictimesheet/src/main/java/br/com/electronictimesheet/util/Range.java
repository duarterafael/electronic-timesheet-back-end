package br.com.electronictimesheet.util;

public class Range<V> {

	private RestType value;

	private Long lowerBound;

	private Long upperBound;

	public Range(RestType r, Long lowerBound, Long upperBound) {
	        this.value = r;
	        this.lowerBound = lowerBound;
	        this.upperBound = upperBound;
	    }

	

	public boolean includes(Long givenValue) {
		return givenValue >= lowerBound && givenValue <= upperBound;

	}

	public RestType getValue() {
	        return value;
	    }

}
