package com.example.myclg.Models;


public class firebasemodel {

    private String title;
    private String content;
    private String id;


    public firebasemodel()
    {

    }

    public firebasemodel(String title, String content, String id)
    {
        this.title=title;
        this.content=content;
        this.id=id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
