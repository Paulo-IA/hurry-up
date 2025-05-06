package com.paulo.hurry_up.service.provider;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Map;

@Service
public class PresignedUrlAwsS3Service implements PresignedUrlProvider {

    @Override
    public String generateUploadPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
        try (S3Presigner presigner = S3Presigner.create()) {

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();


            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();

            System.out.println("Metadata: " + metadata);
            System.out.println("Generated URL: " + presignedRequest.url());

            System.out.println("Presigned URL to upload a file to: " + myURL);
            System.out.println("HTTP method: " + presignedRequest.httpRequest().method());

            return presignedRequest.url().toExternalForm();
        }
    }
}
