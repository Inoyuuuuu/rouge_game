package org.example.model;

public class Cell {
    private char content;
    private int belongsToChamberNumber;
    private CellType celltype;

    public Cell(char content, CellType celltype, int belongsToChamberNumber) {
        this.content = content;
        this.celltype = celltype;
        this.belongsToChamberNumber = belongsToChamberNumber;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public CellType getCelltype() {
        return celltype;
    }

    public void setCellType(CellType celltype) {
        this.celltype = celltype;
    }

    public int getBelongsToChamberNumber() {
        return belongsToChamberNumber;
    }

    public void setBelongsToChamberNumber(int belongsToChamberNumber) {
        this.belongsToChamberNumber = belongsToChamberNumber;
    }
}
