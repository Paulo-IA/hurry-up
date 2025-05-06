package com.paulo.hurry_up.service.provider;

import java.util.Map;

public interface PresignedUrlProvider {
    String generateUploadPresignedUrl(String bucketName, String keyName, Map<String, String> metadata);
}
