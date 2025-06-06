package com.qingqing.common.vo.user;

public class SecondHandGoodsListVO {
    private Long id;
    private String coverUrl;
    private String title;
    private Double price;
    private String publisherNickname;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getPublisherNickname() { return publisherNickname; }
    public void setPublisherNickname(String publisherNickname) { this.publisherNickname = publisherNickname; }
} 