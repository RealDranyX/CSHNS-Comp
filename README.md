## Gunter's Forge

Gunter's Forge is a native Java desktop application for taking blacksmith orders and keeping track of work in the shop. Customers can choose products, adjust quantities, provide order details, and add the order to Gunter's queue. The shop view tracks order status and estimates the materials needed for work that has not started yet.

The project uses Java Swing for the interface and Java2D for the product illustrations. It has no HTML, CSS, JavaScript, external images, or third-party libraries. The source is kept small and organized so the team can make changes quickly during the competition.

## AI use disclosure

AI tools were used to help develop and refine the application's user interface (UI). The team created the core application mechanisms, including order handling, queue management, and material planning. This version runs those features locally in the Java application and does not have a separate server backend. The team should review the implementation, understand how it works, and be prepared to explain or modify it during the competition. No AI-generated code comments are included.

## Requirements

- JDK 8 or newer
- macOS, Windows, or Linux with a desktop environment

## Run the application

### macOS launcher

1. Download and extract the full project ZIP.
2. Open `Gunter's Forge.app` or double-click `Run Gunter's Forge.command`.
3. The application window opens with **Place an order** and **Shop queue** tabs.

The launcher requires Java to be installed. Keep the project files together when extracting the ZIP.

### Compile and run from source

Open a terminal in the project folder and run:

```sh
mkdir -p out
javac -d out src/Main.java src/ProductArt.java
java -cp out Main
```

To create a runnable JAR after compiling:

```sh
jar --create --file GunterForge.jar --main-class Main -C out .
```

Run it with:

```sh
java -jar GunterForge.jar
```

## Using the app

### Place an order

The **Place an order** tab shows a product catalog and a checkout panel. Select **Add** on a product card to add one unit to the basket. Use the plus and minus controls in the basket to adjust quantities. The form records the customer's name, finish preference, timing request, and optional notes.

The four available products are:

- Sword
- Plate Armor
- Chain Mail
- Cooking Pot

To submit an order, enter a customer name and add at least one item. A confirmation appears when the order is placed, and the basket resets for the next customer. Rush requests are marked for Gunter to review; the app does not promise a completion time.

### Manage the shop queue

Open the **Shop queue** tab to see orders in the order they were placed. Select an order and choose a status to move it through **Queued**, **In progress**, and **Ready for pickup**. Orders can also be removed. The summary shows the number of open, ready, and rush orders, and the workload message reflects outstanding item quantities.

### Prepare materials

The material panel totals planning units for queued work. It includes iron, wood, leather, and charcoal. Work that has moved to **In progress** is excluded from the prep totals, and orders marked **Ready for pickup** do not count as outstanding work.

The sample recipes are defined near the top of `src/Main.java` in the `RECIPE` array. Rows match the `ITEMS` array, and columns match the `MATERIALS` array. The numbers are demonstration estimates rather than measured weights, so adjust them to match the shop's real recipes.

## Source files

```text
Gunter's Forge/
├── src/
│   ├── Main.java
│   └── ProductArt.java
├── out/                         compiled classes after building
├── GunterForge.jar              runnable Java archive
├── Gunter's Forge.app/          macOS app launcher
├── Run Gunter's Forge.command   macOS terminal launcher
└── README.md
```

### `src/Main.java`

This is the main application file. It contains the product list, material recipe table, order form, basket, queue, status controls, and material summary. Most competition changes can be made here.

Useful values to edit:

- `ITEMS` controls the products shown in the catalog.
- `MATERIALS` controls the names shown in the material summary.
- `RECIPE` contains the estimated amount of each material for each product.
- `STATUSES` controls the order progress options.
- `description()` provides the short text shown on each product card.

When adding a new product, keep `ITEMS` and `RECIPE` rows in the same order, add a product illustration case in `ProductArt.java`, and add its description in `description()`.

### `src/ProductArt.java`

This file draws the four product illustrations with Java2D shapes. It contains no image files or extra libraries. Each product card uses the same illustration area, and the two-by-two catalog grid keeps the cards aligned as the window changes size.

## Data and limitations

Orders stay in memory while the app is open. Closing the application clears the queue. This version does not use an online database, sync orders between computers, process payments, calculate prices, or send pickup notifications.

The material prep counts are estimates based on the recipe values in the source. The shop should set those values to its real material use before relying on them for production planning.
