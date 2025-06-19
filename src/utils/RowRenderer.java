package src.utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
    ) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column
        );

        label.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        Object statusValue = table.getValueAt(row, 5);
        String status = (statusValue != null) ? statusValue.toString() : "";

        label.setForeground(Color.BLACK);

        if ("requested".equalsIgnoreCase(status)) {
            label.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        } else if ("approved".equalsIgnoreCase(status)) {
            label.setBackground(isSelected ? table.getSelectionBackground() : Color.CYAN);
        } else {
            label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        return label;
    }
}
