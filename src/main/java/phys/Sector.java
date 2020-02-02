package phys;

public class Sector {
    private XY origin; // 中心点。
    private XY direction; // 方向。
    private double radius; // 半径。
    private double radian; // 开角，弧度。

    public Sector(XY origin, XY direction, double radius, double radian) {
        this.origin = origin;
        this.direction = direction;
        this.radius = radius;
        this.radian = radian;
    }

    /**
     * 判断一个点是否在扇形中。
     *
     * @param point 测试的点。
     * @return 判断结果。
     */
    public boolean contain(XY point) {
        // 计算是否在扇形所在的圆内。
        double distance = point.distance(origin);
        if (distance > radius)
            return false; // 超出半径范围。

        // 计算是否在夹角内。
        XY vectorP = XY.subtract(point, origin); // 得到点到原点的向量。
        vectorP.normalize(); // 单位向量化。
        // 单位向量的点乘得夹角 cos
        double cosP = XY.dot(vectorP, direction); // 点P 与 扇形方向向量 的夹角cos
        double cosS = Math.cos(radian * 0.5); // 扇形两条对称边与方向向量夹角相等，cos 一致，为二分之一开角的 cos。
        return cosP > cosS; // cos 大者角度小，所以大于扇形边cos 的都在扇形内。
    }

    /**
     * 测试。
     */
    public static void main(String[] arguments) {
        XY point = new XY(1.0, 3.0); // 点P

        XY sectorDirection = new XY(1.0, 3.0); // 扇形方向。
        sectorDirection.normalize(); // 方向必须是单位向量。
        Sector sector = new Sector(new XY(1.0, 2.0), // 扇形原点。
                sectorDirection, // 扇形方向。
                2.0, // 扇形半径。
                1.0 // 扇形开角，弧度。
        );

        System.out.println(sector.contain(point));
    }

    /**
     * 用于代表向量和点的数据结构。
     *
     */
    public static class XY {
        public double x, y;

        public XY(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 向量求长度。
         */
        public double getMagnitude() {
            return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
        }

        /**
         * 单位向量化。
         */
        public void normalize() {
            double length = getMagnitude();
            x /= length;
            y /= length;
        }

        /**
         * 计算与另一个点的距离。
         *
         * @param another 另外一个点。
         * @return 距离。
         */
        public double distance(XY another) {
            XY diff = subtract(this, another); // 两点做差得到一个向量。
            return diff.getMagnitude(); // 该向量的长度就是点距离。
        }

        /**
         * 点做减法可以得到一个向量。
         */
        public static XY subtract(XY one, XY two) {
            return new XY(one.x - two.x, one.y - two.y);
        }

        /**
         * 向量点乘。
         */
        public static double dot(XY one, XY two) {
            return one.x * two.x + one.y * two.y;
        }
    }
}
