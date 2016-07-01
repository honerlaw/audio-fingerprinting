package io.honerlaw.audio.fingerprint.util;

import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;

import junit.framework.TestCase;

public class HashTest extends TestCase {
	
	public void testCalculateInputStream() throws Exception {
		InputStream inOne = getInputStreamMock(new byte[] { 1, 2, 3, 4 });
		InputStream inTwo = getInputStreamMock(new byte[] { 1, 2, 3, 4 });
		InputStream inThree = getInputStreamMock(new byte[] { 4, 3, 2, 1 });
		byte[] one = Hash.calculate(inOne);
		byte[] two = Hash.calculate(inTwo);
		byte[] three = Hash.calculate(inThree);
		assertTrue(Arrays.equals(one, two));
		assertFalse(Arrays.equals(one, three));
		assertFalse(Arrays.equals(two, three));
	}
	
	private InputStream getInputStreamMock(byte[] data) throws Exception {
		InputStream in = mock(InputStream.class);
		when(in.available()).thenReturn(data.length);
		when(in.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invo -> {
			byte[] buf = invo.getArgumentAt(0, byte[].class);
			int start = invo.getArgumentAt(1, int.class);
			int length = invo.getArgumentAt(2, int.class);
			assertEquals(start, 0);
			assertEquals(length, data.length);
			for(int i = start; i < length; ++i) {
				buf[i] = data[i];
			}
			return data.length;
		});
		return in;
	}
	
	public void testCalculateString() {
		byte[] one = Hash.calculate("foo");
		byte[] two = Hash.calculate("foo");
		byte[] three = Hash.calculate("bar");
		assertTrue(Arrays.equals(one, two));
		assertFalse(Arrays.equals(one, three));
		assertFalse(Arrays.equals(two, three));
	}

}
