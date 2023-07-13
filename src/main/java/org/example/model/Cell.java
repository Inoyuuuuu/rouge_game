package org.example.model;

public class Cell {
    private char content;
    private CellType celltype;

    public Cell(char content, CellType celltype) {
        this.content = content;
        this.celltype = celltype;
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

    public void setCelltype(CellType celltype) {
        this.celltype = celltype;
    }
}
