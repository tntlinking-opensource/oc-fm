package com.geeke.utils;

import com.geeke.fixasset.asset.dto.ConditionDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 * @author goblin
 * @Date 2020-05-20
 * @Version 1.0.0
 * @Description
 * @Company www.newtouch.cn
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 **/
//@Component
public class QRUtil {

    public static void main(String[] args) throws Exception {
        ConditionDto conditionDto =new ConditionDto();
        conditionDto.setId("id");
        conditionDto.setCardNo("202000000000000");
        conditionDto.setAssetName("文化宫-二维码投好2");
        conditionDto.setAssetCode("asset_code3");
        conditionDto.setCompanyId("companyId");
        conditionDto.setCardModel("card_model");
        conditionDto.setProducer("producer");
        conditionDto.setBusinessDate("business_date");
        conditionDto.setPlace("place");
        String qrCode = createQRCode("/Users/jieren/workfiles/固定资产众包/image", conditionDto);
        System.out.println(qrCode);
    }

    /**
     * @param qrcode_path 二维码存放的文件夹路径
     * @param
     * @return java.lang.String
     * @date 2020-05-21
     * @Description 创建二维码，返回二维码的路径
     * @Author goblin
     */
    public static String createQRCode(String qrcode_path, ConditionDto conditionDto)
            throws IOException, WriterException {
        // 资产编码
        String asset_code = conditionDto.getAssetCode();
        String companyId = conditionDto.getCompanyId();
        //String tenantId = "XZ0010";
        String finalName = qrcode_path+File.separator+ companyId;
        File file = new File(finalName);
        if(!file.exists()){
            file.mkdirs();
        }
        //二维码存放路径
        String fileName = finalName+File.separator+asset_code+".jpeg";
        //获取二维码
        BufferedImage bi = getBufferedImage(conditionDto);
        //保存图片 JPEG表示保存格式
        ImageIO.write(bi, "JPEG", new FileOutputStream(fileName));
        return fileName;
    }


    /**
     * 获取生成的二维码图片（未储存）
     * @param
     * @return
     * @throws WriterException
     * @throws UnsupportedEncodingException
     */
    public static BufferedImage getBufferedImage(ConditionDto conditionDto)
            throws WriterException, UnsupportedEncodingException {
        // 资产ID
        String card_id = conditionDto.getId();
        // 资产名称
        String asset_name =  conditionDto.getAssetName();
        // 资产编码
        String asset_code = conditionDto.getAssetCode();
        // 规格型号
        String card_model = conditionDto.getCardModel();
        // 图片宽
        int width = 1500;
        // 图片高
        int height = 800;
        //生成二维码的产品id
        String deviceId = card_id;
        //先创建一个图片编辑器，生成一个二维码，二维码生成图片后再创建另一个图片编辑器，用来编写表格
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        // 矫错级别
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        deviceId = new String(deviceId.getBytes("UTF-8"), "ISO-8859-1");
        int qrCodeSize = 100;
        BitMatrix byteMatrix = qrCodeWriter.encode(deviceId, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth-20, matrixWidth-20, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth , matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 10, j - 10, 1, 1);
                }
            }
        }
        image.flush();
        // 得到图片缓冲区
        // INT精确度达到一定,RGB三原色，高度70,宽度150
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.createGraphics();
        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        // 设置背景颜色
        g2.setColor(Color.WHITE);
        // 填充整张图片(其实就是设置背景颜色)
        g2.fillRect(0, 0, width, height);
        // 设置字体颜色
        g2.setColor(Color.black);
        // 边框加粗
        //g2.setStroke(new BasicStroke(2.0f));
        // 画边框就是黑边框
        //g2.drawRect(0, 0, width-2 , height-2);
        // 设置标题的字体,字号,大小
        Font titleFont = new Font("宋体", Font.PLAIN, 50);
        g2.setFont(titleFont);
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        // 计算文字长度,计算居中的X点坐标
        FontMetrics fm = g2.getFontMetrics(titleFont);
        // 资产编号
        twoLineDrawString(fm,g2,"资产编号：" ,asset_code,750,200);
        // 资产名称
        twoLineDrawString(fm,g2,"资产名称：" , asset_name,750,400);
        // 型号
        twoLineDrawString(fm,g2,"型号：", card_model,750,600);
        // 画文字到新的面板
        g2.drawImage(image, 50, 50, 700, 700, null);
        // 释放对象
        g2.dispose();
        return bi;
    }

    /**
     * 最多容纳两行文字,超出省略
     * @param fm
     * @param g2
     * @param text
     * @param x
     * @param y
     */
    private static void twoLineDrawString(FontMetrics fm,Graphics2D g2,String title,String text,int x,int y){
        String totalStr = title + text;
        int maxLen = 700;
        if (fm.stringWidth(totalStr) <= maxLen){
            g2.drawString(totalStr, x, y);
        }else {
            char [] chars = totalStr.toCharArray();
            int num = 0;
            final String suffix = "…";
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if (fm.stringWidth(sb.toString()) < maxLen){
                    sb.append(chars[i]);
                }else {
                    if (num == 0){
                        g2.drawString(sb.toString(), x, y);
                        maxLen = maxLen - fm.stringWidth(title) - fm.stringWidth(suffix);
                    }else {
                        sb.append(suffix);
                        g2.drawString(sb.toString(), x+fm.stringWidth(title), y+50);
                    }
                    sb.delete(0,sb.length());
                    num++;
                }
                if (num == 2){
                    break;
                }
            }
        }
    }




}
