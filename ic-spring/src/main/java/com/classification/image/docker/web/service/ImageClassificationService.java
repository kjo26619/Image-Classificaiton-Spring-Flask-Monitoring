package com.classification.image.docker.web.service;

import org.json.simple.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class ImageClassificationService {


    public JSONObject communicateFlask(MultipartFile file, String flaskUrl) throws Exception {

        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

            parts.add("file", file.getResource());

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

            ResponseEntity<JSONObject> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, JSONObject.class);

            // System.out.println(response.getBody());

            JSONObject result = response.getBody();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception();
    }
}
