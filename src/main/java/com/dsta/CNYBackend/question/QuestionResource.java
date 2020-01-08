package com.dsta.CNYBackend.question;

import com.dsta.CNYBackend.question.Image.Image;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

public class QuestionResource {
    @JsonView({QuestionResourceView.List.class})
    private Long id;
    @JsonView({QuestionResourceView.List.class})
    private int position;
    @JsonView({QuestionResourceView.List.class})
    private String title;
    @JsonView({QuestionResourceView.List.class})
    private Set<Image> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "QuestionResource{" +
                "id=" + id +
                ", position=" + position +
                ", title='" + title + '\'' +
                ", images=" + images +
                '}';
    }
}
