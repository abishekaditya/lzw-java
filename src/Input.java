package src;

import java.io.IOException;
import java.io.InputStream;


public class Input {

public static long GCD(long a, long b) {
	long tmp;

	while (b != 0) {
			tmp = b;
			b = a % b;
			a = tmp;
	}
	return Math.abs(a);
}


public static long LCM(long a, long b) {
   return (Math.abs((a*b)/GCD(a, b)));
}

private InputStream in;
private int codedLen;
private int mask;
private long buf;        
private int buf_used_bits;
private int buf_used_bytes;
private int total_codes;
private int used_codes;
private boolean eof;

public Input(InputStream in, int codedLen) {
this.in = in;
this.codedLen = codedLen;

used_codes = 0;
buf = 0;
buf_used_bits = (int) LCM(8, codedLen);
buf_used_bytes = buf_used_bits / 8;
total_codes = buf_used_bits / codedLen;
mask = (1 << codedLen) - 1;
}

public int read() throws IOException {
	if ((used_codes <= 0) && (!eof)) {
			buf = 0;
			for (int i = 0; i < buf_used_bytes; i++) {
					int read = in.read();
					if (-1 == read) {
							// read = 0;
							eof = true;
					}
					read = read & 255;
					read <<= i * 8;
					buf |= read;
			}
			used_codes = total_codes;
	}
	if (used_codes > 0) {
			int code = (int) (buf & mask);
			buf >>= codedLen;
			used_codes--;
			if (code < (1 << codedLen) - 1) {
					return code;
			} else {
					return -1;
			}
	}
	else {
			return -1;
	}
}

public void close() throws IOException {
				in.close();
		}
}