import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class LoginPage extends JFrame {
    private JTextField brandNameField;
    private JButton loginButton;

    public LoginPage(Consumer<String> onLogin) {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 2, 10, 10));

        // brand name
        JLabel brandNameLabel = new JLabel("Brand name:");
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
                dispose(); 
            }
        });
        add(loginButton);
    }
}
