package com.airbnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

@Service
public class BucketService {


    @Autowired
    private AmazonS3 amazonS3;



    public String uploadFile(MultipartFile file , String bucketName) throws IOException {

        if(file.isEmpty()){

            throw new IllegalStateException("cannot upload empty file");
        }

        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());

            file.transferTo(convFile);
            try {

                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            } catch (Exception e) {

                throw new IllegalStateException("failed to upload file", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }

    }



    public void deleteFile(String bucketName, String fileName) {
        try {
            // Using DeleteObjectRequest for AWS SDK v1.x
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);

            // Delete the object from the S3 bucket
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (AmazonS3Exception e) {
            // Handle any Amazon S3 exceptions
            throw new RuntimeException("Failed to delete file from S3: " + e.getMessage(), e);
        }


    }
}
