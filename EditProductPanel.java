import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.function.BiConsumer;
import javax.swing.*;

class EditProductPanel extends JPanel {
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField sizeField;
    private JComboBox<String> productTypeComboBox;
    private JPanel additionalFieldsPanel;
    private Product originalProduct;
    private JRadioButton maleButton, femaleButton, unisexButton;

    public EditProductPanel(Runnable backAction, BiConsumer<Product, Product> editAction) {
        setLayout(new GridLayout(10, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Edit product"));

        // product Name
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setHorizontalAlignment(JTextField.LEFT); 
        nameField.setPreferredSize(new Dimension(150, 10));
        add(nameField);

        // stock
        JLabel stockLabel= new JLabel("Stock:");
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
        priceField.setHorizontalAlignment(JTextField.LEFT); 
        priceField.setPreferredSize(new Dimension(150, 10));
        add(priceField);

        // gender selection
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        unisexButton = new JRadioButton("Unisex");

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

        add(genderLabel);
        add(genderBox);

        // product type
        JLabel prLabel = new JLabel("Product Type:");
        prLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(prLabel);
        productTypeComboBox = new JComboBox<>(new String[]{"Tshirt", "Shoes", "Jeans", "Jacket", "Hat"});
        productTypeComboBox.setEnabled(false);
        add(productTypeComboBox);
        
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(sizeLabel);
        sizeField = new JTextField();
        sizeField.setHorizontalAlignment(JTextField.LEFT); 
        sizeField.setPreferredSize(new Dimension(150, 10));
        add(sizeField);

        // fields panel
        additionalFieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        add(additionalFieldsPanel);
        
        add(Box.createVerticalStrut(10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String gender = maleButton.isSelected() ? "Male" :
                        femaleButton.isSelected() ? "Female" : "Unisex";
                String name = nameField.getText();
                int stock = Integer.parseInt(stockField.getText());
                double price = Double.parseDouble(priceField.getText());
                String size = sizeField.getText();
                Product updatedProduct = createUpdatedProduct( name, stock, price, gender,size);
                editAction.accept(originalProduct, updatedProduct);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock and price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(saveButton);

        // cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> backAction.run());
        buttonPanel.add(cancelButton);
        add(buttonPanel);
        add(Box.createVerticalStrut(10));
    }

    public void setProduct(Product product) {
        this.originalProduct = product;
        nameField.setText(product.getName());
        stockField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        sizeField.setText(String.valueOf(product.getSize()));

        // set product type
        if (product instanceof Tshirt) productTypeComboBox.setSelectedItem("Tshirt");
        else if (product instanceof Shoes) productTypeComboBox.setSelectedItem("Shoes");
        else if (product instanceof Jeans) productTypeComboBox.setSelectedItem("Jeans");
        else if (product instanceof Jacket) productTypeComboBox.setSelectedItem("Jacket");
        else if (product instanceof Hat) productTypeComboBox.setSelectedItem("Hat");

        // set gender
        String gender = product.getGender();
        if ("Male".equals(gender)) maleButton.setSelected(true);
        else if ("Female".equals(gender)) femaleButton.setSelected(true);
        else unisexButton.setSelected(true);

        updateFieldsForSelectedProductType();
    }

    private void updateFieldsForSelectedProductType() {
        additionalFieldsPanel.removeAll();
        String selectedType = (String) productTypeComboBox.getSelectedItem();

        switch (selectedType) {
            case "Tshirt":
                addField("Type:", "Polo", "Sleeveless", "Long Sleeve", "Graphic", "Crew Neck", "V-Neck");
                addField("Color:", "Red", "Blue", "Black", "White", "Green");
                break;
            case "Shoes":
                addField("Type:", "Running", "Basketball", "Soccer", "Sneaker", "Ankle Boots", "Snow Boots", "Stiletto Heels");
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
                addField("Fabric:", "Cotton", "Leather", "Denim", "Polyester", "Suede");
                break;
        }


        revalidate();
        repaint();
    }

    private void addField(String label, String... options) {
        additionalFieldsPanel.add(new JLabel(label));
        JComboBox<String> comboBox = new JComboBox<>(options);
        additionalFieldsPanel.add(comboBox);
    }

    private Product createUpdatedProduct( String name, int stock, double price, String gender,String size) {
    	String selectedType = (String) productTypeComboBox.getSelectedItem();
        String additionalField1Value = null;
        String additionalField2Value = null;

        if (additionalFieldsPanel.getComponentCount() > 0) {
            if (additionalFieldsPanel.getComponent(0) instanceof JComboBox) {
                additionalField1Value = (String) ((JComboBox) additionalFieldsPanel.getComponent(0)).getSelectedItem();
            }
        }
        if (additionalFieldsPanel.getComponentCount() > 1) {
            if (additionalFieldsPanel.getComponent(1) instanceof JComboBox) {
                additionalField2Value = (String) ((JComboBox) additionalFieldsPanel.getComponent(1)).getSelectedItem();
            }
        }
        switch (selectedType) {
        case "Tshirt":
            return new Tshirt(name, stock, price,gender,size, additionalField1Value, additionalField2Value); 
        case "Jeans":
            return new Jeans(name, stock, price,gender,size, Integer.parseInt(additionalField1Value),Integer.parseInt(additionalField2Value)); 
        case "Jacket":
            return new Jacket(name, stock, price,gender,size, additionalField1Value,additionalField2Value);
        case "Hat":
            return new Hat(name, stock, price,gender,size, additionalField1Value,additionalField2Value);
        case "Shoes":
            return new Shoes(name, stock, price,gender,size, additionalField1Value);
        default:
            return null;
    }
    }
}
