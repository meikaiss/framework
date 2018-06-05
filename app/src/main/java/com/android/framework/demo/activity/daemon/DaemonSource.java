package com.android.framework.demo.activity.daemon;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 后台上传的本地源文件信息
 * Created by meikai on 2018/06/01.
 */
public class DaemonSource {

    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;


    @IntDef({TYPE_IMAGE, TYPE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SOURCE_TYPE {}

    public @SOURCE_TYPE
    int type;

    public String localPath;

    public static DaemonSource image(String localPath) {
        DaemonSource source = new DaemonSource();
        source.localPath = localPath;
        source.type = TYPE_IMAGE;
        return source;
    }

    public static DaemonSource video(String localPath) {
        DaemonSource source = new DaemonSource();
        source.localPath = localPath;
        source.type = TYPE_VIDEO;

        return source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof DaemonSource)) {
            return false;
        }

        if (this.type != ((DaemonSource) obj).type) {
            return false;
        }

        if (this.localPath == null) {
            //两个不存在的物体无法比较，更不能说它们是相同的
            return false;
        } else {
            return this.localPath.equals(((DaemonSource) obj).localPath);
        }
    }

    /**
     * 在对象不作为Hash表的key的情况下，重写这个方法没有作用
     * 但按照java语言规范要求，即使不作为hash表的key，即:如果两个对象的equal相等，则hashCode必须返回相同的整数
     * 若不重写，默认会根据两个对象的内存地址来生成hashCode，这显然会得到不同的整数，违背规则
     */
    @Override
    public int hashCode() {
        return new Integer(type).hashCode() + (localPath != null ? localPath.hashCode() : 0);
    }

}
