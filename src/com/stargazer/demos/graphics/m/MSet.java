package com.stargazer.demos.graphics.m;

import com.stargazer.demos.math.Complex;

import javax.swing.*;
import java.awt.*;

public class MSet {
    public static void main(String[] args) {
        MSet m = new MSet();
        m.drawMSet();
    }

    public void drawMSet(){
        JFrame frame = new JFrame();
        frame.setTitle("MSet");
        frame.setSize(1000,800);
        //设置窗口位于屏幕中央
        frame.setLocationRelativeTo(null);
        //参数为3时，表示关闭窗口则程序退出
        frame.setDefaultCloseOperation(3);
        //创建画板
        JPanel jpanel = new JPanel() {
            private final double xMin = -2.25,yMin = -1.5,xMax = 0.75,yMax = 1.5;
            private int width = 1000,height = 800;
            double xUnit = (xMax - xMin) / width;
            double yUnit = (yMax - yMin) / height;
            int R = 10; //
            int N = 100;
            @Override
            public void paint(Graphics g) {
                // 必须先调用父类的paint方法
                super.paint(g);
                // 用画笔Graphics，在画板JPanel上画一个小人
                for(int i = 0; i < width; i++) {
                    for(int j = 0; j < height; j++) {
                        double r = xMin + i * xUnit;
                        double im = yMin + j* yUnit;
                        int color = 10 * iterate(r,im);
                        if(0 != color)
                            g.setColor(new Color(color));
                        g.drawLine(i,j,i,j);
                    }
                }
            }

            private int iterate(double real,double imaginary) {
                Complex c = new Complex(real,imaginary);
                Complex z = new Complex(0.0,0.0);
                for(int i = 1; ; i++){
                    z = (z.mul(z)).plus(c);
                    if(z.mod() > R)
                        return i;
                    if(i > N)
                        return 0;
                }
            }
        };
        //将绘有小人图像的画板嵌入到相框中
        frame.add(jpanel);
        // 将画框展示出来。true设置可见，默认为false隐藏
        frame.setVisible(true);
    }
}
