package com.example.lab_2.AdditionalClasses;

public class IconClass {

    private String brandName;
    private String brandOther;
    private int brandLogo;

    public IconClass(String brandName, String brandOther, int brandLogo)
    {
        this.brandName = brandName;
        this.brandLogo = brandLogo;
        this.brandOther = brandOther;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public int getBrandLogo()
    {
        return brandLogo;
    }

    public String getBrandOther()
    {
        return brandOther;
    }
}
