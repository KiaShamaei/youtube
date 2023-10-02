package com.example.youtubeuploder.repository;


import com.example.youtubeuploder.entity.UploadMedia;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UploadMediaRepository extends JpaRepository<UploadMedia, String> {

    @Modifying
    @Query("update UploadMedia set compressedFilePath = ?2, compressed = true where id = ?1")
    int convertFileSuccess(Long id, String path);

    @Modifying
    @Query("update UploadMedia set thumbnailPath = ?2, thumbnail = true where id = ?1")
    int generateThumbnail(Long id, String thumbnailPath);
}
