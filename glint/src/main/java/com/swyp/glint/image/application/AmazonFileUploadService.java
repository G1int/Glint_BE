package com.swyp.glint.image.application;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.swyp.glint.core.common.exception.FileNotFoundException;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AmazonFileUploadService implements FileUploadService {

    static Logger logger = LoggerFactory.getLogger(AmazonFileUploadService.class);

    @Value("${cloud.aws.s3.bucket:resource-glint}")
    private String bucket = "resource-glint";

    private static final String profileImagePreObjectKey = "profile";
    private static final String authenticationImagePreObjectKey = "authentication";

    private final AmazonS3 s3Client;

    @Override
    public String uploadProfileImageFile(String filename, MultipartFile multipartFile) {
        return uploadFile(profileImagePreObjectKey, filename, multipartFile, true);
    }

    @Override
    public String uploadAuthenticationImageFile(String filename, MultipartFile multipartFile) {
        return uploadFile(profileImagePreObjectKey, filename, multipartFile, true);
    }

    @Override
    public byte[] getProfileImageFile(String filename) {
        return getFile(profileImagePreObjectKey + "/" + filename);
    }

    @Override
    public void deleteProfileImageFile(String filename) {
        deleteFile(profileImagePreObjectKey, filename);
    }

    @Override
    public String getFileUrl(String objectKey) {
        if(!s3Client.doesObjectExist(bucket,objectKey)){
            throw new FileNotFoundException("not found s3Client object : " + objectKey);
        }

        return  s3Client.getUrl(bucket, objectKey).toString();
    }


    private String uploadFile(String preObjectkey, String inputFileName, MultipartFile multipartFile, boolean needFullUrlWithEncoding) {
        String fileName = StringUtil.isNullOrEmpty(inputFileName) ? multipartFile.getOriginalFilename() : inputFileName;

        String objectKey = preObjectkey + "/" + fileName;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            s3Client.putObject(new PutObjectRequest(bucket, objectKey, byteArrayInputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {

            logger.error("s3Client.putObject", e);
            throw new RuntimeException("preObjectkey : " + preObjectkey + " | " + e.getMessage());

        } catch (Exception e) {

            logger.error("s3Client.putObject", e);
            throw new RuntimeException("preObjectkey : " + preObjectkey + " | " + e.getMessage());

        }

        return needFullUrlWithEncoding ? s3Client.getUrl(bucket, objectKey).toString() : objectKey;
    }

    private byte[] getFile(String objectKey) {
        try{

            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket, objectKey));
            return IOUtils.toByteArray(s3Object.getObjectContent());

        }catch (IOException ioe) {

            logger.error("s3Client.putObject", ioe);
            throw new RuntimeException("objectkey : " + objectKey + " | " + ioe.getMessage());

        }catch (Exception e){
            logger.error("s3Client.putObject", e);

            if(e instanceof AmazonS3Exception && ((AmazonS3Exception)e).getStatusCode() == 404){
                throw new FileNotFoundException("objectkey : " + objectKey + " | " + e.getMessage());
            }

            throw new RuntimeException("objectkey : " + objectKey + " | " + e.getMessage());
        }
    }

    private void deleteFile(String preObjectkey, String fileName) {
        String objectKey = preObjectkey + "/" + fileName;

        s3Client.deleteObject(bucket, objectKey);
    }

    private void deleteFiles(String[] objectKeys) {

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket).withKeys(objectKeys);

        s3Client.deleteObjects(deleteObjectsRequest);

    }
}
