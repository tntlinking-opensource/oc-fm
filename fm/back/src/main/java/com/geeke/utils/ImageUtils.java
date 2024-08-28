package com.geeke.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
/**
 * Created by Administrator on 2018/1/19 0019.
 */
public class ImageUtils {
    public static void main(String[] args) throws Exception {
        String [] imgs = new String[]{"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg",
                "D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg"
                ,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg",
                "D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg",
                "D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg"
                ,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg",
                "D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg"
                ,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg",
                "D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg"
                ,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\asset_code.jpeg"
               };
    //    ImageUtils.combinePicture(imgs,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\\\4.jpg",2);
        ImageUtils.doSpecialCombine(imgs,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\all.jpeg",2,"D:\\data\\asset\\qrcode\\blank\\blank.jpg");

        ArrayList<String> list = new ArrayList<>();
        list.add("D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\all.jpeg");



      ImageUtils.imageToPdf(list,"D:\\data\\asset\\qrcode\\XZ0010\\b.jpeg\\XZ0010\\all.pdf");
//        File [] files = new File[]{new File(list.get(0)),new File(list.get(1))};
//        ImageUtils.imagesToPdf("D:\\data\\asset\\qrcode\\XZ0010\\test.pdf",files);
    }

    /**
     * @param imgs
     * @param outputFile
     * @param length
     * @return void
     * @date 2020-05-24
     * @Description 将图片按照指定的长度垂直拼接后再水平拼接
     * @Author goblin
     */
    public static void doSpecialCombine(String[]imgs,String outputFile,int length,String blankPath) throws Exception{

        List<String> imgsList = new ArrayList<>(Arrays.asList(imgs));
        List<List<String>> lists = ListUtils.subListByLength(imgsList, length);
        List<String> tmpImagePaths = new ArrayList<>();
        String fullPath = FilenameUtils.getFullPath(outputFile);
        //将所有图片两个水平拼接
        lists.forEach(a->{
            // 如果一行只有一个，则拼接一张空白图片
            /**
             * 3.排版修改2021/9/17 BY LJ (三张一排)
             */
            if(a.size() == 1 ){
                a.add(blankPath);
                a.add(blankPath);
            }else if (a.size() == 2 ){
                a.add(blankPath);
            }


            String [] tmpArray = new String[a.size()];
            try {
                String tmpImagePath = fullPath+UUID.randomUUID().toString()+".jpg";
                tmpImagePaths.add(tmpImagePath);

                combinePicture(a.toArray(tmpArray),tmpImagePath,2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 将所有水平拼接的图片垂直拼接
        String[] realPath =new String[tmpImagePaths.size()];
        combinePicture(tmpImagePaths.toArray(realPath),outputFile,1);
        // 删除临时图片
        for(String tmpImagePath:tmpImagePaths){
            File file = new File(tmpImagePath);
            file.deleteOnExit();
        }

    }

    /**
     * 垂直拼接
     * @param imgs  需要拼接的图片名字数组
     * @param outFilePathName 拼接后的文件名
     * @param type 1：垂直拼接  2：水平拼接
     * @return
     * @throws Exception
     */
    public static boolean combinePicture(String[] imgs, String outFilePathName,int type) throws Exception {
        int len=imgs.length;
        if (len > 0) {
                int widths[]=new int[len];
                int heights[]=new int[len];
                int imgRgbs[][]=new int[len][];
                for (int i = 0; i < imgs.length; i++) {
                    File file = new File(imgs[i]);
                    File newFile = resizeImage1(file,"temp"+i,type);
                    BufferedImage imageFirst = ImageIO.read(newFile);
                    int width = imageFirst.getWidth();// 图片宽度
                    int height = imageFirst.getHeight();// 图片高度
                    int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
                    imageArrayFirst = imageFirst.getRGB(0, 0, width, height, imageArrayFirst, 0, width);
                    widths[i]=width;
                    heights[i]=height;
                    imgRgbs[i]=imageArrayFirst;
                }
            BufferedImage imageResult;
            if(type==1) {
                /**
                 * 4.排版修改2021/9/17 BY LJ (+100起步，每行+118，刷白色底色)
                 */
                int heightSum = 0+100;
                for (int i = 0; i < heights.length; i++) {
                    heightSum += (heights[i]+118);
                }
                imageResult = new BufferedImage(widths[0], heightSum, BufferedImage.TYPE_INT_RGB);
                //底色洗白
                Graphics2D dg = imageResult.createGraphics();
                //设置笔刷白色
                dg.setColor(Color.WHITE);
                //填充整个屏幕
                dg.fillRect(0,0,widths[0],heightSum);
                dg.dispose();
                int start_Y = 0+100;
                for (int i = 0; i < imgRgbs.length; i++) {
                    imageResult.setRGB(0, start_Y, widths[i], heights[i], imgRgbs[i], 0, widths[i]);
                    start_Y += (heights[i]+118);
                }
            }else{
                int widthSum = 0;
                for (int i = 0; i < widths.length; i++) {
                    widthSum += widths[i];
                }
                imageResult = new BufferedImage(widthSum, heights[0], BufferedImage.TYPE_INT_RGB);
                int start_X = 0;
                for (int i = 0; i < imgRgbs.length; i++) {
                    imageResult.setRGB(start_X, 0, widths[i], heights[0], imgRgbs[i], 0, widths[i]);
                    start_X += widths[i];
                }
            }
//                File outFile=new File(outFilePathName);
//                ImageIO.write(imageResult, "jpg", outFile);// 写图片

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Needed see javadoc
            param.setCompressionQuality(1.0F); // Highest quality
            File file=new File(outFilePathName);
            FileImageOutputStream out = new FileImageOutputStream(file);
            writer.setOutput(out);
            writer.write(null, new IIOImage(imageResult, null, null), param);
            out.close();
            writer.dispose();
            return true;
        } else {
            return false;
        }
    }


    /**
     * @Description:小图片贴到大图片形成一张图(合成)
     * @author:liuyc
     * @time:2016年5月27日 下午5:51:20
     */
    public static final void overlapImage(String bigPath, String smallPath, String outFile) {
        try {
            BufferedImage big = ImageIO.read(new File(bigPath));
            BufferedImage small = ImageIO.read(new File(smallPath));
            Graphics2D g = big.createGraphics();
            int x = (big.getWidth() - small.getWidth()) / 2;
            int y = (big.getHeight() - small.getHeight()) / 2;
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 将图片改变到指定的宽度，高度自适应改变
     * @param file
     * @return
     * @throws IOException
     */
    public static File resizeImage1(File file,String newFileName,int type) throws IOException {
        FileInputStream fis=new FileInputStream(file);
        FileOutputStream fos=new FileOutputStream(new File(newFileName));
        resizeImage(fis, fos, 1980, "jpg",type);
        File newFile=new File(newFileName);
        return newFile;
    }
    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     * @param is 上传的图片的输入流
     * @param os 改变了图片的大小后，把图片的流输出到目标OutputStream
     * @param size 新图片的宽
     * @param format 新图片的格式
     * @throws IOException
     */
    public static OutputStream resizeImage(InputStream is, OutputStream os, int size, String format,int type) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        double percent=0d;
        if(type==1){
            percent = size/width;
        }else{
            percent = size/height;
        }
        int newWidth = (int)(width * percent);
        int newHeight = (int)(height * percent);
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
        ImageIO.write(image, format, os);
        os.flush();
        is.close();
        os.close();
      //  ByteArrayOutputStream b = (ByteArrayOutputStream) os;
        return os;
    }

    /**
     * @param imageUrllist
     * @param mOutputPdfFileName
     * @return java.io.File
     * @date 2020-05-21
     * @Description 将图片转为pdf
     * @Author goblin
     */
    public static File imageToPdf(ArrayList<String> imageUrllist, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20); //new一个pdf文档
        try {
            File file = new File(mOutputPdfFileName);
            PdfWriter.getInstance(doc, new FileOutputStream(file)); //pdf写入
            doc.open();//打开文档
            for (int i = 0; i < imageUrllist.size(); i++) {  //循环图片List，将图片加入到pdf中
                doc.newPage();  //在pdf创建一页
                Image png1 = Image.getInstance(imageUrllist.get(i)); //通过文件路径获取image
                float heigth = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent2(heigth, width);
                png1.setAlignment(Image.MIDDLE);
                png1.scalePercent(percent);// 表示是原来图像的比例;
                doc.add(png1);
            }
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        waitForWirtenCompleted(mOutputPdfFile);
        //反回文件输出流
        return mOutputPdfFile;
    }

    private static void waitForWirtenCompleted(File file) {
        if (!file.exists()){
            return;
        }
        long old_length;
        do {
            old_length = file.length();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (old_length != file.length());
    }
    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }
    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }
//    public void imgOfPdf(String filepath,HttpServletRequest request) {
//        boolean result = false;
//        try {
//            ArrayList<String> imageUrllist = new ArrayList<String>(); //图片list集合
//            imageUrllist.add(request.getSession()
//                    .getServletContext().getRealPath("\\" + filepath));  //添加图片文件路径
//            String fles = filepath.substring(0, filepath.lastIndexOf("."));
//            String pdfUrl = request.getSession().getServletContext()
//                    .getRealPath("\\" +fles+".pdf");  //输出pdf文件路径
//            result = true;
//            if (result == true) {
//                File file = Pdf(imageUrllist, pdfUrl);//生成pdf
//                file.createNewFile();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static void imagesToPdf(String outPdfFilepath, File[] imageFiles) throws Exception {


        File file = new File(outPdfFilepath);
        // 第一步：创建一个document对象。
        Document document = new Document();
        document.setMargins(0, 0, 0, 0);
        // 第二步：
        // 创建一个PdfWriter实例，
        PdfWriter.getInstance(document, new FileOutputStream(file));
        // 第三步：打开文档。
        document.open();
        // 第四步：在文档中增加图片。
        int len = imageFiles.length;

        for (int i = 0; i < len; i++) {
            if (imageFiles[i].getName().toLowerCase().endsWith(".bmp")
                    || imageFiles[i].getName().toLowerCase().endsWith(".jpg")
                    || imageFiles[i].getName().toLowerCase().endsWith(".jpeg")
                    || imageFiles[i].getName().toLowerCase().endsWith(".gif")
                    || imageFiles[i].getName().toLowerCase().endsWith(".png")) {
                String temp = imageFiles[i].getAbsolutePath();

                Image img = Image.getInstance(temp);
                img.setAlignment(Image.ALIGN_CENTER);
                img.scaleAbsolute(597, 844);// 直接设定显示尺寸
                // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                //document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                document.setPageSize(new Rectangle(597, 844));
                document.newPage();
                document.add(img);
            }
        }
        // 第五步：关闭文档。
        document.close();

    }

    /**
     * 缩放图片(压缩图片质量，改变图片尺寸)
     * 若原图宽度小于新宽度，则宽度不变！
     * @param originalFile 原图片路径地址
     * @param resizedFile 压缩后输出路径地址
     * @param maxWidth 最大宽度
     * @param maxHeight 最大高度
     *
     * @param quality 图片质量参数 0.7f 相当于70%质量
     */
    public static void imageResize(File originalFile, File resizedFile,
                                   int maxWidth,int maxHeight, float quality) throws IOException {

        if (quality > 1) {
            throw new IllegalArgumentException(
                    "图片质量需设置在0.1-1范围");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        java.awt.Image i = ii.getImage();
        java.awt.Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        int newWidth = maxWidth;
        if(iWidth < maxWidth){
            newWidth = iWidth;
        }


        if (iWidth >= iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, java.awt.Image.SCALE_SMOOTH);
        }



        int newHeight = maxHeight;
        if(iHeight < maxHeight){
            newHeight = iHeight;
        }

        if(resizedImage==null && iHeight >= iWidth){
            resizedImage = i.getScaledInstance((newHeight * iWidth) / iHeight,
                    newHeight, java.awt.Image.SCALE_SMOOTH);
        }

        //此代码确保加载图像中的所有像素
        java.awt.Image temp = new ImageIcon(resizedImage).getImage();

        //创建缓冲图像
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        //将图像复制到缓冲图像
        Graphics g = bufferedImage.createGraphics();

        //清除背景并绘制图像。
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();


        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        String dstName = resizedFile.getName();
        String formatName = dstName.substring(dstName.lastIndexOf(".") + 1);
        //FileOutputStream out = new FileOutputStream(dstName);
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //encoder.encode(dstImage);
        ImageIO.write(bufferedImage, /*"GIF"*/ formatName /* format desired */ , new File(dstName) /* target */ );
//        //将jpeg写入文件
//        FileOutputStream out = new FileOutputStream(resizedFile);
//
//        //将图像编码为jpeg数据流
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//
//        JPEGEncodeParam param = encoder
//                .getDefaultJPEGEncodeParam(bufferedImage);
//
//        param.setQuality(quality, true);
//
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(bufferedImage);

    }

}
