package com.cloudchaptask.dto.response;

import lombok.Getter;

@Getter
public class FileDownloadUrlResponse {

    // Presigned URL
    private final String url;

    // 생성자
    public FileDownloadUrlResponse(String url) {
        this.url = url;
    }
}
