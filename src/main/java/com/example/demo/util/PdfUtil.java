package com.example.demo.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class PdfUtil {
    public static void  toImage(String pdfPath,String imgPath) {
             try {

                     PDDocument doc = PDDocument
                             .load(pdfPath);
                     int pageCount = doc.getPageCount();
                     System.out.println(pageCount);
                     List pages = doc.getDocumentCatalog().getAllPages();
                     for (int i = 0; i < pages.size(); i++) {
                             PDPage page = (PDPage) pages.get(i);
                             BufferedImage image = page.convertToImage();
                             Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
                             ImageWriter writer = (ImageWriter) iter.next();
                             File outFile = new File(imgPath+ i + ".jpg");
                             FileOutputStream out = new FileOutputStream(outFile);
                             ImageOutputStream outImage = ImageIO
                                     .createImageOutputStream(out);
                             writer.setOutput(outImage);
                             writer.write(new IIOImage(image, null, null));
                         }
                     doc.close();
                     System.out.println("over");
                 } catch (FileNotFoundException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
         }
}
