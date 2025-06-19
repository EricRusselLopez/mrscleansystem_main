package src.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowRendererInventory extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
    ) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column
        );

        label.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setForeground(Color.BLACK);

        Object statusValue = table.getValueAt(row, 4);
        String status = (statusValue != null) ? statusValue.toString() : "";

        label.setForeground(Color.BLACK);

        if ("low".equalsIgnoreCase(status)) {
            label.setBackground(isSelected ? table.getSelectionBackground() : new Color(247, 147, 59));
        } else if ("normal".equalsIgnoreCase(status)) {
            label.setBackground(isSelected ? table.getSelectionBackground() : new Color(185, 217, 30));
        } else if ("out of stock".equalsIgnoreCase(status)) {
            label.setBackground(isSelected ? table.getSelectionBackground() : new Color(242, 104, 104));
        }
         else {
            label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        return label;
    }
}
