package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {

    private char[] symbols = {'#', '$', '@', '%', '*', '+', '-', '.'};

    public void setSymbols(char[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public char convert(int color) {
        return this.symbols[(int) Math.floor(color / 256. * symbols.length)];
    }

}


