import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ClothingChainManagement extends JFrame {
    private Brand brand;
    private DefaultTableModel tableModel;
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel brandNameLabel;
    private JLabel profitLabel;

    public ClothingChainManagement(String brandName) {
        // frame settings
        setTitle("Clothing Chain Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        brand = new Brand(brandName);

        // card layout panel
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // product list panel
        JPanel productListPanel = createProductListPanel();
        mainContentPanel.add(productListPanel, "ProductList");

        // add product panel
        AddProductPanel addProductPanel = new AddProductPanel(
            () -> {
                cardLayout.show(mainContentPanel, "ProductList");
            },
            product -> {
                brand.addProduct(product);
                refreshTable();
                cardLayout.show(mainContentPanel, "ProductList");
            }, brand
        );
        mainContentPanel.add(addProductPanel, "AddProduct");

        // edit product panel
        EditProductPanel editProductPanel = new EditProductPanel(
            () -> {
                cardLayout.show(mainContentPanel, "ProductList");
            },
            (originalProduct, updatedProduct) -> {
                brand.updateProduct(originalProduct, updatedProduct);
                refreshTable();
                cardLayout.show(mainContentPanel, "ProductList");
            }
        );
        mainContentPanel.add(editProductPanel, "EditProduct");

        // sell product panel
        SellProductPanel sellProductPanel = new SellProductPanel(
            () -> {
                cardLayout.show(mainContentPanel, "ProductList");
            },
            productName -> {
                refreshTable();
            },
            brand
        );
        mainContentPanel.add(sellProductPanel, "SellProduct");

        // main panel
        add(mainContentPanel, BorderLayout.CENTER);

        cardLayout.show(mainContentPanel, "ProductList");
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Products"));

        // action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setPreferredSize(new Dimension(200, 0));

        JButton addProductButton = new JButton("Add product");
        addProductButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "AddProduct");
        });
        actionPanel.add(addProductButton);

        actionPanel.add(Box.createVerticalStrut(10));

        JButton refreshButton = new JButton("Refresh table");
        refreshButton.addActionListener(e -> refreshTable());
        actionPanel.add(refreshButton);
        actionPanel.add(Box.createVerticalStrut(10));

        JButton sellProductButton = new JButton("Sell");
        sellProductButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "SellProduct");
        });
        actionPanel.add(sellProductButton);

        // brand panel
        JPanel brandPanel = createBrandPanel();
        actionPanel.add(Box.createVerticalStrut(50));
        actionPanel.add(brandPanel);

        panel.add(actionPanel, BorderLayout.WEST);

        // table
        tableModel = new DefaultTableModel(new String[]{"Name", "Stock", "Price", "Type", "Gender", "Size", "Delete", "Edit"}, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 6; 
            }
        };

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(tableModel, brand, this::refreshTable, "delete", null));
        table.getColumn("Edit").setCellEditor(new ButtonEditor(tableModel, brand, this::refreshTable, "edit",
            product -> {
                ((EditProductPanel) mainContentPanel.getComponent(2)).setProduct(product);
                cardLayout.show(mainContentPanel, "EditProduct");
            }
        ));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        refreshTable();
        return panel;
    }

    private JPanel createBrandPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Marka Bilgileri"));

        brandNameLabel = new JLabel("Brand: " + brand.getBrandName());
        profitLabel = new JLabel("Current profit: " + brand.getProfit());

        panel.add(brandNameLabel);
        panel.add(profitLabel);

        return panel;
    }


    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product product : brand.getProducts()) {
            tableModel.addRow(new Object[]{
                product.getName(),
                product.getStock(),
                product.getPrice(),
                product.getClass().getSimpleName(),
                product.getGender(),
                product.getSize(),
                "Delete",
                "Edit"
            });
        }
        updateProfit();
    }

    private void updateProfit() {
        profitLabel.setText("Current profit: " + brand.getProfit());
    }
}

class ButtonRenderer extends DefaultTableCellRenderer {
    private JButton button;

    public ButtonRenderer() {
        button = new JButton();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        button.setText(value != null ? value.toString() : "");
        return button;
    }
}

// button editor class
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String action;
    private int selectedRow;
    private DefaultTableModel tableModel;
    private Brand brand;
    private Runnable refreshTable;
    private Consumer<Product> editAction;

    public ButtonEditor(DefaultTableModel tableModel, Brand brand, Runnable refreshTable, String action, Consumer<Product> editAction) {
        super(new JTextField());
        this.tableModel = tableModel;
        this.brand = brand;
        this.refreshTable = refreshTable;
        this.action = action;
        this.editAction = editAction;

        button = new JButton();
        button.addActionListener(e -> performAction());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row;
        button.setText(value != null ? value.toString() : "");
        return button;
    }

    private void performAction() {
        Product product = brand.getProducts().get(selectedRow);
        
        if (action.equals("delete")) {
            brand.getProducts().remove(product);
            refreshTable.run();
        } else if (action.equals("edit") && editAction != null) {
            editAction.accept(product);
        }
        fireEditingStopped();
    }

}
