package io.honerlaw.audio.fingerprint.hash.peak;

import io.honerlaw.audio.fingerprint.util.Hash;

/**
 * Represents a two peaks in a given audio file
 * with the ability to hash the two peaks
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public final class HashedPeak {
	
	private final Peak one;
	private final Peak two;
	private final int delta;
	
	public HashedPeak(Peak one, Peak two, int delta) {
		this.one = one;
		this.two = two;
		this.delta = delta;
	}
	
	public Peak getPeakOne() {
		return one;
	}
	
	public Peak getPeakTwo() {
		return two;
	}
	
	public int getDelta() {
		return delta;
	}
	
	public byte[] getHash() {
		return Hash.calculate(one.getFreq() + "|" + two.getFreq() + "|" + delta);
	}
	
	public String getHashAsHex() {
		return Hash.toHex(getHash());
	}
	
}