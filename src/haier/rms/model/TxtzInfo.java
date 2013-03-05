package haier.rms.model;

/**
 * 贴现台帐
 * User: zhanrui
 * Date: 2010-4-9
 * Time: 9:43:53
 * To change this template use File | Settings | File Templates.
 */
public class TxtzInfo {
    String khbh;
    String khmc;
    String rq;
    String hpdqr;
    double lx;
    double qx;   //期限
    double day_lx;

    public String getKhbh() {
        return khbh;
    }

    public void setKhbh(String khbh) {
        this.khbh = khbh;
    }

    public String getKhmc() {
        return khmc;
    }

    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getHpdqr() {
        return hpdqr;
    }

    public void setHpdqr(String hpdqr) {
        this.hpdqr = hpdqr;
    }

    public double getLx() {
        return lx;
    }

    public void setLx(double lx) {
        this.lx = lx;
    }

    public double getQx() {
        return qx;
    }

    public void setQx(double qx) {
        this.qx = qx;
    }

    public double getDay_lx() {
        return day_lx;
    }

    public void setDay_lx(double day_lx) {
        this.day_lx = day_lx;
    }
}
