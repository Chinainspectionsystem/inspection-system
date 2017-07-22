package com.ice.edupatrol;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/6/24.
 */
public class MyApplication extends Application {
    public  String name="admin";
    public  String realname="admin";
    public  String phone="111";
    public  String sex="男";
    public  String url="http://ujitom.55555.io/EduInspectSystem/icon/";
    public  String teachingid="2222";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
    public String getName()
{
    return name;
}
    public void setName(String name)
    {
 this.name=name;
    }
    public String getRealname()
    {
        return realname;
    }
    public void setRealname(String realname)
    {
        this.realname=realname;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex=sex;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url=url;
    }
    public String getTeachingid()
    {
        return teachingid;
    }
    public void setTeachingid(String teachingid)
    {
        this.teachingid=teachingid;
    }
}
