package src.utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

/**
 * A utility class for creating and applying custom borders to Swing components.
 * This custom class provides methods to set a titled border with a specific
 * color.
 *
 * @author Eric Russel M. Lopez
 * @version 23.0.1 (Java SE 23.0.1)
 * @since 2025
 */
public interface CustomBorder {

    /**
     * Sets a titled border with a custom color for the specified Component.
     *
     * @param component The Component to which the border will be applied.
     * @param title The title text to display on the border.
     * @param color The color of the border and title text.
     */
    public default void setInputBorderColor(JComponent component, String title, Color color) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(color, 1),
                title,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font(null, 1, 12),
                color
        );
        component.grabFocus();
        component.setOpaque(false);
        component.setBorder(border);
    }
}