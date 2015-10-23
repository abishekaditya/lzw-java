package src;

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
private int codedLen = 12; //can be changed, but will have to change the mask too
private Map<ByteArray, Integer> codedTable; //hashmap of code:value pairs
private List<ByteArray> decodedTable;//array/list of decoded values from hash map


public void compress(InputStream in, OutputStream out) throws IOException {
init();

int code = 256;//initial size of compress table. each byte is mapped to itself

InputStream bufferedIn = new BufferedInputStream(in);
Output comp_out = new Output(new BufferedOutputStream(out),
				codedLen);

int _1stByte = bufferedIn.read();
ByteArray w = new ByteArray((byte) _1stByte);
int K;

while ((K = bufferedIn.read()) != -1) {
		ByteArray wK = new ByteArray(w).append((byte) K);
		if (codedTable.containsKey(wK)) {
				w = wK;
		} else {
				comp_out.write(codedTable.get(w));
				if (code < (1 << codedLen) - 1) {
						codedTable.put(wK, code++);
				}
				w = new ByteArray((byte) K);
		}
}
comp_out.write(codedTable.get(w));
comp_out.flush();
comp_out.close();
}

public void decompress(InputStream in, OutputStream out) throws IOException {
init();

Input comp_in = new Input(new BufferedInputStream(in),
				codedLen);
OutputStream bufferedOut = new BufferedOutputStream(out);

int old_code = comp_in.read();
bufferedOut.write(old_code);
int character = old_code;
int new_code;
while ((new_code = comp_in.read()) != -1) {
		ByteArray string;
		if (new_code >= decodedTable.size()) {
				string = new ByteArray(decodedTable.get(old_code));
				string.append((byte) character);
		} else {
				string = decodedTable.get(new_code);
		}
		for (int i = 0; i < string.size(); i++) {
				bufferedOut.write(string.get(i));
		}
		character = string.get(0);
		decodedTable.add(new ByteArray(decodedTable.get(old_code))
						.append((byte) character));
		old_code = new_code;
}

bufferedOut.flush();
bufferedOut.close();
}

private void init() {
codedTable = new HashMap<ByteArray, Integer>();
decodedTable = new ArrayList<ByteArray>();
for (int i = 0; i < 256; i++) {
		codedTable.put(new ByteArray((byte) i), i);
		decodedTable.add(new ByteArray((byte) i));
	}
}
}