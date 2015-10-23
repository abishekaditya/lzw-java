package src;

import java.io.IOException;
import java.io.OutputStream;

public class Output {

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
	return Math.abs((a * b) / GCD(a, b));
}

private OutputStream out;
int codedLen;
private int mask;
private long buf;
private int written;
private int max_bit_codes;
private int max_codes;
private int total_codes;
		
public Output(OutputStream out, int codedLen){
	this.out = out;
	this.codedLen = codedLen;

	written = 0;
	buf = 0;
	max_bit_codes = (int) LCM(8, codedLen);
	max_codes = max_bit_codes / 8;
	total_codes = max_bit_codes / codedLen;
	mask = (1 << codedLen) - 1;
}

public void write(int code) throws IOException {

code = (code & mask) << ((written) * codedLen);
buf |= code;
written++;
	if (written >= total_codes) {
			for (int i = 0; i < max_codes; i++) {
					out.write((int) (buf & 255));
					buf >>= 8;
			}
			written = 0;
			buf = 0;
	}
}

public void flush() throws IOException {
	while ((written < total_codes) && (written != 0)) {
		write(-1);
		}
	out.flush();
}

public void close() throws IOException {
	out.close();
}
}