package com.android.framework.util;

/**
 * 地图纠偏工具类，实现GPS、GCJ-02、Baidu、Mapbar坐标的相互转换
 * <p>
 * <p>
 * 坐标系概念定义：
 * WGS-84：大地坐标系，GPS所采用的坐标系。<a href="http://baike.baidu.com/view/46572.htm">http://baike.baidu.com/view/46572.htm</a>
 * GCJ-02：火星坐标系，中国国家测绘局制订的地理信息系统的坐标系统。<a href="http://baike.baidu.com/view/3163334.htm">http://baike.baidu
 * .com/view/3163334.htm</a>
 * Baidu： 百度坐标系
 * Mapbar：图吧坐标系
 */
public class MapOffsetUtils {

    // double f = 1 / 298.3;
    // double b = A * (1 - f);
    // double EE = (Math.pow(A, 2) - Math.pow(b, 2)) / Math.pow(A, 2);

    private static final double A = 6378245.0;                                  // 卫星椭球坐标投影到平面地图坐标系的投影因子。
    private static final double EE = 0.00669342162296594323;                    // 椭球的偏心率。
    private static final double PI = 3.14159265358979323;                       // 圆周率
    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * GPS转百度坐标
     */
    public static Point getBaiduFromGPS(double lng, double lat) {
        // GPS -> GCJ-02
        Point temp = getGCJFromGPS(lng, lat);
        // GCJ-02 -> Baidu
        return getBaiduFromGCJ(temp.getLng(), temp.getLat());
    }

    /**
     * 百度坐标转GPS
     */
    public static Point getGPSFromBaidu(double lng, double lat) {
        // Baidu -> GCJ-02
        Point temp = getGCJFromBaidu(lng, lat);
        // GCJ-02 -> Baidu
        return getGPSFromGCJ(temp.getLng(), temp.getLat());
    }

    /**
     * GPS转火星坐标
     */
    public static Point getGCJFromGPS(double lng, double lat) {
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);

        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLng = transformLng(lng - 105.0, lat - 35.0);
        dLng = (dLng * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);

        double outlat = lat + dLat;
        double outlng = lng + dLng;

        return new Point(outlng, outlat);
    }

    /**
     * 火星坐标转GPS
     */
    public static Point getGPSFromGCJ(double lng, double lat) {
        Point point = getGCJFromGPS(lng, lat);

        double g_lng = point.getLng();
        double g_lat = point.getLat();

        double outlat = 2 * lat - g_lat;
        double outlng = 2 * lng - g_lng;

        return new Point(outlng, outlat);
    }

    /**
     * 火星坐标转百度
     */
    public static Point getBaiduFromGCJ(double lng, double lat) {
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);

        double outlng = z * Math.cos(theta) + 0.0065;
        double outlat = z * Math.sin(theta) + 0.006;

        return new Point(outlng, outlat);
    }

    /**
     * 百度转火星坐标
     */
    public static Point getGCJFromBaidu(double lng, double lat) {
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);

        double outlng = z * Math.cos(theta);
        double outlat = z * Math.sin(theta);

        return new Point(outlng, outlat);
    }

    /**
     * GPS转图吧坐标
     */
    public static Point getMapbarFromGPS(double lng, double lat) {
        lng = lng * 100000 % 36000000;
        lat = lat * 100000 % 36000000;

        double outlng = (((Math.cos(lat / 100000)) * (lng / 18000)) + ((Math.sin(lng / 100000)) * (lat / 9000)) + lng)
                / 100000.0;
        double outlat = (((Math.sin(lat / 100000)) * (lng / 18000)) + ((Math.cos(lng / 100000)) * (lat / 9000)) + lat)
                / 100000.0;

        return new Point(outlng, outlat);
    }

    /**
     * 图吧坐标转GPS
     */
    public static Point getGPSFromMapbar(double lng, double lat) {
        lng = lng * 100000 % 36000000;
        lat = lat * 100000 % 36000000;

        double x = ((Math.cos(lat / 100000)) * (lng / 18000)) + ((Math.sin(lng / 100000)) * (lat / 9000)) + lng;
        double y = ((Math.sin(lat / 100000)) * (lng / 18000)) + ((Math.cos(lng / 100000)) * (lat / 9000)) + lat;

        double outlng =
                (-(((Math.cos(y / 100000)) * (x / 18000)) + ((Math.sin(x / 100000)) * (y / 9000))) + lng + ((lng > 0)
                                                                                                            ? 1 : -1))
                        / 100000.0;
        double outlat =
                (-(((Math.sin(y / 100000)) * (x / 18000)) + ((Math.cos(x / 100000)) * (y / 9000))) + lat + ((lat > 0)
                                                                                                            ? 1 : -1))
                        / 100000.0;

        return new Point(outlng, outlat);
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 坐标点
     */
    public static class Point {
        private double lng;
        private double lat;

        public Point(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
