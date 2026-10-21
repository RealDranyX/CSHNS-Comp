## Gunter's Forge

Gunter's Forge is a Java desktop application for taking blacksmith orders and keeping track of work in the shop. Customers can choose products, adjust quantities, enter order details, see prices in the cart, and add orders to Gunter's queue. The shop view tracks each order's status and estimates the materials needed for queued work. The app also includes Gunter's Mining Adventure, a gold clicker game with upgrades. Mining 100,000 gold earns a $3 discount on an order with a subtotal of at least $3.

The project uses Java Swing for the interface and Java2D for the product illustrations. The mining game uses the gold image in the `assets` folder.

## AI use disclosure

AI tools were used to help develop and refine the application's user interface (UI). The team created the core application mechanisms, including order handling, cart pricing, the mining game, queue management, and material planning. These features run locally in the Java application/scenario; there is no separate server backend.

## Requirements

- JDK 8 or newer
- macOS, Windows, or Linux with a desktop environment

## Run the application

### macOS launcher

1. Keep the project files together in the project folder.
2. Double-click `Run Gunter's Forge.command`.
3. The application opens to the order page, with a separate shop queue tab.

The launcher checks for Java on the computer and then for the Java runtime bundled with VS Code. If neither is available, install a JDK and try again.

### Compile and run from source

Open a terminal in the project folder and run:

```sh
mkdir -p out/assets
javac --release 8 -d out Main.java ProductArt.java
cp assets/gold-removebg-preview.png out/assets/
java -cp out Main
```

To create a runnable JAR after compiling:

```sh
jar --create --file GunterForge.jar --main-class Main -C out .
```

Run the JAR with:

```sh
java -jar GunterForge.jar
```

## Using the app

### Place an order

The **Place an order** tab shows the product catalog and checkout area. Add items to the basket, change their quantities, enter a customer name, choose a finish and timing request, and add optional notes. The cart shows each item's price, the subtotal, any mining discount, and the final total. Place the order to add it to Gunter's queue.

The available products and prices are:

- Sword — $3
- Plate Armor — $2
- Chain Mail — $4
- Cooking Pot — $1

The $3 mining discount requires at least 100,000 gold and a cart subtotal of at least $3. The gold is spent when the discounted order is placed. Rush requests are marked for Gunter to review; the app does not promise a completion time.

### Play Gunter's Mining Adventure

Click the gold image to mine gold. Use gold to purchase upgrades:

- **Sharper Pickaxe** increases the gold earned per click.
- **Iron Pickaxe** increases the gold earned per click further.
- **Mining Helper** earns gold automatically over time.

Gold and upgrades last only while the application is open.

### Manage the shop queue

Open the **Shop queue** tab to see orders in the order they were placed. Select an order to view its details, then update its status through **Queued**, **In progress**, and **Ready for pickup**. Orders can also be removed. The summary shows open, ready, and rush orders, along with an estimate of the shop's workload.

### Prepare materials

The material panel estimates the materials needed for queued work. It includes iron, wood, leather, and charcoal. Only orders with **Queued** status are included in the material totals. The sample recipes are defined near the top of `Main.java` in the `RECIPE` array. Rows match the `ITEMS` array, and columns match the `MATERIALS` array. These are demonstration estimates; adjust them to match the shop's real recipes before relying on them for production planning.

## Source files

```text
CSHNS-Comp-main-3/
├── Main.java                       main application, cart, queue, and mining game
├── ProductArt.java                 Java2D product illustrations
├── assets/
│   └── gold-removebg-preview.png   mining game image
├── out/                            compiled classes after building
├── GunterForge.jar                 runnable Java archive
├── Run Gunter's Forge.command      macOS launcher
└── README.md
```

### `Main.java`

This is the main application file. It contains the product and material lists, price values, order form, cart, queue, mining game, upgrades, and material summary. Most competition changes can be made here.

Useful values to edit:

- `ITEMS` controls the products shown in the catalog.
- `MATERIALS` controls the names shown in the material summary.
- `RECIPE` contains estimated material amounts for each product.
- `STATUSES` controls the order progress options.
- The product price fields control the listed prices.
- `DISCOUNT_GOLD_COST` and `GOLD_REWARD_DISCOUNT` control the mining reward.
- `description()` provides the short text shown on product cards.

When adding a product, keep `ITEMS` and `RECIPE` rows in the same order, add an illustration case in `ProductArt.java`, set its price, and add its description in `description()`.

### `ProductArt.java`

This file draws the four product illustrations with Java2D shapes. Each product card uses a consistent illustration area, and the catalog keeps the product cards aligned as the window changes size.

## Data and limitations

Orders, mining gold, and upgrade levels stay in memory while the application is open. Closing the app clears this information. The application does not use an online database, sync orders between computers, process payments, or send pickup notifications.

Material totals are estimates based on the recipe values in the source. Gunter should set those values to the shop's real material use before relying on them for production planning.
