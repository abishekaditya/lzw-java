package LZW_java;

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
        private int codeWordLength;
        private int mask;
        private long buf;        
        private int bufUsageBits;    
        private int bufUsageBytes;
        private int bufUsageSymbols;
        private int bufferedCodes;
        private boolean eof;

public Input(InputStream in, int codeWordLength) {
                this.in = in;
                this.codeWordLength = codeWordLength;

                bufferedCodes = 0;
                buf = 0;
                bufUsageBits = (int) LCM(8, codeWordLength);
                bufUsageBytes = bufUsageBits / 8;
                bufUsageSymbols = bufUsageBits / codeWordLength;
                mask = (1 << codeWordLength) - 1;
}

public int read() throws IOException {
                if ((bufferedCodes <= 0) && (!eof)) {
                        buf = 0;
                        for (int i = 0; i < bufUsageBytes; i++) {
                                int read = in.read();
                                if (-1 == read) {
                                        // read = 0;
                                        eof = true;
                                }
                                read = read & 255;
                                read <<= i * 8;
                                buf |= read;
                        }
                        bufferedCodes = bufUsageSymbols;
                }
                if (bufferedCodes > 0) {
                        int code = (int) (buf & mask);
                        buf >>= codeWordLength;
                        bufferedCodes--;
                        if (code < (1 << codeWordLength) - 1) {
                                return code;
                        } else {
                                return -1;
                        }
                } else {
                        return -1;
                }
}

public void close() throws IOException {
                in.close();
        }
}