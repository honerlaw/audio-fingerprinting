package io.honerlaw.audio.fingerprint.util;

import java.io.File;

public class Directory {
	
	/**
	 * The temporary directory that will store audio files
	 */
	public static final String TEMP = "temp" + File.separator;
	
	/**
	 * The location of the wav files after they get converted using ffmpeg
	 */
	public static final String WAV = TEMP + "wavs" + File.separator;
	
	/**
	 * The directory containing the location for audio processing
	 */
	public static final String AUDIO = TEMP + "audio" + File.separator;
	
	/**
	 * The directory where files are held when they are pending processing
	 */
	public static final String PENDING = AUDIO + "pending" + File.separator;
	
	/**
	 * The directory where files are held after they have been processed
	 */
	public static final String COMPLETED = AUDIO + "completed" + File.separator;
	
	/**
	 * The directory where files are held during processing
	 */
	public static final String PROCESSING = AUDIO + "processing" + File.separator;

}
