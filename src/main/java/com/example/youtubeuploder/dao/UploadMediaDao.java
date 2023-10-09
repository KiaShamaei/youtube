package com.example.youtubeuploder.dao;

import com.example.youtubeuploder.entity.UploadMedia;
import com.example.youtubeuploder.repository.UploadMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UploadMediaDao {

    @Autowired
    private UploadMediaRepository repository;

    public Optional<UploadMedia> findById(String id) {
        return repository.findById(id);
    }

    public UploadMedia upload(UploadMedia value) {
        return repository.save(value);
    }

    public List<UploadMedia> getAll(){
        return repository.findAll();
    }
}
