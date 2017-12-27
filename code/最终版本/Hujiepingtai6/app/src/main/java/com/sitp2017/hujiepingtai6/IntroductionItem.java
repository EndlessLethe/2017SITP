package com.sitp2017.hujiepingtai6;

/**
 * Created by gameben on 2017/5/5.
 */
public class IntroductionItem { //AppIntroductionPage活动中RecyclerView的实体类

    private String name;

    private String brief;

    private int imageId;

    public IntroductionItem(String name, int imageId, String brief) {
        this.name = name;
        this.imageId = imageId;
        this.brief = brief;
    }

    public String getName() {
        return name;
    }

    public String getBrief() {
        return brief;
    }

    public int getImageId() {
        return imageId;
    }
}
