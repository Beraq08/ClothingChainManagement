import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage(brandName -> {
                ClothingChainManagement app = new ClothingChainManagement(brandName);
                app.setVisible(true);
            });
            loginPage.setVisible(true);
        });
    }
}
