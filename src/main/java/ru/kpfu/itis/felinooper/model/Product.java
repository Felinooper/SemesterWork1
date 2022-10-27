package ru.kpfu.itis.felinooper.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String composer;
    private String image;
    private double price;
    private int countOfMark;
    private double rating;
    private String linkAppleMusic;

    public String getLinkAppleMusic() {
        return linkAppleMusic;
    }

    public void setLinkAppleMusic(String linkAppleMusic) {
        this.linkAppleMusic = linkAppleMusic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCountOfMark() {
        return countOfMark;
    }

    public void setCountOfMark(int countOfMark) {
        this.countOfMark = countOfMark;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
