package io.honerlaw.audio.fingerprint;

/**
 * Parses the header information from the audio file buffer
 * 
 * Information regarding the header file format can be found here
 * http://soundfile.sapp.org/doc/WaveFormat/
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class AudioFileHeader {

	private final String chunkId;	// 4 bytes
	private final int chunkSize; // unsigned 4 bytes, little endian
	private final String format;	// 4 bytes
	private final String subChunkOneId;	// 4 bytes
	private final int subChunkOneSize; // unsigned 4 bytes, little endian
	private final int audioFormat; // unsigned 2 bytes, little endian
	private final int channels; // unsigned 2 bytes, little endian
	private final int sampleRate; // unsigned 4 bytes, little endian
	private final int byteRate; // unsigned 4 bytes, little endian
	private final int blockAlign; // unsigned 2 bytes, little endian
	private final int bitsPerSample; // unsigned 2 bytes, little endian
	private final String subChunkTwoId;	// 4 bytes
	private final int subChunkTwoSize; // unsigned 4 bytes, little endian

	public AudioFileHeader(AudioFileBuffer buffer) {
		this.chunkId = buffer.getString();
		this.chunkSize = buffer.getInt();
		this.format = buffer.getString();
		this.subChunkOneId = buffer.getString();
		this.subChunkOneSize = buffer.getInt();
		this.audioFormat = buffer.getShort();
		this.channels = buffer.getShort();
		this.sampleRate = buffer.getInt();
		this.byteRate = buffer.getInt();
		this.blockAlign = buffer.getShort();
		this.bitsPerSample = buffer.getShort();
		this.subChunkTwoId = buffer.getString();
		this.subChunkTwoSize = buffer.getInt();
	}
	
	public String getChunkId() {
		return chunkId;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public String getFormat() {
		return format;
	}

	public String getSubChunkOneId() {
		return subChunkOneId;
	}

	public int getSubChunkOneSize() {
		return subChunkOneSize;
	}

	public int getAudioFormat() {
		return audioFormat;
	}

	public int getChannels() {
		return channels;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getByteRate() {
		return byteRate;
	}

	public int getBlockAlign() {
		return blockAlign;
	}

	public int getBitsPerSample() {
		return bitsPerSample;
	}

	public String getSubChunkTwoId() {
		return subChunkTwoId;
	}

	public int getSubChunkTwoSize() {
		return subChunkTwoSize;
	}

}
