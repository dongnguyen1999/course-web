package com.ndong.courseweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.ndong.courseweb.constant.DefaultValueConstant;

public class LessonDTO extends AbstractDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer idNo;

    private Long courseId;

    private String title;

    private String shortDescription;

    private String content;

    private Timestamp uploadTime;

    private Integer duration;

    private Boolean enableFreeTrial;

    public static LessonDTO createDefault() {
        LessonDTO lesson = new LessonDTO();
        lesson.setTitle(DefaultValueConstant.LESON_TITLE);
        lesson.setEnableFreeTrial(false);
        return lesson;
    }

    public Integer getIdNo() {
        return idNo;
    }

    public void setIdNo(Integer idNo) {
        this.idNo = idNo;
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

    public Boolean getEnableFreeTrial() {
        return enableFreeTrial;
    }

    public void setEnableFreeTrial(Boolean enableFreeTrial) {
        this.enableFreeTrial = enableFreeTrial;
    }
}
