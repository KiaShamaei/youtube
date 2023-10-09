package com.example.youtubeuploder.controller;


import com.example.youtubeuploder.entity.UploadMedia;
import com.example.youtubeuploder.service.UploadMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/media")
public class MediaUploaderController {

    @Value("${server.compression.mime-types}")
    private List<String> contentVideos;

    @Autowired
    private UploadMediaService service;

    @PostMapping(
            value = "/v1/upload/video",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> createVideo(
            @RequestPart("content")  MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (!contentVideos.contains(contentType)) {
            log.info("invalid  type: {}", contentType);
            return badRequest().body(" invalid  type!");
        }

        UploadMedia metaData = service.createFile(file);
        return ok(metaData);
    }



    @GetMapping("/v1/download/{id}/video/")
    public ResponseEntity<?> getVideoOriginal(@PathVariable("id") String id) {
        log.info("/v1/download/{id}/video/ is call");
        Optional<UploadMedia> mediaOptional = service.findById(id);
        if (!mediaOptional.isPresent()) {
            return noContent().build();
        }

        UploadMedia media = mediaOptional.get();
        if (!media.isWrite()) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }

        String path = media.getOriginalFilePath();
        try {
            byte[] file = service.getFile(path);
            return ok(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * this part need pagging in real project
     * just for test
     * @return
     */
    @GetMapping("/v1/download/video/all")
    public ResponseEntity<?> getAllVideos() {

        log.info("/v1/download/video/all is call");
        try {
            var result = service.findAll();
            return ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
