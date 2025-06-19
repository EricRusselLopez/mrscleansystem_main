package src.main.java;
import javax.swing.SwingUtilities;

/**
 * Entry point for the Laundry Management & Monitoring System.
 * <p>
 * This class initializes the graphical user interface (GUI) using
 * {@code SwingUtilities} to ensure thread safety.
 * </p>
 *
 * <p><strong>⚠️ IMPORTANT NOTICE:</strong></p>
 * <ul>
 *   <li>Please <strong>read the <code>README.md</code> file</strong> carefully before running this project.</li>
 *   <li>It contains <strong>important configuration instructions</strong> required to ensure smooth execution.</li>
 * </ul>
 *
 * @developer Eric Russel M. Lopez
 * @version 24 (Java SE 24)
 * @since 2025
 * @license Educational Project Use Only - © 2025 Eric Russel M. Lopez
 * @see <a href="https://github.com/EricRusselLopez/mrscleansystem">GitHub Repository</a>
 */


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
