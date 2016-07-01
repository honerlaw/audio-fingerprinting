package io.honerlaw.audio.fingerprint.hash;

import java.util.ArrayList;
import java.util.List;

import io.honerlaw.audio.fingerprint.AudioFile;
import io.honerlaw.audio.fingerprint.hash.peak.HashedPeak;
import io.honerlaw.audio.fingerprint.hash.peak.Peak;

/**
 * Calculates the fingerprint of the audio file given
 * the spectrogram for the audio file
 * 
 * http://www.ee.columbia.edu/~dpwe/papers/Wang03-shazam.pdf
 * http://mtg.upf.edu/files/publications/MMSP-2002-pcano.pdf
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class FingerPrint {
	
	/**
	 * The size of the neighborhood to search for peaks in
	 */
	private static final int PEAK_NEIGHBORHOOD = 20;
	
	/**
	 * The number of peaks to look through when generating a hash
	 */
	private static final int FAN_VALUE = 15;
	
	/**
	 * The audio file that we are trying to fingerprint
	 */
	private final AudioFile audioFile;
	
	/**
	 * The generated hashes for the given audio file
	 */
	private HashedPeak[] hashes;
	
	/**
	 * Create a new class that can fingerprint the given audio file
	 * 
	 * @param audioFile The audio file to fingerprint 
	 */
	public FingerPrint(AudioFile audioFile) {
		this.audioFile = audioFile;
	}
	
	/**
	 * Calculates all of the fingerprint hashes for the given Audiofile
	 * 
	 * @param audioFile The audio file to calculate the fingerprint hashes for
	 * 
	 * @return An array of fingerprint hashes
	 */
	public HashedPeak[] getHashes() {
		if(hashes != null) {
			return hashes;
		}
		
		// get the spectrogram data
		double[][] spectrogram = audioFile.getSpectrogram().getData();
		
		// get the peaks in the spectrogram
		List<Peak> peaks = new ArrayList<Peak>();
		for(int i = 0; i < spectrogram.length; ++i) {
			for(int j = 0; j < spectrogram[0].length; ++j) {
				if(isPeakAt(i, j, spectrogram, PEAK_NEIGHBORHOOD)) {
					peaks.add(new Peak(i, j));
				}
			}
		}

		// store the generated hashes
		List<HashedPeak> hashes = new ArrayList<HashedPeak>(); //new HashedPeak[peaks.size() * FAN_VALUE];
		
		// generate a hash for the given peaks
		for(int i = 0; i < peaks.size(); ++i) {
			
			// loop through the current peak and the next 15 peaks
			for(int j = 0; j < FAN_VALUE; ++j) {
				if(i + j >= peaks.size()) {
					break;
				}
				
				// get the two peaks
				Peak one = peaks.get(i);
				Peak two = peaks.get(i + j);
				
				// calculate the time between them
				int delta = two.getTime() - one.getTime();
				
				// if they are within a reasonable time distance, calculate the hash
				if(delta >= 0 && delta <= 200) {
					hashes.add(new HashedPeak(one, two, delta));
				}
			}
		}
		this.hashes = hashes.toArray(new HashedPeak[hashes.size()]);
		return this.hashes;
	}
	
	/**
	 * Checks whether a given point in the spectrogram is a local maxima for the given 
	 * neighborhood size
	 * 
	 * @param x The time position in the spectrogram
	 * @param y The frequency position in the spectrogram
	 * @param spectrogram The spectrogram data
	 * @param neighborhood The size of the neighborhood
	 * 
	 * @return Whether the given point is a local maxima or not
	 */
	private boolean isPeakAt(int x, int y, double[][] spectrogram, int neighborhood) {
		double amplitude = spectrogram[x][y];
		int minX = x - neighborhood < 0 ? 0 : x - neighborhood;
		int maxX = x + neighborhood >= spectrogram.length ? spectrogram.length - 1 : x + neighborhood;
		int minY = y - neighborhood < 0 ? 0 : y - neighborhood;
		int maxY = y + neighborhood >= spectrogram[0].length ? spectrogram[0].length - 1 : y + neighborhood;
		for(int k = minX; k < maxX; ++k) {
			for(int l = minY; l < maxY; ++l) {
				if(spectrogram[k][l] > amplitude) {
					return false;
				}
			}
		}
		return true;
	}

}