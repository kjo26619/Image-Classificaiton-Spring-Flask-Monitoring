package com.classification.image.docker.web.controller;

import com.classification.image.docker.web.error.EntityEmptyException;
import com.classification.image.docker.web.error.WrongExtensionException;
import com.classification.image.docker.web.service.ImageClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

@Controller
public class ImageClassificationController {

    @Autowired
    ImageClassificationService service;

    @Value("${my.flask.url}")
    private String flaskUrl;

    @RequestMapping(value = "/image", method= RequestMethod.POST)
    public String fileUpload(MultipartFile file, Model model) throws Exception {
        if(!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));

            String[] allowed_extension = {".jpg", ".jpeg", ".png"};

            if ( !Arrays.asList(allowed_extension).contains(extension)) {
                throw new WrongExtensionException("잘못된 확장자입니다");
            }

            JSONObject result = service.communicateFlask(file, flaskUrl);

            ArrayList<String> resultClass = (ArrayList<String>) result.get("class");

            ArrayList<Double> resultProb = (ArrayList<Double>) result.get("probability");

            byte[] buffer = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(buffer);

            BufferedImage bufferImage = ImageIO.read(file.getInputStream());

            int width = bufferImage.getWidth();
            int height = bufferImage.getHeight();

            if (width >= 600) width /= 3;
            if (height >= 400) height /= 3;

            model.addAttribute("result", result);
            model.addAttribute("img", base64Image);
            model.addAttribute("imgWidth", width);
            model.addAttribute("imgHeight", height);

            return "result";
        }

        throw new EntityEmptyException("이미지를 선택하지 않았습니다.");
    }
}
