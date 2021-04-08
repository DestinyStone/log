package com.test.maven.testcomsuner.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.test.maven.testcomsuner.feign.TestFeign;
import com.test.maven.testcomsuner.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class TestController {

    @Autowired
    private TestFeign testFeign;

    @Autowired
    private TestService testService;

    @GetMapping("/getImage")
    public String getImage() throws IOException, URISyntaxException {


        URL url = new URL("https://mall.qinlin.plus/wangniuLogo.png");
        BufferedImage read = ImageIO.read(url);


        QrConfig qrConfig = new QrConfig(100, 100);
        qrConfig.setImg(read);

        BufferedImage generate = QrCodeUtil.generate("http://www.baidu.com", qrConfig);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(generate, "png", byteArrayOutputStream);
        String encode = Base64Encoder.encode(byteArrayOutputStream.toByteArray());
        return "data:image/png;base64," + encode;
    }
}
