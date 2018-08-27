package com.stargazer.demos.math;

public class Complex {
    private final double x,y;//c = x + y *i即real + i* imaginary
    /**
     * 构造复数对象z ＝ x +y *i
     * @param x 实部/real
     * @param y 虚部/imaginary
     */
    public Complex(double x,double y) {
        this.x =x;   this.y =y;
    }
    Complex(){ this(0,0); }

    /**
     *复数乘法。Complex multiplication
     *
     */
    public Complex mul(Complex a){
        double newx =this.x*a.x-this.y*a.y;
        double newy =2*this.x*a.y;
        return new Complex(newx,newy);
    }
    public Complex plus(Complex a){
        double newx=this.x +a.x;
        double newy=this.y +a.y;
        return new Complex(newx,newy);
    }
    /**
     * 求模。该复数到坐标原点的距离
     * @return  |z|   z = x + i*y
     */
    public double mod(){
        return (x!=0 || y!=0) ? Math.sqrt( x*x + y*y):0d;
    }
}
