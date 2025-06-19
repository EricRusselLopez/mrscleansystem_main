package src.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This class extends {@link DefaultTableCellRenderer} and customizes the
 * appearance of table headers by setting an image icon next to the column
 * title. Additionally, I have implemented a custom image resizer
 * {@code CustomImageResizer} to ensure that the icons fit properly within the
 * table header.
 *
 * @author Eric Russel M. Lopez
 * @version 23.0.1 (Java SE 23.0.1)
 * @since 2025
 */
public class CustomHeaderRenderer extends DefaultTableCellRenderer {

    private final String icon;

    CustomImageResizer customSizeImage = new CustomImageResizer();

    public CustomHeaderRenderer(String icon) {
        this.icon = icon;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * This method sets the column name text along with the specified icon.
     *
     * @param table the {@code JTable} this header belongs to
     * @param value the column name (text)
     * @param isSelected unused parameter (not applicable for headers)
     * @param hasFocus unused parameter (not applicable for headers)
     * @param row the row index (always -1 for headers)
     * @param column the column index of the header
     * @return a {@code JLabel} with the column name and icon
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel(value.toString(), customSizeImage.resizeIcon(new ImageIcon(icon), 20, 20), JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(label.getPreferredSize().width, 35));
        return label;
    }
}
