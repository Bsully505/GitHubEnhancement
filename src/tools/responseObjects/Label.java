package github.tools.responseObjects;

import java.awt.*;

public class Label {
    private String name;
    private String description;
    private Color color;

    public Label(String name, String description, Color color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Color getColor() {
        return color;
    }
}
