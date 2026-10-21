import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Color INK = new Color(47, 45, 40);
    private static final Color PAPER = new Color(245, 242, 234);
    private static final Color CARD = new Color(255, 253, 248);
    private static final Color RUST = new Color(151, 76, 49);
    private static final String[] ITEMS = {"Sword", "Plate Armor", "Chain Mail", "Cooking Pot"};
    private static final String[] MATERIALS = {"Iron", "Wood", "Leather", "Charcoal"};
    private static final int[][] RECIPE = {{3, 1, 0, 2}, {8, 0, 3, 5}, {6, 0, 0, 4}, {4, 0, 0, 2}};
    private static final String[] STATUSES = {"Queued", "In progress", "Ready for pickup"};

    private final Map<String, Integer> basket = new LinkedHashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private final JPanel orderRows = new JPanel();
    private int selectedOrder = -1;
    private final JTabbedPane tabs = new JTabbedPane();
    private final JPanel basketRows = new JPanel();
    private final JLabel basketCount = new JLabel("0 items");
    private final JLabel queueCount = new JLabel();
    private final JLabel orderDetails = new JLabel("Select an order to view its notes.");
    private final JLabel workload = new JLabel();
    private final JLabel[] materialTotals = new JLabel[MATERIALS.length];
    private final JTextField customer = new JTextField();
    private final JTextArea notes = new JTextArea(3, 18);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().open());
    }

    private void open() {
        JFrame frame = new JFrame("Gunter's Forge — Mautern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 650));
        frame.setSize(1060, 790);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(buildWindow());
        frame.setVisible(true);
    }

    private JPanel buildWindow() {
        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBackground(PAPER);
        root.setBorder(new EmptyBorder(20, 24, 22, 24));
        root.add(section("Gunter's Forge", "Handmade in the royal town of Mautern"), BorderLayout.NORTH);
        tabs.addTab("Place an order", storePage());
        tabs.addTab("Shop queue", queuePage());
        root.add(tabs, BorderLayout.CENTER);
        return root;
    }

    private JPanel storePage() {
        JPanel catalog = new JPanel(new BorderLayout(0, 10));
        catalog.setOpaque(false);
        catalog.add(section("Shop the collection", "Choose items to add to your order."), BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(2, 2, 12, 12));
        grid.setOpaque(false);
        for (String item : ITEMS) grid.add(productCard(item));
        catalog.add(grid, BorderLayout.CENTER);

        JPanel checkout = panel();
        checkout.setLayout(new BorderLayout(0, 10));
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        top.add(section("Your order", "Review items before placing it."), BorderLayout.NORTH);
        top.add(basketCount, BorderLayout.SOUTH); basketCount.setForeground(new Color(105, 98, 88));
        checkout.add(top, BorderLayout.NORTH);
        basketRows.setOpaque(false); basketRows.setLayout(new BoxLayout(basketRows, BoxLayout.Y_AXIS));
        JScrollPane basketScroll = new JScrollPane(basketRows);
        basketScroll.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(230, 225, 214)));
        basketScroll.getViewport().setBackground(CARD);
        checkout.add(basketScroll, BorderLayout.CENTER);
        checkout.add(checkoutForm(), BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, catalog, checkout);
        split.setResizeWeight(.63); split.setDividerLocation(610); split.setBorder(null);
        split.setOpaque(false); split.setContinuousLayout(true);
        JPanel page = new JPanel(new BorderLayout()); page.setOpaque(false);
        page.setBorder(new EmptyBorder(14, 0, 0, 0)); page.add(split);
        return page;
    }

    private JPanel productCard(String item) {
        JPanel card = panel(); card.setLayout(new BorderLayout(0, 8));
        card.add(new ProductArt(item), BorderLayout.CENTER);
        JPanel footer = new JPanel(new BorderLayout(8, 5)); footer.setOpaque(false);
        JPanel labels = new JPanel(new GridLayout(0, 1, 0, 3)); labels.setOpaque(false);
        JLabel name = new JLabel(item); name.setFont(new Font("SansSerif", Font.BOLD, 14)); name.setForeground(INK);
        JLabel desc = new JLabel(description(item)); desc.setFont(new Font("SansSerif", Font.PLAIN, 10)); desc.setForeground(new Color(111, 105, 95));
        labels.add(name); labels.add(desc);
        JButton add = new JButton("Add"); add.setBackground(Color.BLACK); add.setForeground(Color.WHITE); add.setFocusPainted(false);
        add.setOpaque(true); add.setContentAreaFilled(true); add.setBorderPainted(false);
        add.addActionListener(e -> adjustBasket(item, 1));
        footer.add(labels, BorderLayout.CENTER); footer.add(add, BorderLayout.EAST);
        card.add(footer, BorderLayout.SOUTH);
        return card;
    }

    private JPanel checkoutForm() {
        JPanel form = new JPanel(new GridBagLayout()); form.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0, 0, 8, 0);
        addField(form, c, "Customer name", customer);
        JComboBox<String> finish = new JComboBox<>(new String[]{"Gunter's recommendation", "Polished iron", "Darkened steel", "Brass details"});
        c.gridy++; addField(form, c, "Finish", finish);
        JComboBox<String> timing = new JComboBox<>(new String[]{"Standard", "Rush request"});
        c.gridy++; addField(form, c, "Timing", timing);
        notes.setLineWrap(true); notes.setWrapStyleWord(true);
        c.gridy++; addField(form, c, "Notes for Gunter", new JScrollPane(notes));
        JButton place = new JButton("Place order"); place.setBackground(Color.BLACK); place.setForeground(Color.WHITE);
        place.setOpaque(true); place.setContentAreaFilled(true); place.setBorderPainted(false);
        place.setFocusPainted(false); place.setFont(new Font("SansSerif", Font.BOLD, 13));
        place.setBorder(new EmptyBorder(10, 12, 10, 12));
        place.addActionListener(e -> placeOrder((String) finish.getSelectedItem(), (String) timing.getSelectedItem()));
        c.gridy++; c.insets = new Insets(2, 0, 0, 0); form.add(place, c);
        return form;
    }

    private JPanel queuePage() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(CARD);
        content.setBorder(new EmptyBorder(18, 18, 18, 18));
        content.add(section("Shop queue", "Update progress as commissions move through the forge."));
        content.add(Box.createVerticalStrut(8));
        queueCount.setFont(new Font("SansSerif", Font.BOLD, 12));
        queueCount.setForeground(new Color(92, 87, 77));
        queueCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(queueCount);
        content.add(Box.createVerticalStrut(12));
        JLabel orderHeading = new JLabel("Orders — click one to select it");
        orderHeading.setFont(new Font("SansSerif", Font.BOLD, 14));
        orderHeading.setForeground(INK);
        orderHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(orderHeading);
        content.add(Box.createVerticalStrut(6));
        orderRows.setLayout(new BoxLayout(orderRows, BoxLayout.Y_AXIS));
        orderRows.setBackground(CARD);
        JScrollPane orderScroll = new JScrollPane(orderRows);
        orderScroll.setPreferredSize(new Dimension(700, 150));
        orderScroll.setMinimumSize(new Dimension(100, 100));
        orderScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        orderScroll.getViewport().setBackground(CARD);
        orderScroll.setBackground(CARD);
        content.add(orderScroll);
        content.add(Box.createVerticalStrut(10));

        JComboBox<String> status = new JComboBox<>(STATUSES);
        JButton update = new JButton("Update status"); update.addActionListener(e -> updateStatus((String) status.getSelectedItem()));
        JButton remove = new JButton("Remove order"); remove.addActionListener(e -> removeOrder());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); actions.setOpaque(false);
        actions.add(status); actions.add(update); actions.add(remove);
        JPanel prep = materialPanel();
        orderDetails.setForeground(new Color(105, 99, 89));
        orderDetails.setFont(new Font("SansSerif", Font.PLAIN, 11));
        workload.setOpaque(true); workload.setBackground(new Color(235, 238, 229));
        workload.setForeground(new Color(69, 82, 60)); workload.setBorder(new EmptyBorder(10, 12, 10, 12));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(actions);
        orderDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(orderDetails);
        prep.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(prep);
        content.add(Box.createVerticalStrut(9));
        workload.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(workload);
        JPanel page = new JPanel(new BorderLayout());
        JScrollPane pageScroll = new JScrollPane(content);
        pageScroll.setBorder(null);
        pageScroll.getViewport().setBackground(CARD);
        page.add(pageScroll, BorderLayout.CENTER);
        refreshQueue();
        return page;
    }

    private JPanel materialPanel() {
        JPanel box = new JPanel(new BorderLayout(0, 8)); box.setOpaque(false);
        box.add(section("Material prep", "Estimated planning units for queued work."), BorderLayout.NORTH);
        JPanel cells = new JPanel(new GridLayout(1, MATERIALS.length, 8, 0)); cells.setOpaque(false);
        for (int i = 0; i < MATERIALS.length; i++) {
            JPanel cell = new JPanel(new GridLayout(0, 1, 0, 3));
            cell.setBackground(new Color(241, 237, 228)); cell.setBorder(new EmptyBorder(9, 10, 9, 10));
            JLabel label = new JLabel(MATERIALS[i]); label.setForeground(new Color(108, 102, 92));
            materialTotals[i] = new JLabel("0"); materialTotals[i].setFont(new Font("Serif", Font.BOLD, 19));
            cell.add(label); cell.add(materialTotals[i]); cells.add(cell);
        }
        box.add(cells, BorderLayout.CENTER); return box;
    }

    private void adjustBasket(String item, int change) {
        int qty = basket.getOrDefault(item, 0) + change;
        if (qty <= 0) basket.remove(item); else basket.put(item, qty);
        refreshBasket();
    }

    private void refreshBasket() {
        basketRows.removeAll();
        int total = basket.values().stream().mapToInt(Integer::intValue).sum();
        basketCount.setText(total + (total == 1 ? " item" : " items"));
        if (basket.isEmpty()) {
            JLabel empty = new JLabel("Your basket is empty."); empty.setForeground(new Color(120, 114, 104));
            empty.setBorder(new EmptyBorder(14, 4, 14, 4)); basketRows.add(empty);
        } else basket.forEach((item, qty) -> {
            JPanel line = new JPanel(new BorderLayout(6, 0)); line.setOpaque(false); line.setBorder(new EmptyBorder(6, 2, 6, 2));
            JLabel label = new JLabel(item + "  ×  " + qty); label.setFont(new Font("SansSerif", Font.BOLD, 12));
            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0)); buttons.setOpaque(false);
            JButton minus = new JButton("−"), plus = new JButton("+");
            minus.addActionListener(e -> adjustBasket(item, -1)); plus.addActionListener(e -> adjustBasket(item, 1));
            buttons.add(minus); buttons.add(plus); line.add(label, BorderLayout.CENTER); line.add(buttons, BorderLayout.EAST);
            basketRows.add(line);
        });
        basketRows.revalidate(); basketRows.repaint();
    }

    private void placeOrder(String finish, String timing) {
        String name = customer.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(null, "Enter your name before placing the order."); customer.requestFocus(); return; }
        if (basket.isEmpty()) { JOptionPane.showMessageDialog(null, "Add at least one item to your order."); return; }
        orders.add(new Order(name, new LinkedHashMap<>(basket), finish, timing, notes.getText().trim()));
        basket.clear(); customer.setText(""); notes.setText(""); refreshBasket(); refreshQueue();
        tabs.setSelectedIndex(1);
        selectedOrder = orders.size() - 1;
        refreshQueue();
        JOptionPane.showMessageDialog(null, "Your order has been added to Gunter's queue.", "Order placed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus(String status) {
        int row = selectedOrder;
        if (row < 0) { JOptionPane.showMessageDialog(null, "Select an order first."); return; }
        orders.get(row).status = status; refreshQueue();
    }

    private void removeOrder() {
        int row = selectedOrder;
        if (row < 0) { JOptionPane.showMessageDialog(null, "Select an order first."); return; }
        orders.remove(row); selectedOrder = -1; refreshQueue();
    }

    private void refreshQueue() {
        orderRows.removeAll();
        int open = 0, ready = 0, rush = 0;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            final int index = i;
            JButton row = new JButton("#" + (i + 1) + "   " + order.customer + "   |   "
                    + itemText(order.items) + "   |   " + order.timing + "   |   " + order.status);
            row.setHorizontalAlignment(SwingConstants.LEFT);
            row.setFont(new Font("SansSerif", Font.PLAIN, 13));
            row.setOpaque(true);
            row.setContentAreaFilled(true);
            row.setFocusPainted(false);
            row.setBorder(new EmptyBorder(12, 14, 12, 14));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            row.setForeground(selectedOrder == i ? Color.WHITE : INK);
            row.setBackground(selectedOrder == i ? INK : (i % 2 == 0 ? CARD : PAPER));
            row.addActionListener(e -> { selectedOrder = index; refreshQueue(); });
            orderRows.add(row);
            if (order.status.equals("Ready for pickup")) ready++;
            else { open++; if (order.timing.equals("Rush request")) rush++; }
        }
        orderRows.revalidate();
        orderRows.repaint();
        queueCount.setText(open + " open   ·   " + ready + " ready   ·   " + rush + " rush");
        int[] total = new int[MATERIALS.length];
        for (Order order : orders) if (order.status.equals("Queued")) order.items.forEach((item, qty) -> addMaterials(total, item, qty));
        for (int i = 0; i < total.length; i++) materialTotals[i].setText(Integer.toString(total[i]));
        int itemsOpen = orders.stream().filter(o -> !o.status.equals("Ready for pickup"))
                .mapToInt(o -> o.items.values().stream().mapToInt(Integer::intValue).sum()).sum();
        workload.setText(itemsOpen == 0 ? "No outstanding work. The forge is ready for new commissions."
                : itemsOpen <= 3 ? "Light workload · " + itemsOpen + " items outstanding."
                : itemsOpen <= 7 ? "Steady workload · " + itemsOpen + " items outstanding."
                : "Busy forge · " + itemsOpen + " items outstanding. Review rush requests and pickup expectations.");
        showDetails();
    }

    private void addMaterials(int[] total, String item, int qty) {
        int index = java.util.Arrays.asList(ITEMS).indexOf(item);
        if (index >= 0) for (int i = 0; i < total.length; i++) total[i] += RECIPE[index][i] * qty;
    }

    private void showDetails() {
        if (selectedOrder < 0 || selectedOrder >= orders.size()) {
            orderDetails.setText("Select an order to view its finish and notes."); return;
        }
        Order o = orders.get(selectedOrder);
        orderDetails.setText("Finish: " + o.finish + "   ·   Notes: " + (o.notes.isEmpty() ? "None" : o.notes));
    }

    private JPanel section(String title, String subtitle) {
        JPanel box = new JPanel(new GridLayout(0, 1, 0, 4)); box.setOpaque(false);
        JLabel heading = new JLabel(title); heading.setFont(new Font("Serif", Font.BOLD, 20)); heading.setForeground(INK);
        JLabel detail = new JLabel(subtitle); detail.setFont(new Font("SansSerif", Font.PLAIN, 11)); detail.setForeground(new Color(111, 105, 95));
        box.add(heading); box.add(detail); return box;
    }

    private JPanel panel() {
        JPanel box = new JPanel(); box.setBackground(CARD);
        box.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225, 219, 206)), new EmptyBorder(13, 13, 13, 13)));
        return box;
    }

    private void addField(JPanel form, GridBagConstraints c, String label, JComponent component) {
        JPanel field = new JPanel(new BorderLayout(0, 4)); field.setOpaque(false);
        JLabel caption = new JLabel(label); caption.setFont(new Font("SansSerif", Font.BOLD, 11)); caption.setForeground(INK);
        field.add(caption, BorderLayout.NORTH); field.add(component, BorderLayout.CENTER); form.add(field, c);
    }

    private String description(String item) {
        switch (item) {
            case "Sword": return "Balanced hand-forged steel";
            case "Plate Armor": return "Fitted protection";
            case "Chain Mail": return "Flexible linked rings";
            default: return "Made for the hearth";
        }
    }

    private String itemText(Map<String, Integer> items) {
        StringBuilder text = new StringBuilder();
        items.forEach((name, qty) -> { if (text.length() > 0) text.append(", "); text.append(name).append(" × ").append(qty); });
        return text.toString();
    }

    private static class Order {
        final String customer, finish, timing, notes;
        final Map<String, Integer> items;
        String status = "Queued";
        Order(String customer, Map<String, Integer> items, String finish, String timing, String notes) {
            this.customer = customer; this.items = items; this.finish = finish; this.timing = timing; this.notes = notes;
        }
    }
}
