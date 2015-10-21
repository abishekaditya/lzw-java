import java.lang.Long;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LayeredImage{
     public static void main(String[] args) {
     // Array of input images.
     BufferedImage[] input = new BufferedImage[7];
     // Load each input image.
     // Assume they are called "image_0.png", "image_1.png",
     // etc.
     for ( int i = 0; i < input.length; i++ ) {
     try {
     File f = new File( "image_" + (i+1) + ".bmp" );
     input[i] = ImageIO.read( f );
     }
     catch ( IOException x ) {
     // Complain if there is any problem loading
     // an input image.
     x.printStackTrace();
     }
     }
// Create the output image.
// It is the same size as the first
// input image.  I had to specify the type
// so it would keep it's transparency.
BufferedImage output = new BufferedImage(input[0].getWidth(),
input[0].getHeight(),BufferedImage.TYPE_INT_ARGB );
// Draw each of the input images onto the
// output image.
Graphics g = output.getGraphics();
for ( int i = 0; i < input.length; i++ ) {
g.drawImage( input[i], 0, 0, null );
}
// Create the output image file and write the
// output image to it.
File f = new File("image.png");
try {
ImageIO.write( output, "png", f );
}
catch ( IOException x ) {
// Complain if there was any problem writing
// the output file.
x.printStackTrace();
}
  }
}