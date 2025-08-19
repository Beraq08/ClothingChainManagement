import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JTextField brandNameField;
    private JButton loginButton;

    public LoginPanel(Consumer<String> onLogin) {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Login"));

        // brand name
        JLabel brandNameLabel = new JLabel("Marka Adı:");
        brandNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(brandNameLabel);
        brandNameField = new JTextField();
        brandNameField.setHorizontalAlignment(JTextField.LEFT);
        add(brandNameField);

        // login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String brandName = brandNameField.getText();
            if (brandName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brand name cannot be left blank!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                onLogin.accept(brandName);
            }
        });
        add(loginButton);
    }
}
