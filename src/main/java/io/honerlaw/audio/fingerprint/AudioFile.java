package io.honerlaw.audio.fingerprint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.honerlaw.audio.fingerprint.hash.FingerPrint;
import io.honerlaw.audio.fingerprint.hash.Spectrogram;
import io.honerlaw.audio.fingerprint.util.Directory;
import io.honerlaw.audio.fingerprint.util.Hash;

/**
 * Represents an audio file. Converts the given file into
 * a wav file and then calculates the spectrogram and fingerprint
 * for the wav file.
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class AudioFile {
	
	/**
	 * The hash calculated from the content of the original audio file
	 */
	private final String fileHashString;
	
	/**
	 * The audio file that we are working with
	 */
	private final File file;
	
	/**
	 * The path to the converted wav file
	 */
	private final String wavFilePath;
	
	/**
	 * The audio file buffer for the given file
	 */
	private final AudioFileBuffer buffer;
	
	/**
	 * The audio file header information (wav header information)
	 */
	private final AudioFileHeader header;
	
	/**
	 * The fingerprint utility class
	 */
	private final FingerPrint fingerPrint;
	
	/**
	 * The spectrogram utility class
	 */
	private final Spectrogram spectrogram;

	/**
	 * Locate the file, generate a wav file name and then tries to convert the given
	 * file into a WAV file and stores the file with the WAV file name as well as 
	 * opening an input stream for the WAV file so we can read it
	 * 
	 * @param file The file name
	 * @param wavFilePath The path to where the wav file is stored
	 * 
	 * @throws Exception
	 */
	public AudioFile(File file) throws Exception {
		
		// calculate the hash for the file name
		this.fileHashString = Hash.toHex(Hash.calculate(new FileInputStream(file)));
		
		// try and find and load the file
		this.file = file;
		if(!this.file.exists()) {
			throw new FileNotFoundException();
		}
		this.wavFilePath = Directory.WAV + getFileHashString() + ".wav";
		
		// convert the audio file to a wav file
		this.convert();
		
		// load the file into the audio file buffer
		this.buffer = new AudioFileBuffer(this);
		
		// read the header information from the audio file
		this.header = new AudioFileHeader(this.buffer);
		
		// initialize the fingerprint utility class
		this.fingerPrint = new FingerPrint(this);
		
		// initialize the spectrogram utility class
		this.spectrogram = new Spectrogram(this);
	}
	
	/**
	 * Converts the audio file to a 16 bit mono WAV file
	 * 
	 * @throws Exception
	 */
	public void convert() throws Exception {
		
		// if the wav already exists we do not need to convert it again
		if(Files.exists(Paths.get(getWAVFilePath()))) {
			return;
		}
		
		// make the directory if it doesn't exist
		File wavsDir = new File(Directory.WAV);
		if(!wavsDir.exists()) {
			wavsDir.mkdirs();
		}
		
		// convert the file to a 16 bit mono .wav file
		Process process = Runtime.getRuntime().exec(System.getenv("FFMPEG_PATH") + " -i " + this.file.getAbsolutePath() + " " + getWAVFilePath());
		if(process.waitFor() != 0) {
			byte[] data = new byte[process.getErrorStream().available()];
			process.getErrorStream().read(data);
			throw new Exception("Failed to convert audio file: " + new String(data));
		}
	}
	
	/**
	 * 
	 * @return The file hash as a string
	 */
	public String getFileHashString() {
		return fileHashString;
	}
	
	/**
	 * 
	 * @return The WAV file path
	 */
	public String getWAVFilePath() {
		return wavFilePath;
	}
	
	/**
	 * 
	 * @return The header information for the given WAV file
	 */
	public AudioFileHeader getHeader() {
		return header;
	}
	
	/**
	 * 
	 * @return The buffer containing the WAV file contents
	 */
	public AudioFileBuffer getBuffer() {
		return buffer;
	}
	
	/**
	 * 
	 * @return The generated fingerprint for the WAV file
	 */
	public FingerPrint getFingerPrint() {
		return fingerPrint;
	}
	
	/**
	 * 
	 * @return The generated spectrogram for the WAV file
	 */
	public Spectrogram getSpectrogram() {
		return spectrogram;
	}
	
	/**
	 * Calculates the sample amplitudes from the WAV file data
	 * 
	 * @return The sample amplitudes of the wav file
	 */
	public short[] getSampleAmplitudes() {
		int bytePerSample = getHeader().getBitsPerSample() / 8;
		int samples = getBuffer().size() / bytePerSample;
		short[] amplitudes = new short[samples];
		int position = 0;
		for(int i = 0; i < samples; ++i) {
			short amplitude = 0;
			for(int byteNumber = 0; byteNumber < bytePerSample; byteNumber++) {
				amplitude |= (short) ((getBuffer().get(position++) & 0xFF) << (byteNumber * 8));
			}
			amplitudes[i] = amplitude;
		}
		return amplitudes;
	}
	
}
