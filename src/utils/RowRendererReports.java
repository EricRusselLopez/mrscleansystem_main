package src.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.json.JSONObject;

import src.main.java.auth.session.java.FetchUser;

public class RowRendererReports extends DefaultTableCellRenderer {
    JSONObject user = FetchUser.getUserData();
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
    ) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column
        );

        label.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        label.setOpaque(true);
        label.setFont(label.getFont().deriveFont(Font.BOLD));

        Object statusValue = table.getValueAt(row, 9);
        String status = (statusValue != null) ? statusValue.toString().trim().toLowerCase() : "";

        label.setForeground(Color.BLACK);

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
        } else {
            switch (status) {
                case "pending" -> {
                    label.setBackground(new Color(255, 243, 205));
                    label.setForeground(new Color(133, 100, 4));
                }
                case "canceled" -> {
                    label.setBackground(new Color(248, 215, 218));
                    label.setForeground(new Color(114, 28, 36));
                }
                case "pickup" -> {
                    label.setBackground(new Color(209, 236, 241));
                    label.setForeground(new Color(12, 84, 96));
                }
                case "delivered" -> {
                    label.setBackground(new Color(212, 237, 218));
                    label.setForeground(new Color(21, 87, 36));
                }
                case "paid" -> {
                    label.setBackground(new Color(209, 242, 235));
                    if ("employee".equals(user.optString("role"))) {
                         label.setBackground(new Color(209, 242, 235, 100));
                         label.setForeground(new Color(128, 128, 128, 180));
                    } else {
                         label.setBackground(new Color(209, 242, 235));
                         label.setForeground(new Color(17, 120, 100));
                    }
                }
                default -> {
                    label.setBackground(table.getBackground());
                    label.setForeground(Color.BLACK);
                }
            }
        }

        return label;
    }
}
