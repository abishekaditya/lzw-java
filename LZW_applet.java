package LZW_java;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* <applet code="LZW_applet" width=450 height=400>
</applet>
*/

public class LZW_applet extends Applet implements ActionListener {

public void init(){
	Label uncom_label = new Label("Uncompressed File:", Label.LEFT);
	Label com_label = new Label("Compressed File:", Label.LEFT);
	Button compress = new Button("compress");
	Button decompress = new Button("decompress");
	TextField uncom = new TextField(20);
	TextField com = new TextField(20);
	uncom.setEditable(true);
	com.setEditable(true);

	add(uncom_label);
	add(uncom);
	add(compress);
	add(com_label);
	add(com);
	add(decompress);

	compress.addActionListener(this);
	decompress.addActionListener(this);
	com.addActionListener(this);
	uncom.addActionListener(this);
}

public void actionPerformed(ActionEvent ae){
	String str= ae.getActionCommand();

	if (str.equals("compress")) {
		FileInputStream in = new FileInputStream(uncom.getText());
        FileOutputStream out = new FileOutputStream(com.getText());
        LZW lzw = new LZW();
		lzw.compress(in, out);		
	}
	else if (str.equals("decompress")) {
		FileInputStream in = new FileInputStream(com.getText());
		FileOutputStream out = new FileOutputStream(uncom.getText());
		LZW lzw = new LZW();
		lzw.decompress(in, out);
	}
	repaint();
}

public void start(){
}

public void stop(){
}

public void destroy(){
}

public void paint(Graphics g){

}
	
}