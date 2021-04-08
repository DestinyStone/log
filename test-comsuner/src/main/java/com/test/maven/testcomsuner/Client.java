package com.test.maven.testcomsuner;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;

public class Client {
    public static void main(String[] args) throws ParseException, InterruptedException, IOException {
        QrConfig qrConfig = new QrConfig(100, 100);

        BufferedImage generate = QrCodeUtil.generate("http://www.baidu.com", qrConfig);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(generate, "png", byteArrayOutputStream);
        String encode = Base64Encoder.encode(byteArrayOutputStream.toByteArray());
        System.out.println(encode);
    }
}
