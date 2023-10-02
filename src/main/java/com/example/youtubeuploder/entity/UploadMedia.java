package com.example.youtubeuploder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class UploadMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_path")
    public String originalFilePath;

    @Column(name = "original_file_name", nullable = false)
    public String originalFileName;

    @Column(name = "original_file_ext", nullable = false)
    public String originalFileExtension;

    @JsonIgnore
    @Column(name = "is_write")
    private boolean write;


    @Column(name = "compressed_path")
    public String compressedFilePath;


    @Column(name = "is_compressed")
    private boolean compressed;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;


    @Column(name = "is_thumbnail")
    private boolean thumbnail;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

}
