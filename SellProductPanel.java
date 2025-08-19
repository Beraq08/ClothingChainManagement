import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class SellProductPanel extends JPanel {
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private JLabel stockLabel;
    private JLabel priceLabel;
    private JButton sellButton;
    private JButton cancelButton;
    private Brand brand;
    private Runnable onCancel;

    public SellProductPanel(Runnable onCancel, Consumer<String> onSell, Brand brand) {
        this.onCancel = onCancel;
        this.brand = brand;
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Sell"));

        // choosing product
        JLabel productLabel = new JLabel("Product:");
        productLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(productLabel);
        productComboBox = new JComboBox<>();
        productComboBox.addActionListener(e -> updateProductDetails());
        add(productComboBox);

        // stock
        JLabel stockTextLabel = new JLabel("Stock:");
        stockTextLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(stockTextLabel);
        stockLabel = new JLabel();
        stockLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(stockLabel);

        // price
        JLabel priceTextLabel = new JLabel("Price:");
        priceTextLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(priceTextLabel);
        priceLabel = new JLabel();
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(priceLabel);

        // quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(quantityLabel);
        quantityField = new JTextField();
        quantityField.setHorizontalAlignment(JTextField.LEFT);
        add(quantityField);

        // sell button
        sellButton = new JButton("Sell");
        sellButton.addActionListener(e -> {
            String productName = (String) productComboBox.getSelectedItem();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                String result = brand.sellProduct(productName, quantity);
                JOptionPane.showMessageDialog(this, result, "Sales result", JOptionPane.INFORMATION_MESSAGE);
                onSell.accept(productName);
                onCancel.run(); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(sellButton);

        // cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancel.run());
        add(cancelButton);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            refreshProductList();
        }
    }

    private void refreshProductList() {
        productComboBox.removeAllItems();
        for (Product product : brand.getProducts()) {
            productComboBox.addItem(product.getName());
        }
        updateProductDetails();
    }

    private void updateProductDetails() {
        String selectedProductName = (String) productComboBox.getSelectedItem();
        if (selectedProductName != null) {
            for (Product product : brand.getProducts()) {
                if (product.getName().equals(selectedProductName)) {
                    stockLabel.setText(String.valueOf(product.getStock()));
                    priceLabel.setText(String.valueOf(product.getPrice()));
                    break;
                }
            }
        } else {
            stockLabel.setText("");
            priceLabel.setText("");
        }
    }
}
