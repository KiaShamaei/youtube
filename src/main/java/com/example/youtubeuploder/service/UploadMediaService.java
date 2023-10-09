package com.example.youtubeuploder.service;


import com.example.youtubeuploder.dao.UploadMediaDao;
import com.example.youtubeuploder.entity.UploadMedia;
import com.example.youtubeuploder.utils.FileStorageUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UploadMediaService {

    @Autowired
    private UploadMediaDao dao;

    @Autowired
    private FileStorageUtil storageUtil;

    public byte[] getFile(String path) throws IOException {
        return storageUtil.getFile(path);
    }

    public Optional<UploadMedia> findById(String id) {
        return dao.findById(id);
    }

    public List<byte[]> findAll() throws IOException{

        var result = dao.getAll();
        List<byte[]> output=  result.parallelStream().map(t-> {
            try {
                return getFile(t.getOriginalFilePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).collect(Collectors.toList());
        return output;
    }

    @Transactional
    public UploadMedia createFile(MultipartFile file) throws IOException {
        UploadMedia media = new UploadMedia();
        media.setOriginalFileName(Paths.get(file.getOriginalFilename()).getFileName().toString());
        media.setOriginalFileExtension(FilenameUtils.getExtension(media.getOriginalFileName()));
        media.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        media.setWrite(false);
        media.setCompressed(false);
        String path = storageUtil.createFile(file);
        media.setOriginalFilePath(path);
        return dao.upload(media);
    }


}
