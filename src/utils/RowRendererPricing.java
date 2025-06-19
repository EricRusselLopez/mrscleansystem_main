package src.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class RowRendererPricing extends DefaultTableCellRenderer {

    private final TableCellRenderer defaultCheckboxRenderer = new JTable().getDefaultRenderer(Boolean.class);

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component c;
        if (value instanceof Boolean) {
            c = defaultCheckboxRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (c instanceof JCheckBox) {
                ((JCheckBox) c).setHorizontalAlignment(SwingConstants.CENTER);
            }
        } else {
            c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
            ((JLabel) c).setFont(new Font(null, Font.BOLD, 13));
            ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
        }
        applyRowColors(table, row, c, isSelected);

        return c;
    }

    private void applyRowColors(JTable table, int row, Component c, boolean isSelected) {
        Object statusValue = table.getValueAt(row, 3);
        String status = (statusValue != null) ? statusValue.toString() : "";

        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        } else {
            switch (status) {
                case "true" -> {
                    c.setBackground(new Color(212, 237, 218));
                    c.setForeground(new Color(21, 87, 36));
                }
                case "false" -> {
                    c.setBackground(new Color(248, 215, 218));
                    c.setForeground(new Color(114, 28, 36));
                }
                default -> {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
            }
        }
    }
}
