package com.example.hexcontrol;

import java.util.FormatFlagsConversionMismatchException;

public class Payload{
    public int Strength = 0;
    public int Angle = 0;
    public int Fold = 1;
    public int Strafe = 0;
    public int Height = 1;

    @Override
    public String toString() {
        return Strength + ";" + Angle + ";" + Fold + ";" + Strafe + ";" + Height + ";";
    }

    public boolean equals(Payload p) {
        return p.Strength == Strength &&
                p.Angle == Angle &&
                p.Fold == Fold &&
                p.Strafe == Strafe &&
                p.Height == Height;
    }

    public void copy(Payload p) {
        Strength = p.Strength;
        Angle = p.Angle;
        Fold = p.Fold;
        Strafe = p.Strafe;
        Height = p.Height;
    }


}
