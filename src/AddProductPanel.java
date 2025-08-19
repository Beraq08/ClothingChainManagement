import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*; // Required for Consumer

public class AddProductPanel extends JPanel {
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField sizeField;
    private JComboBox<String> productTypeComboBox; 
    private JPanel additionalFieldsPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private Brand brand;

    public AddProductPanel(Runnable onCancel, Consumer<Product> onSave, Brand brand) {
        this.brand = brand;
        setLayout(new GridLayout(10, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Add New Product"));

        // product name
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setHorizontalAlignment(JTextField.LEFT); 
        nameField.setPreferredSize(new Dimension(150, 10));
        add(nameField);

        // stock
        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stockLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(stockLabel);
        stockField = new JTextField();
        stockField.setHorizontalAlignment(JTextField.LEFT); 
        stockField.setPreferredSize(new Dimension(150, 10));
        add(stockField);

        // price
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(priceLabel);
        priceField = new JTextField();
        priceField.setHorizontalAlignment(JTextField.LEFT); // align text left
        priceField.setPreferredSize(new Dimension(150, 10));
        add(priceField);
        
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton unisexButton = new JRadioButton("Unisex");

        // button group
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(unisexButton);

        Box genderBox = Box.createHorizontalBox();
        genderBox.add(maleButton);
        genderBox.add(femaleButton);
        genderBox.add(unisexButton);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genderLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // gender label and radio buttons
        add(genderLabel);
        add(genderBox);
       
        JLabel prLabel = new JLabel("Product Type:");
        prLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(prLabel);
        productTypeComboBox = new JComboBox<>(new String[] {"Tshirt", "Jacket", "Jeans","Hat","Shoes"});
        productTypeComboBox.addActionListener(e -> updateFieldsForSelectedProductType());
        add(productTypeComboBox);
        
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(sizeLabel);
        sizeField = new JTextField();
        sizeField.setHorizontalAlignment(JTextField.LEFT); 
        sizeField.setPreferredSize(new Dimension(150, 10));
        add(sizeField);
        
        additionalFieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        add(additionalFieldsPanel);
        
        add(Box.createVerticalStrut(10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        // save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String size = sizeField.getText();
            String gender;
            if (maleButton.isSelected()) {
                gender = "Male";
            } else if (femaleButton.isSelected()) {
                gender = "Female";
            } else {
                gender = "Unisex";
            }
            try {
                int stock = Integer.parseInt(stockField.getText());
                double price = Double.parseDouble(priceField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter product name!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (brand.isproductexist(name)) {
                    JOptionPane.showMessageDialog(this, "There are products with the same name!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (price < 0) {
                    JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (size.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter size!.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Product product = createProduct(name, stock, price, gender, size);
                    if (product != null) {
                        onSave.accept(product);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock and price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(saveButton);
        

        // cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancel.run());
        
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        add(Box.createVerticalStrut(10));
        
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            clearFields(); 
        }
    }
    private void clearFields() {
        nameField.setText("");
        stockField.setText("");
        priceField.setText("");
        sizeField.setText("");
        productTypeComboBox.setSelectedIndex(0);
        additionalFieldsPanel.removeAll();
        revalidate();
        repaint();
    }
    private void updateFieldsForSelectedProductType() {
        additionalFieldsPanel.removeAll();
        String selectedType = (String) productTypeComboBox.getSelectedItem();

        switch (selectedType) {
            case "Tshirt":
                addField("Type:", "Polo", "Sleeveless", "Long Sleeve","Graphic","Crew Neck","V-Neck");
                addField("Color:", "Red", "Blue", "Black", "White", "Green");
                break;
            case "Shoes":
                addField("Type:", "Running", "Basketball", "Soccer", "Sneaker", "Ankle Boots","Snow Boots","Stiletto Heels");
                addField("Size:", "36", "37", "38", "39", "40");
                break;
            case "Jeans":
                addField("Waist Size:", "28", "30", "32", "34", "36");
                addField("Length:", "28", "30", "32", "34", "36");
                break;
            case "Jacket":
                addField("Color:", "Red", "Blue", "Black", "Grey", "Green");
                addField("Material:", "Cotton", "Leather", "Denim", "Suit");
                break;
            case "Hat":
                addField("Type:", "Fitted", "Beret", "Boater", "Baseball", "Cap");
                addField("Fabric:", "Cotton", "Leather", "Denim", "Polyester","Suede");
                break;
        }
        revalidate();
        repaint();
    }

    private void addField(String labelText, String... options) {
        JLabel addLabel = new JLabel(labelText);
        add(addLabel);
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setAlignmentX(RIGHT_ALIGNMENT);
        additionalFieldsPanel.add(addLabel);
        additionalFieldsPanel.add(comboBox);
    }

    private Product createProduct(String name, int stock, double price,String gender,String size) {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        String additionalField1Value = null;
        String additionalField2Value = null;

        for (Component component : additionalFieldsPanel.getComponents()) {
            if (component instanceof JComboBox) {
                JComboBox comboBox = (JComboBox) component;
                if (additionalField1Value == null) {
                    additionalField1Value = (String) comboBox.getSelectedItem();
                } else {
                    additionalField2Value = (String) comboBox.getSelectedItem();
                }
            }
        }

        if (additionalField1Value == null || additionalField1Value.isEmpty()) {
            return null;
        }

        switch (selectedType) {
            case "Tshirt":
                return new Tshirt(name, stock, price, gender, size, additionalField1Value, additionalField2Value); 
            case "Jeans":
                if (additionalField2Value == null || additionalField2Value.isEmpty()) {
                    return null;
                }
                return new Jeans(name, stock, price, gender, size, Integer.parseInt(additionalField1Value), Integer.parseInt(additionalField2Value));
            case "Jacket":
                if (additionalField2Value == null || additionalField2Value.isEmpty()) {
                    return null;
                }
                return new Jacket(name, stock, price, gender, size, additionalField1Value, additionalField2Value);
            case "Hat":
                if (additionalField2Value == null || additionalField2Value.isEmpty()) {
                    return null;
                }
                return new Hat(name, stock, price, gender, size, additionalField1Value, additionalField2Value);
            case "Shoes":
                return new Shoes(name, stock, price, gender, size, additionalField1Value);
            default:
                return null;
        }
    }
}


