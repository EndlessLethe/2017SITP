package com.sitp2017.hujiepingtai6;

/**
 * Created by gameben on 2017/5/9.
 */
public class PopupWindowItem {
    //弹出菜单中RecyclerView的实体类

    private int imageId;

    private String name;

    public PopupWindowItem(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
