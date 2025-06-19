package src.utils;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A utility class for resizing images represented as {@link ImageIcon}. This
 * class provides methods to scale an image to a specified width and height
 * while maintaining its aspect ratio or custom dimensions.
 * <p>
 * This custom class is useful for resizing icons used in buttons, labels, or
 * other UI components.
 * </p>
 *
 * @author Eric Russel M. Lopez
 * @version 24.0.1 (Java SE 24.0.1)
 * @since 2025
 */
public class CustomImageResizer {

    /**
     * Resizes the given ImageIcon to the specified width and height.
     *
     * @param icon The original ImageIcon to be resized.
     * @param width The desired width of the resized icon.
     * @param height The desired height of the resized icon.
     * @return A new ImageIcon with the specified dimensions.
     */
    public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}