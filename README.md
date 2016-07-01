# Audio Fingerprinting Library
A simple java library for fingerprinting audio files.

### To install and use

1. Install ffmpeg and set `FFMPEG_PATH` to the ffmpeg executable.
2. Build the project using `mvn clean install assembly:single`
3. Add the jar to the local maven repository `mvn install:install-file -Dfile=target/audio-fingerprint-1.0.0-jar-with-dependencies.jar -DgroupId=io.honerlaw -DartifactId=audio-fingerprint -Dversion=1.0.0 -Dpackaging=jar`
4. Add the dependency to your pom.xml
```
<dependency>
	<groupId>io.honerlaw</groupId>
	<artifactId>audio-fingerprint</artifactId>
	<version>1.0.0</version>
</dependency>
```
