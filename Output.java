package LZW_java;

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
        private int mask;        
        private long buf;  
        private int written;       
        private int bufUsageBits;        
        private int bufUsageBytes;       
        private int bufUsageSymbols;
        
        public Output(OutputStream out, int codeWordLength){
                this.out = out;
                this.codeWordLength = codeWordLength;

                written = 0;
                buf = 0;
                bufUsageBits = (int) LCM(8, codeWordLength);
                bufUsageBytes = bufUsageBits / 8;
                bufUsageSymbols = bufUsageBits / codeWordLength;
                mask = (1 << codeWordLength) - 1;
        }

        public void write(int code) throws IOException {
                
                code = (code & mask) << ((written) * codeWordLength);
                buf |= code;
                written++;
                if (written >= bufUsageSymbols) {
                        for (int i = 0; i < bufUsageBytes; i++) {
                                out.write((int) (buf & 255));
                                buf >>= 8;
                        }
                        written = 0;
                        buf = 0;
                }
        }

        public void flush() throws IOException {
                while ((written < bufUsageSymbols) && (written != 0)) {
                        write(-1);
                }
                out.flush();
        }

        public void close() throws IOException {
                out.close();
        }
}