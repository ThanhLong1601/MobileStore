package com.ex.mobilestore.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {


    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile (MultipartFile file)throws IOException {
        try {
            cloudinary.api().createFolder("ImageMobile",ObjectUtils.emptyMap());
        }catch (Exception e){
            System.out.printf("Error creating folder: " + e.getMessage());
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","ImageMobile"));
        return uploadResult.get("url").toString();
    }
}
