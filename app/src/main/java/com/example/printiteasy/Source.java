package com.example.printiteasy;

import java.util.ArrayList;

class Source {
    private String mShopName;
    private String mRateBlack;
    private String mRateColor;
    private String mAddress;

    private Source(String shopName, String rateBlack,String rateColor ,String address){
        mShopName = shopName;
        mRateBlack = rateBlack;
        mAddress = address;
        mRateColor = rateColor;
    }

    String getShopName(){
        return mShopName;
    }
    String getRateBlack(){
        return mRateBlack;
    }
    String getRateColor(){
        return mRateColor;
    }


    String getAddress(){
        return mAddress;
    }
    static ArrayList<Source> createShopList(String shopName_1){
        ArrayList<Source> shopDetails = new ArrayList<>();
        shopDetails.add(new Source("ShopName_1","3","5","Central Library, MNNIT"));
        shopDetails.add(new Source("ShopName_2","2","10","Saraswati Gate, MNNIT"));
        return shopDetails;
    }


}

