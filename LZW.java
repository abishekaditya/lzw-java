package LZW_java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW {
        private int codeWordLength = 12;
        private Map<ByteArray, Integer> codeTable;
        private List<ByteArray> decodeTable;

        
        public void compress(InputStream in, OutputStream out) throws IOException {
                init();

                int code = 256;

                InputStream bufferedIn = new BufferedInputStream(in);
                Output compressedOutput = new Output(new BufferedOutputStream(out),
                                codeWordLength);

                int firstByte = bufferedIn.read();
                ByteArray w = new ByteArray((byte) firstByte);
                int K;

                while ((K = bufferedIn.read()) != -1) {
                        ByteArray wK = new ByteArray(w).append((byte) K);
                        if (codeTable.containsKey(wK)) {
                                w = wK;
                        } else {
                                compressedOutput.write(codeTable.get(w));
                                if (code < (1 << codeWordLength) - 1) {
                                        codeTable.put(wK, code++);
                                }
                                w = new ByteArray((byte) K);
                        }
                }
                compressedOutput.write(codeTable.get(w));
                compressedOutput.flush();
                compressedOutput.close();
        }

        public void decompress(InputStream in, OutputStream out) throws IOException {
                init();

                Input compressedInput = new Input(new BufferedInputStream(in),
                                codeWordLength);
                OutputStream bufferedOut = new BufferedOutputStream(out);

                int oldCode = compressedInput.read();
                bufferedOut.write(oldCode);
                int character = oldCode;
                int newCode;
                while ((newCode = compressedInput.read()) != -1) {
                        ByteArray string;
                        if (newCode >= decodeTable.size()) {
                                string = new ByteArray(decodeTable.get(oldCode));
                                string.append((byte) character);
                        } else {
                                string = decodeTable.get(newCode);
                        }
                        for (int i = 0; i < string.size(); i++) {
                                bufferedOut.write(string.get(i));
                        }
                        character = string.get(0);
                        decodeTable.add(new ByteArray(decodeTable.get(oldCode))
                                        .append((byte) character));
                        oldCode = newCode;
                }

                bufferedOut.flush();
                bufferedOut.close();
        }

        private void init() {
                codeTable = new HashMap<ByteArray, Integer>();
                decodeTable = new ArrayList<ByteArray>();
                for (int i = 0; i < 256; i++) {
                        codeTable.put(new ByteArray((byte) i), i);
                        decodeTable.add(new ByteArray((byte) i));
                }
        }
}
