package com.ndong.courseweb.dto;

import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.composite_id.LessonId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.sql.Timestamp;

public class LessonDTO {

    private Integer no;

    private Long courseId;

    private String title;

    private String shortDescription;

    private String content;

    private Timestamp uploadTime;

    private Integer duration;

    private Boolean enableFreeTrail;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getEnableFreeTrail() {
        return enableFreeTrail;
    }

    public void setEnableFreeTrail(Boolean enableFreeTrail) {
        this.enableFreeTrail = enableFreeTrail;
    }
}
