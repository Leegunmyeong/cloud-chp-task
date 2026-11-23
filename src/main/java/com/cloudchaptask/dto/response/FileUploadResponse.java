package com.cloudchaptask.dto.response;

import lombok.Getter;

@Getter
public class FileUploadResponse {

    // 업로드한 이미지 주소
    private final String imageUrl;

    // 생성자
    public FileUploadResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
