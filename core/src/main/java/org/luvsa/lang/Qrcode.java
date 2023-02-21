package org.luvsa.lang;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 二维码工具
 *
 * @author Aglet
 * @create 2023/2/14 10:27
 */
public class Qrcode {
    private static final String CHARSET = "utf-8";
    private static final String NAME = "JPG";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    public static void generate(String content, int width, int height, String name, OutputStream stream) {
        try {
            if (Strings.isEmpty(content)) {
                return;
            }
            //核心代码-生成二维码
            var image = draw(content.trim(), width, height);
            //区别就是这一句，输出到输出流中，如果第三个参数是 File，则输出到文件中
            ImageIO.write(image, name, stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void generate(String content, OutputStream stream) {
        generate(content, WIDTH, HEIGHT, NAME, stream);
    }

    /**
     * 绘制一张二维码图片
     *
     * @param content 二维码中的内容
     * @return 二维码图片
     * @throws Exception 绘制异常
     */
    private static BufferedImage draw(String content, int width, int height) throws Exception {
        var hints = new HashMap<EncodeHintType, Object>();
        //EncodeHintType.CHARACTER_SET：设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //EncodeHintType.ERROR_CORRECTION：设置误差校正
        //ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
        //不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);
        var writer = new MultiFormatWriter();
        var matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
