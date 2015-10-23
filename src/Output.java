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
int codeWordLength;
private int mask; // mask for clipping ASCII values from code
private long buf; //internal buffer for getting output
private int written; //number of codes in the output stream       
private int buf_used_bits;//number of bits used in buffer for storing code        
private int buf_used_bytes;       
private int total_codes;// number of codes that can be stored
		
public Output(OutputStream out, int codeWordLength){
	this.out = out;
	this.codeWordLength = codeWordLength;

	written = 0;
	buf = 0;
	buf_used_bits = (int) LCM(8, codeWordLength);
	buf_used_bytes = buf_used_bits / 8;
	total_codes = buf_used_bits / codeWordLength;
	mask = (1 << codeWordLength) - 1;
}

public void write(int code) throws IOException {

code = (code & mask) << ((written) * codeWordLength);
buf |= code;
written++;
	if (written >= total_codes) {
			for (int i = 0; i < buf_used_bytes; i++) {
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